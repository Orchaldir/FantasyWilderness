package jfw.util.rendering.tile;

import jfw.util.rendering.TileMap;

public interface Tile {

	void render(TileMap tileMap, int column, int row);

}
