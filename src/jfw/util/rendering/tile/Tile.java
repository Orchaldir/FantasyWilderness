package jfw.util.rendering.tile;

import jfw.util.rendering.TileRenderer;

public interface Tile {

	Tile EMPTY = (renderer, column, row) -> {};

	void render(TileRenderer renderer, int column, int row);

}
