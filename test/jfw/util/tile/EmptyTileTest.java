package jfw.util.tile;

import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EmptyTileTest {

	@Test
	void testRender() {
		TileRenderer renderer = mock(TileRenderer.class);
		EmptyTile emptyTile = new EmptyTile();

		emptyTile.render(renderer, 2, 3);

		verifyNoInteractions(renderer);
	}

}