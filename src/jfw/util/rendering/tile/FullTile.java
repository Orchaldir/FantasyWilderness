package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import jfw.util.rendering.TileMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FullTile implements Tile {

	@NonNull
	private final Color color;

	@Override
	public void render(TileMap tileMap, int column, int row) {
		tileMap.renderTile(column, row, color);
	}

}
