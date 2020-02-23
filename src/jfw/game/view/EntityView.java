package jfw.game.view;

import jfw.util.ecs.ComponentStorage;
import jfw.util.map.Map2d;
import jfw.util.tile.Tile;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileConverter;

public interface EntityView {

	static void view(ComponentStorage<Integer> positions, TileMap tileMap, TileConverter<Integer> tileConverter) {
		Map2d<Tile> map = tileMap.getMap();

		positions.visit((id, position) ->
				tileMap.setTile(tileConverter.convert(id), map.getX(position), map.getY(position)));
	}
}
