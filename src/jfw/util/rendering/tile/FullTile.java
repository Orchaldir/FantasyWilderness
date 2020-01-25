package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import jfw.util.rendering.TileRenderer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor
@EqualsAndHashCode
public class FullTile implements Tile {

	@NonNull
	private final Color color;

	@Override
	public void render(TileRenderer renderer, int column, int row) {
		renderer.renderTile(column, row, color);
	}

}
