package jfw.util.rendering;

import javafx.scene.paint.Color;
import jfw.util.map.Map2d;
import jfw.util.map.MutableArrayMap2d;
import jfw.util.map.MutableMap2d;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.UnicodeTile;
import lombok.Getter;
import lombok.NonNull;

import static jfw.util.Validator.validateNotNull;

@Getter
public class TileMap {

	private final MutableMap2d<Tile> map;

	public TileMap(int width, int height, Tile tile) {
		validateNotNull(tile, "tile");
		int size = width * height;
		Tile[] cells = new Tile[size];

		for (int i = 0; i < size; i++) {
			cells[i] = tile;
		}
		map = new MutableArrayMap2d<>(width, height, cells);
	}

	public Map2d<Tile> getMap() {
		return map;
	}

	public void render(TileRenderer renderer, int column, int row) {
		int index = 0;

		for (int currentRow = row; currentRow < row + map.getHeight(); currentRow++) {
			for (int currentColumn = column; currentColumn < column + map.getWidth(); currentColumn++) {
				map.getNode(index++).render(renderer, currentColumn, currentRow);
			}
		}
	}

	public void setText(String text, int column, int row, Color color) {
		int index = 0;

		for (int character : text.codePoints().toArray()) {
			int currentColumn = column + index++;

			if (map.isInside(currentColumn, row)) {
				map.setNode(new UnicodeTile(character, color), currentColumn, row);
			}
		}
	}

	public void setCenteredText(String text, int row, Color color) {
		int column = (map.getWidth() - text.length()) / 2;
		setText(text, column, row, color);
	}

	public void setTile(@NonNull Tile tile, int column, int row) {
		map.setNode(tile, column, row);
	}

}
