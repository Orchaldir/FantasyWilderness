package jfw.game.state.world;

import javafx.scene.paint.Color;
import jfw.util.tile.FullTile;
import jfw.util.tile.Tile;
import jfw.util.tile.rendering.TileSelector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class WorldCell {

	private static final Tile PLAIN_TILE = new FullTile(Color.GREEN);
	private static final Tile HILL_TILE = new FullTile(Color.SADDLEBROWN);
	private static final Tile MOUNTAIN_TILE = new FullTile(Color.GREY);

	public static final TileSelector<WorldCell> TILE_SELECTOR = cell -> {
		switch (cell.getTerrainType()) {
			case PLAIN:
				return PLAIN_TILE;
			case HILL:
				return HILL_TILE;
			default:
				return MOUNTAIN_TILE;
		}
	};

	private final TerrainType terrainType;

}
