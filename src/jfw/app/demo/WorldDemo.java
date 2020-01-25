package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.state.world.WorldMap;
import jfw.util.redux.Store;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.rendering.TileMap;
import jfw.util.rendering.TileRenderer;
import jfw.util.rendering.tile.Tile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorldDemo extends Application {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 10;

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
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		tileRenderer = new TileRenderer(canvasRenderer, 0, 0,  22, 32);

		create();
	}

	private void create() {
		log.info("create()");

		int size = WIDTH * HEIGHT;
		WorldCell[] cells = new WorldCell[size];

		for (int i = 0; i < size; i++) {
			cells[i] = new WorldCell(TerrainType.PLAIN);
		}

		WorldMap worldMap = new WorldMap(WIDTH, HEIGHT, cells);
		DemoState initState = new DemoState(worldMap);
		store = new Store<>((action, state) -> state, initState);

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap tileMap = new TileMap(WIDTH, HEIGHT, Tile.EMPTY);

		tileMap.setCenteredText("🌳 🌲 ⛰ 🌊", 8, Color.GREEN);

		tileMap.render(tileRenderer, 0, 0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
