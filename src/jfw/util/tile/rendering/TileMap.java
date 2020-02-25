package jfw.util.tile.rendering;

import javafx.scene.paint.Color;
import jfw.util.map.Map2d;
import jfw.util.map.MutableArrayMap2d;
import jfw.util.map.MutableMap2d;
import jfw.util.tile.Tile;
import jfw.util.tile.UnicodeTile;
import lombok.Getter;
import lombok.NonNull;

import static jfw.util.Validator.validateNotNull;

@Getter
public class TileMap {

	private final MutableMap2d<Tile> map;

	public TileMap(int width, int height, Tile tile) {
		validateNotNull(tile, "tile");
		Tile[] cells = new Tile[width * height];
		map = new MutableArrayMap2d<>(width, height, cells, tile);
	}

	public Map2d<Tile> getMap() {
		return map;
	}

	public void render(TileRenderer renderer) {
		render(renderer, 0, 0);
	}
	public void render(TileRenderer renderer, int column, int row) {
		int index = 0;

		for (int currentRow = row; currentRow < row + map.getHeight(); currentRow++) {
			for (int currentColumn = column; currentColumn < column + map.getWidth(); currentColumn++) {
				map.getNode(index++).render(renderer, currentColumn, currentRow);
			}
		}
	}

	public <T> void setMap(Map2d<T> map, int column, int row, TileConverter<T> selector) {
		for (int r = 0; r < map.getHeight(); r++) {
			for (int c = 0; c < map.getWidth(); c++) {
				int currentColumn = column + c;
				int currentRow = row + r;

				if (this.map.isInside(currentColumn, currentRow)) {
					this.map.setNode(selector.convert(map.getNode(c, r)), currentColumn, currentRow);
				}
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

	public void setTile(@NonNull Tile tile) {
		for (int i = 0; i < map.getSize(); i++) {
			map.setNode(tile, i);
		}
	}

}
