package jfw.util.tile.rendering;

import javafx.scene.paint.Color;
import jfw.util.rendering.Renderer;
import lombok.Getter;
import lombok.NonNull;

import static jfw.util.Validator.validateGreater;
import static jfw.util.Validator.validateNotNull;

@Getter
public class TileRenderer {

	private final Renderer renderer;

	// in pixels
	private final int startX;
	private final int startY;
	private final int tileWidth;
	private final int tileHeight;

	public TileRenderer(Renderer renderer,
						int startX, int startY,
						int tileWidth, int tileHeight) {
		this.renderer = validateNotNull(renderer, "renderer");
		this.startX = validateGreater(startX, -1, "startX");
		this.startY = validateGreater(startY, -1, "startY");
		this.tileWidth = validateGreater(tileWidth, 0, "tileWidth");
		this.tileHeight = validateGreater(tileHeight, 0, "tileHeight");
	}

	public void renderCharacter(int codePoint, int column, int row, @NonNull Color color) {
		renderer.setColor(color);
		renderer.renderCharacter(codePoint, getCenterPixelX(column), getCenterPixelY(row), tileHeight);
	}

	public void renderTile(int column, int row, @NonNull Color color) {
		renderer.setColor(color);
		renderer.renderRectangle(getStartPixelX(column), getStartPixelY(row), tileWidth, tileHeight);
	}

	public int getColumn(int x) {
		return (x - startX) / tileWidth;
	}

	public int getRow(int y) {
		return (y - startY) / tileHeight;
	}

	private int getStartPixelX(int column) {
		return startX + column * tileWidth;
	}

	private int getStartPixelY(int row) {
		return startY + row * tileHeight;
	}

	private int getCenterPixelX(int column) {
		return getStartPixelX(column) + tileWidth / 2;
	}

	private int getCenterPixelY(int row) {
		return getStartPixelY(row) + tileHeight / 2;
	}
}
