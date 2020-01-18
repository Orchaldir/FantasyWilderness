package jfw.util.rendering;

import javafx.scene.paint.Color;

public class TileMap {

	private final Renderer renderer;

	// in pixels
	private final int startX;
	private final int startY;
	private final int width;
	private final int height;
	private final int tileWidth;
	private final int tileHeight;

	private final int numberOfColumns;
	private final int numberOfRows;

	public TileMap(Renderer renderer,
					int startX, int startY,
					int tileWidth, int tileHeight,
					int numberOfColumns, int numberOfRows) {
		this.renderer = renderer;
		this.startX = startX;
		this.startY = startY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;

		width = tileWidth * numberOfColumns;
		height = tileHeight * numberOfRows;
	}

	public void clear() {
		renderer.clear(startX, startY, width, height);
	}

	public void renderTile(int column, int row, Color color) {
		renderer.setColor(color);
		renderer.renderRectangle(getX(column), getY(row), tileWidth, tileHeight);
	}

	private int getX(int column) {
		return startX + column * tileWidth;
	}

	private int getY(int row) {
		return startY + row * tileHeight;
	}
}
