package jfw.util.rendering;

import javafx.scene.paint.Color;
import jfw.util.OutsideMapException;
import lombok.NonNull;

import static jfw.util.Validator.requireGreater;

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

	public TileMap(@NonNull Renderer renderer,
					int startX, int startY,
					int tileWidth, int tileHeight,
					int numberOfColumns, int numberOfRows) {
		this.renderer = renderer;
		this.startX = requireGreater(startX, -1, "startX");
		this.startY = requireGreater(startY, -1, "startY");
		this.tileWidth = requireGreater(tileWidth, 0, "tileWidth");
		this.tileHeight = requireGreater(tileHeight, 0, "tileHeight");
		this.numberOfColumns = requireGreater(numberOfColumns, 0, "numberOfColumns");
		this.numberOfRows = requireGreater(numberOfRows, 0, "numberOfRows");

		width = tileWidth * numberOfColumns;
		height = tileHeight * numberOfRows;
	}

	public void clear() {
		renderer.clear(startX, startY, width, height);
	}

	public void renderCharacter(char character, int column, int row, Color color) {
		validateInside(column, row);
		renderer.setColor(color);
		renderCharacter(character, column, row);
	}

	private void renderCharacter(char character, int column, int row) {
		renderer.renderCharacter(character, getCenterX(column), getCenterY(row), tileHeight);
	}

	public void renderText(String text, int column, int row, Color color) {
		renderer.setColor(color);

		int index = 0;

		for (char character : text.toCharArray()) {
			int currentColumn = column + index++;

			if (isInside(currentColumn, row)) {
				renderCharacter(character, currentColumn, row);
			}
		}
	}

	public void renderCenteredText(String text, int row, Color color) {
		int column = (numberOfColumns - text.length()) / 2;
		renderText(text, column, row, color);
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

	private int getCenterX(int column) {
		return getX(column) + tileWidth / 2;
	}

	private int getCenterY(int row) {
		return getY(row) + tileHeight / 2;
	}

	private void validateInside(int column, int row) {
		if(isOutside(column, row)) {
			throw new OutsideMapException(column, row);
		}
	}

	private boolean isInside(int column, int row) {
		return !isOutside(column, row);
	}

	private boolean isOutside(int column, int row) {
		return column < 0 || column >= numberOfColumns ||
				row < 0 || row >= numberOfRows;
	}
}
