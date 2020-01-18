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
		Canvas canvas = new Canvas(1000, 600);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		TileMap tileMap = new TileMap(canvasRenderer, 0, 0,  22, 32, 10, 10);

		render(tileMap);
	}

	private void render(TileMap tileMap) {
		log.info("render()");

		tileMap.clear();
		tileMap.renderTile(5, 0, Color.RED);
		tileMap.renderTile(7, 1, Color.RED);
		tileMap.renderTile(9, 1, Color.RED);
		tileMap.renderTile(11, 1, Color.RED);
		tileMap.renderTile(13, 1, Color.RED);
		tileMap.renderTile(18, 1, Color.RED);
		tileMap.renderCharacter('W', 5, 0, Color.BLACK);
		tileMap.renderText("Hello & goodbye", 5, 1, Color.BLUE);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
