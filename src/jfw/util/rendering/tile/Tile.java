package jfw.util.rendering.tile;

import jfw.util.rendering.TileRenderer;

public interface Tile {

	void render(TileRenderer renderer, int column, int row);

}
