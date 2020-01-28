package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.rendering.TileMap;
import jfw.util.rendering.TileRenderer;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.UnicodeTile;
import jfw.util.rendering.tile.FullTile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenderingDemo extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Rendering Demo");
		Group root = new Group();
		Canvas canvas = new Canvas(1000, 600);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		TileRenderer tileRenderer = new TileRenderer(canvasRenderer, 0, 0,  22, 32);

		render(tileRenderer);
	}

	private void render(TileRenderer tileRenderer) {
		log.info("render()");

		TileMap tileMap = new TileMap(20, 10, Tile.EMPTY);
		UnicodeTile fireTile = new UnicodeTile(128293, Color.RED);
		FullTile redTile = new FullTile(Color.RED);

		tileMap.setTile(redTile, 5, 0);
		tileMap.setTile(redTile, 7, 0);
		tileMap.setTile(redTile, 9, 0);
		tileMap.setTile(redTile, 11, 0);
		tileMap.setTile(redTile, 13, 0);
		tileMap.setTile(redTile, 18, 0);
		tileMap.setTile(fireTile, 5, 4);
		tileMap.setText("Hello & goodbye", 5, 1, Color.BLUE);
		tileMap.setText("Test: 🌳 & 🌲", 1, 2, Color.BLUE);
		tileMap.setCenteredText("Centered", 8, Color.GREEN);

		tileMap.render(tileRenderer, 0, 0);

		log.info("render(): finished");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
