package jfw.app.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.util.rendering.CanvasRenderer;
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

		render(canvasRenderer);
	}

	private void render(CanvasRenderer canvasRenderer) {
		log.info("render()");

		canvasRenderer.setColor(Color.RED);
		canvasRenderer.renderRectangle(100, 0, 50, 50);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
