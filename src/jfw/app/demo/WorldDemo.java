package jfw.app.demo;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.util.TileApplication;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jfw.game.state.world.WorldCell.TILE_SELECTOR;

@Slf4j
public class WorldDemo extends TileApplication {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 10;

	private static final int TILE_WIDTH = 22;
	private static final int TILE_HEIGHT = 32;

	private static final Tile CHARACTER_TILE = new UnicodeTile("@", Color.BLACK);

	@AllArgsConstructor
	@ToString
	private static class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
		private final ComponentStorage<Integer> positions;
		private final TerrainType tool;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	private static class ChangeTerrain {
		private final int index;
		private final TerrainType terrainType;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	private static class SwitchTool {
		private final TerrainType tool;
	}

	private static final Reducer<Object, DemoState> REDUCER = (action, oldState) -> {
		if (action instanceof ChangeTerrain) {
			ChangeTerrain changeTerrain = (ChangeTerrain) action;
			if (oldState.worldMap.getNode(changeTerrain.index).getTerrainType() == changeTerrain.terrainType) {
				return oldState;
			}

			WorldCell newCell = new WorldCell(changeTerrain.terrainType);
			ArrayMap2d<WorldCell> newWorldMap = oldState.worldMap.withCell(newCell, changeTerrain.index);

			return new DemoState(newWorldMap, oldState.positions, oldState.tool);
		}
		else if (action instanceof SwitchTool) {
			SwitchTool switchTool = (SwitchTool) action;

			if (switchTool.tool != oldState.tool) {
				return new DemoState(oldState.worldMap, oldState.positions, switchTool.tool);
			}
		}

		return oldState;
	};

	private Store<Object, DemoState> store;

	@Override
	public void start(Stage primaryStage) {
		Scene scene = init(primaryStage, "World Demo", WIDTH, HEIGHT, TILE_WIDTH, TILE_HEIGHT);

		EventHandler<MouseEvent> mouseEventEventHandler = event -> onMouseClicked((int) event.getX(), (int) event.getY());

		scene.setOnMouseClicked(mouseEventEventHandler);
		scene.setOnMouseDragged(mouseEventEventHandler);

		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));
		create();
	}

	private void create() {
		log.info("create()");

		int size = WIDTH * HEIGHT;
		WorldCell[] cells = new WorldCell[size];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(WIDTH, HEIGHT, cells, new WorldCell(TerrainType.PLAIN));

		Map<Integer,Integer> positionMap = new HashMap<>();
		positionMap.put(0, 8);
		positionMap.put(1, 44);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		DemoState initState = new DemoState(worldMap, positions, TerrainType.MOUNTAIN);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>(), new LogDiffMiddleware<>()));

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = createTileMap();
		worldMap.setMap(state.worldMap, 0, 0, TILE_SELECTOR);
		worldMap.render(tileRenderer, 0, 0);

		TileMap uiMap = createTileMap();
		uiMap.setText("Tool=" + state.tool, 0, 9, Color.BLACK);
		renderCharacters(state, uiMap);
		uiMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	private void renderCharacters(DemoState state, TileMap tileMap) {
		Map2d<Tile> map = tileMap.getMap();

		for (Integer position : state.positions.getAll()) {
			tileMap.setTile(CHARACTER_TILE, map.getX(position), map.getY(position));
		}
	}

	private void onMouseClicked(int x, int y) {
		int column = tileRenderer.getColumn(x);
		int row = tileRenderer.getRow(y);
		log.info("onMouseClicked(): screen={}|{} -> tile={}|{}", x, y, column, row);

		DemoState state = store.getState();
		Map2d<WorldCell> map = state.worldMap;

		if (map.isInside(column, row)) {
			ChangeTerrain action = new ChangeTerrain(map.getIndex(column, row), state.tool);
			store.dispatch(action);
		}
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		if (keyCode == KeyCode.DIGIT1) {
			store.dispatch(new SwitchTool(TerrainType.PLAIN));
		}
		else if (keyCode == KeyCode.DIGIT2) {
			store.dispatch(new SwitchTool(TerrainType.HILL));
		}
		else if (keyCode == KeyCode.DIGIT3) {
			store.dispatch(new SwitchTool(TerrainType.MOUNTAIN));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
