package jfw.util.tile.rendering;

import jfw.util.tile.Tile;

public interface TileSelector<T> {

	Tile select(T parameter);

}
