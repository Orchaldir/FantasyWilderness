package jfw.app.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.view.EntityView;
import jfw.util.TileApplication;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import jfw.util.redux.middleware.LogDiffMiddleware;
import jfw.util.tile.Tile;
import jfw.util.tile.UnicodeTile;
import jfw.util.tile.rendering.TileMap;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jfw.game.state.world.WorldCell.TILE_SELECTOR;

@Slf4j
public class TravelDemo extends TileApplication {

	private static final Tile CHARACTER_TILE = new UnicodeTile("@", Color.BLACK);

	@AllArgsConstructor
	@ToString
	private static class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
		private final ComponentStorage<Integer> positions;
	}

	private static final Reducer<Object, DemoState> REDUCER = (action, oldState) -> oldState;

	private Store<Object, DemoState> store;

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
		positionMap.put(0, 8);
		positionMap.put(1, 44);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		DemoState initState = new DemoState(worldMap, positions);
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
		uiMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
