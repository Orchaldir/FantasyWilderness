package jfw.util.rendering;

import javafx.scene.paint.Color;
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
		renderer.renderCharacter(codePoint, getCenterX(column), getCenterY(row), tileHeight);
	}

	public void renderTile(int column, int row, @NonNull Color color) {
		renderer.setColor(color);
		renderer.renderRectangle(getStartX(column), getStartY(row), tileWidth, tileHeight);
	}

	private int getStartX(int column) {
		return startX + column * tileWidth;
	}

	private int getStartY(int row) {
		return startY + row * tileHeight;
	}

	private int getCenterX(int column) {
		return getStartX(column) + tileWidth / 2;
	}

	private int getCenterY(int row) {
		return getStartY(row) + tileHeight / 2;
	}
}
