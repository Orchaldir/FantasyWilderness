package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.rendering.TileMap;
import jfw.util.rendering.TileRenderer;
import jfw.util.rendering.tile.FullTile;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.TileSelector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorldDemo extends Application {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 10;

	private static final Tile PLAIN_TILE = new FullTile(Color.GREEN);
	private static final Tile HILL_TILE = new FullTile(Color.BROWN);
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
	private class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
	}

	@AllArgsConstructor
	@Getter
	private class WorldAction {
		private final int index;
		private final TerrainType terrainType;
	}

	private final Reducer<WorldAction, DemoState> REDUCER = (action, oldState) -> {
		ArrayMap2d<WorldCell> newWorldMap = oldState.worldMap.withCell(new WorldCell(action.terrainType), action.index);

		return new DemoState(newWorldMap);
	};

	private TileRenderer tileRenderer;
	private Store<WorldAction, DemoState> store;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("World Demo");
		Group root = new Group();
		Canvas canvas = new Canvas(1000, 600);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		canvas.setOnMouseClicked(event -> onMouseClick((int)event.getX(), (int)event.getY()));

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		tileRenderer = new TileRenderer(canvasRenderer, 0, 0,  22, 32);

		create();
	}

	private void create() {
		log.info("create()");

		int size = WIDTH * HEIGHT;
		WorldCell[] cells = new WorldCell[size];
		DemoState initState = new DemoState(new ArrayMap2d<>(WIDTH, HEIGHT, cells, new WorldCell(TerrainType.PLAIN)));
		store = new Store<>(REDUCER, initState);

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = new TileMap(WIDTH, HEIGHT, Tile.EMPTY);

		worldMap.setMap(state.worldMap, 0, 0, TILE_SELECTOR);
		worldMap.setCenteredText("🌳 🌲 ⛰ 🌊", 8, Color.GREEN);

		worldMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	private void onMouseClick(int x, int y) {
		int column = tileRenderer.getColumn(x);
		int row = tileRenderer.getRow(y);
		log.info("onMouseClick(): screen={}|{} -> tile={}|{}", x, y, column, row);

		Map2d<WorldCell> map = store.getState().worldMap;

		if (map.isInside(column, row)) {
			WorldAction action = new WorldAction(map.getIndex(column, row), TerrainType.MOUNTAIN);
			store.dispatch(action);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
