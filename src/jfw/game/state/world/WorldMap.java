package jfw.game.state.world;

import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;

public class WorldMap {

	private final Map2d<WorldCell> cells;

	public WorldMap(int width, int height, WorldCell[] cells) {
		this.cells = new ArrayMap2d<>(width, height, cells);
	}

	public WorldMap(int width, int height, WorldCell defaultCell) {
		int size = width * height;
		WorldCell[] cells = new WorldCell[size];
		this.cells = new ArrayMap2d<>(width, height, cells, defaultCell);
	}

}
