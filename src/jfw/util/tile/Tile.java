package jfw.util.tile;

import jfw.util.tile.rendering.TileRenderer;

public interface Tile {

	void render(TileRenderer renderer, int column, int row);

}
