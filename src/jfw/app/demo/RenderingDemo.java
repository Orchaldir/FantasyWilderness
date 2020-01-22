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
		TileMap tileMap = new TileMap(canvasRenderer, 0, 0,  22, 32, 20, 10);

		render(tileMap);
	}

	private void render(TileMap tileMap) {
		log.info("render()");

		UnicodeTile fireTile = new UnicodeTile(128293, Color.RED);
		FullTile redTile = new FullTile(Color.RED);

		tileMap.clear();
		redTile.render(tileMap, 5, 0);
		redTile.render(tileMap, 7, 1);
		redTile.render(tileMap, 9, 1);
		redTile.render(tileMap, 11, 1);
		redTile.render(tileMap, 13, 1);
		redTile.render(tileMap, 18, 1);
		fireTile.render(tileMap, 5, 4);
		tileMap.renderText("Hello & goodbye", 5, 1, Color.BLUE);
		tileMap.renderCenteredText("Centered", 8, Color.GREEN);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
