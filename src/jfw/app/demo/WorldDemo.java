package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.state.world.WorldMap;
import jfw.util.redux.Store;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.rendering.TileMap;
import jfw.util.rendering.TileRenderer;
import jfw.util.rendering.tile.FullTile;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.TileSelector;
import lombok.AllArgsConstructor;
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
		private final WorldMap worldMap;
	}

	private TileRenderer tileRenderer;
	private Store<Integer, DemoState> store;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("World Demo");
		Group root = new Group();
		Canvas canvas = new Canvas(1000, 600);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		tileRenderer = new TileRenderer(canvasRenderer, 0, 0,  22, 32);

		create();
	}

	private void create() {
		log.info("create()");

		WorldMap worldMap = new WorldMap(WIDTH, HEIGHT, new WorldCell(TerrainType.PLAIN));
		DemoState initState = new DemoState(worldMap);
		store = new Store<>((action, state) -> state, initState);

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = new TileMap(WIDTH, HEIGHT, Tile.EMPTY);

		worldMap.setMap(state.worldMap.getCells(), 1, 2, TILE_SELECTOR);
		worldMap.setCenteredText("🌳 🌲 ⛰ 🌊", 8, Color.GREEN);

		worldMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
