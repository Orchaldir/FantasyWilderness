package jfw.util.rendering.tile;

import jfw.util.rendering.TileRenderer;
import lombok.ToString;

@ToString
public class EmptyTile implements Tile {

	public static final Tile EMPTY = new EmptyTile();

	@Override
	public void render(TileRenderer renderer, int column, int row) {

	}
}
