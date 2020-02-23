package jfw.app.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.action.MoveEntity;
import jfw.game.reducer.MoveEntityReducer;
import jfw.game.state.State;
import jfw.game.state.component.Statistics;
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
import jfw.util.tile.Tile;
import jfw.util.tile.UnicodeTile;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileSelector;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static jfw.game.state.world.WorldCell.TILE_SELECTOR;

@Slf4j
public class TravelDemo extends TileApplication {

	private static final Tile CHARACTER_TILE = new UnicodeTile("@", Color.BLACK);
	private static final Tile ACTIVE_CHARACTER_TILE = new UnicodeTile("@", Color.WHITE);

	private static final Reducer<Object, State> REDUCER = (action, oldState) -> {
		if (action instanceof MoveEntity) {
			return MoveEntityReducer.REDUCER.reduce((MoveEntity) action, oldState);
		}

		return oldState;
	};

	private Store<Object, State> store;
	private final TimeDefinition timeDefinition = new TimeDefinition();
	private TileSelector<Integer> characterTileSelector;

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
		positionMap.put(2, 122);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		ComponentStorage<Statistics> statisticsStorage = new ComponentMap<>(Collections.emptyMap());

		List<TimeEntry> entries = positions.getIds().stream().map(TimeEntry::new).collect(Collectors.toList());
		TimeSystem timeSystem = new TimeSystem(entries);

		State initState = new State(worldMap, positions, statisticsStorage, timeSystem);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>()));

		characterTileSelector = id -> {
			if (getCurrentEntityId(store.getState()) == id) {
				return ACTIVE_CHARACTER_TILE;
			}
			return CHARACTER_TILE;
		};

		store.subscribe(this::render);
	}

	private void render(State state) {
		log.info("render()");

		TileMap worldMap = createTileMap();
		worldMap.setMap(state.getWorldMap(), 0, 0, TILE_SELECTOR);
		worldMap.render(tileRenderer, 0, 0);

		TileMap uiMap = createTileMap();
		EntityView.view(state.getPositions(), uiMap, characterTileSelector);
		uiMap.setText(getTime(state), 0, 0, Color.BLACK);
		uiMap.setText(getNextEntityText(state), 0, 9, Color.BLACK);
		uiMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	private String getTime(State state) {
		return timeDefinition.toString(state.getTimeSystem().getCurrentTime());
	}

	private String getNextEntityText(State state) {
		return "Entity=" + getCurrentEntityId(state);
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		int entityId = getCurrentEntityId(store.getState());

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

	private int getCurrentEntityId(State state) {
		return state.getTimeSystem().getCurrentEntry().getEntityId();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
