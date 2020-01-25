package jfw.game.state.world;

import jfw.util.map.ArrayMap2d;

public class WorldMap extends ArrayMap2d<WorldCell> {

	public WorldMap(int width, int height, WorldCell[] cells) {
		super(width, height, cells);
	}

}
