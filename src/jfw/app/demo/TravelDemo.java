package jfw.app.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.TimeDefinition;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import jfw.game.view.EntityView;
import jfw.util.TileApplication;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Direction;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import jfw.util.redux.middleware.LogDiffMiddleware;
import jfw.util.tile.Tile;
import jfw.util.tile.UnicodeTile;
import jfw.util.tile.rendering.TileMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static jfw.game.state.world.WorldCell.TILE_SELECTOR;

@Slf4j
public class TravelDemo extends TileApplication {

	private static final Tile CHARACTER_TILE = new UnicodeTile("@", Color.BLACK);

	@AllArgsConstructor
	@ToString
	private static class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
		private final ComponentStorage<Integer> positions;
		private final TimeSystem timeSystem;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	private static class MoveEntity {
		private final int entityId;
		private final Direction direction;
	}

	private static final Reducer<Object, DemoState> REDUCER = (action, oldState) -> {
		if (action instanceof MoveEntity) {
			MoveEntity moveEntity = (MoveEntity) action;

			Optional<Integer> optionalPosition = oldState.positions.get(moveEntity.entityId);

			if (optionalPosition.isPresent()) {
				int position = optionalPosition.get();
				Optional<Integer> optionalNewPosition = oldState.worldMap.getNeighborIndex(position, moveEntity.direction);

				if (optionalNewPosition.isPresent()) {
					ComponentStorage<Integer> positions = oldState.positions.
							updateComponent(moveEntity.entityId, optionalNewPosition.get());

					TimeSystem timeSystem = oldState.timeSystem.advanceCurrentEntry(30);

					return new DemoState(oldState.worldMap, positions, timeSystem);
				}
			}
		}

		return oldState;
	};

	private Store<Object, DemoState> store;
	private final TimeDefinition timeDefinition = new TimeDefinition();

	@Override
	public void start(Stage primaryStage) {
		Scene scene = init(primaryStage, "Travel Demo", 20, 10, 22, 32);

		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));

		create();
	}

	private void create() {
		log.info("create()");

		WorldCell[] cells = new WorldCell[getTiles()];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(getColumns(), getRows(), cells, new WorldCell(TerrainType.PLAIN));

		Map<Integer,Integer> positionMap = new HashMap<>();
		positionMap.put(0, 88);
		positionMap.put(1, 44);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		List<TimeEntry> entries = positions.getIds().stream().map(TimeEntry::new).collect(Collectors.toList());
		TimeSystem timeSystem = new TimeSystem(entries);

		DemoState initState = new DemoState(worldMap, positions, timeSystem);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>(), new LogDiffMiddleware<>()));

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = createTileMap();
		worldMap.setMap(state.worldMap, 0, 0, TILE_SELECTOR);
		worldMap.render(tileRenderer, 0, 0);

		TileMap uiMap = createTileMap();
		EntityView.view(state.positions, uiMap, id -> CHARACTER_TILE);
		uiMap.setText(getTime(state), 0, 0, Color.BLACK);
		uiMap.setText(getNextEntityText(state), 0, 9, Color.BLACK);
		uiMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	private String getTime(DemoState state) {
		return timeDefinition.toString(state.timeSystem.getCurrentTime());
	}

	private String getNextEntityText(DemoState state) {
		return "Entity=" + state.timeSystem.getCurrentEntry().getEntityId();
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		int entityId = store.getState().timeSystem.getCurrentEntry().getEntityId();

		if (keyCode == KeyCode.UP) {
			store.dispatch(new MoveEntity(entityId, Direction.NORTH));
		}
		else if (keyCode == KeyCode.RIGHT) {
			store.dispatch(new MoveEntity(entityId, Direction.EAST));
		}
		else if (keyCode == KeyCode.DOWN) {
			store.dispatch(new MoveEntity(entityId, Direction.SOUTH));
		}
		else if (keyCode == KeyCode.LEFT) {
			store.dispatch(new MoveEntity(entityId, Direction.WEST));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
