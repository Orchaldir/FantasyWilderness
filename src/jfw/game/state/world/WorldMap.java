package jfw.game.state.world;

import jfw.util.map.ArrayMap2d;
import lombok.Getter;

@Getter
public class WorldMap {

	private final ArrayMap2d<WorldCell> cells;

	public WorldMap(int width, int height, WorldCell[] cells) {
		this.cells = new ArrayMap2d<>(width, height, cells);
	}

	public WorldMap(int width, int height, WorldCell defaultCell) {
		int size = width * height;
		WorldCell[] newCells = new WorldCell[size];
		this.cells = new ArrayMap2d<>(width, height, newCells, defaultCell);
	}

}
