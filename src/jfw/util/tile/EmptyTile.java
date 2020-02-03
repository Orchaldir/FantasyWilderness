package jfw.util.tile;

import jfw.util.tile.rendering.TileRenderer;
import lombok.ToString;

@ToString
public class EmptyTile implements Tile {

	public static final Tile EMPTY = new EmptyTile();

	@Override
	public void render(TileRenderer renderer, int column, int row) {
		// An empty tile renders nothing.
	}
}
