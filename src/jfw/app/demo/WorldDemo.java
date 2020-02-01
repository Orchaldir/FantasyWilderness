package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.rendering.TileMap;
import jfw.util.rendering.TileRenderer;
import jfw.util.rendering.tile.FullTile;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.TileSelector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class WorldDemo extends Application {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 10;

	private static final int TILE_WIDTH = 22;
	private static final int TILE_HEIGHT = 32;

	private static final Tile PLAIN_TILE = new FullTile(Color.GREEN);
	private static final Tile HILL_TILE = new FullTile(Color.SADDLEBROWN);
	private static final Tile MOUNTAIN_TILE = new FullTile(Color.GREY);

	private static final TileSelector<WorldCell> TILE_SELECTOR = cell -> {
		switch (cell.getTerrainType()) {
			case PLAIN:
				return PLAIN_TILE;
			case HILL:
				return HILL_TILE;
			default:
				return MOUNTAIN_TILE;
		}
	};

	@AllArgsConstructor
	@ToString
	private static class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
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

	private final Reducer<Object, DemoState> REDUCER = (action, oldState) -> {
		if (action instanceof ChangeTerrain) {
			ChangeTerrain changeTerrain = (ChangeTerrain) action;
			if (oldState.worldMap.getNode(changeTerrain.index).getTerrainType() == changeTerrain.terrainType) {
				return oldState;
			}

			WorldCell newCell = new WorldCell(changeTerrain.terrainType);
			ArrayMap2d<WorldCell> newWorldMap = oldState.worldMap.withCell(newCell, changeTerrain.index);

			return new DemoState(newWorldMap, oldState.tool);
		}
		else if (action instanceof SwitchTool) {
			SwitchTool switchTool = (SwitchTool) action;

			if (switchTool.tool != oldState.tool) {
				return new DemoState(oldState.worldMap, switchTool.tool);
			}
		}

		return oldState;
	};

	private TileRenderer tileRenderer;
	private Store<Object, DemoState> store;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("World Demo");
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH * TILE_WIDTH, HEIGHT * TILE_HEIGHT);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setOnMouseClicked(event -> onMouseClicked((int)event.getX(), (int)event.getY()));
		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		tileRenderer = new TileRenderer(canvasRenderer, 0, 0,  TILE_WIDTH, TILE_HEIGHT);

		create();
	}

	private void create() {
		log.info("create()");

		int size = WIDTH * HEIGHT;
		WorldCell[] cells = new WorldCell[size];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(WIDTH, HEIGHT, cells, new WorldCell(TerrainType.PLAIN));
		DemoState initState = new DemoState(worldMap, TerrainType.MOUNTAIN);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>()));

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = new TileMap(WIDTH, HEIGHT, Tile.EMPTY);
		worldMap.setMap(state.worldMap, 0, 0, TILE_SELECTOR);
		worldMap.render(tileRenderer, 0, 0);

		TileMap uiMap = new TileMap(WIDTH, HEIGHT, Tile.EMPTY);
		uiMap.setText("Tool=" + state.tool, 0, 9, Color.BLACK);
		uiMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
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
