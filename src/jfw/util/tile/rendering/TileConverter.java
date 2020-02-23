package jfw.util.tile.rendering;

import jfw.util.tile.Tile;

public interface TileConverter<T> {

	Tile select(T parameter);

}
