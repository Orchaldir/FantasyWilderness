package jfw.util.rendering.tile;

public interface TileSelector<T> {

	Tile select(T parameter);

}
