package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import jfw.util.rendering.TileMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullTile implements Tile {

	private final Color color;

	@Override
	public void render(TileMap tileMap, int column, int row) {
		tileMap.renderTile(column, row, color);
	}

}
