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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenderingDemo extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Rendering Demo");
		Group root = new Group();
		Canvas canvas = new Canvas(300, 250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		TileMap tileMap = new TileMap(canvasRenderer, 0, 0,  16, 16, 10, 10);

		render(tileMap);
	}

	private void render(TileMap tileMap) {
		log.info("render()");

		tileMap.clear();
		tileMap.renderTile(5, 8, Color.RED);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
