package jfw.util;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import jfw.util.rendering.CanvasRenderer;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static jfw.util.tile.EmptyTile.EMPTY;

@Getter
@Slf4j
public abstract class TileApplication extends Application {

	private int columns;
	private int rows;

	private int tileWidth;
	private int tileHeight;

	protected TileRenderer tileRenderer;

	public Scene init(Stage primaryStage, String title, int columns, int rows, int tileWidth, int tileHeight) {
		this.columns = columns;
		this.rows = rows;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;

		primaryStage.setTitle(title);
		Group root = new Group();
		Canvas canvas = new Canvas(columns * tileWidth, rows * tileHeight);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		CanvasRenderer canvasRenderer = new CanvasRenderer(canvas.getGraphicsContext2D());
		tileRenderer = new TileRenderer(canvasRenderer, 0, 0, tileWidth, tileHeight);

		return scene;
	}

	public TileMap createTileMap() {
		return new TileMap(columns, rows, EMPTY);
	}
}
