package jfw.util.tile;

import javafx.scene.paint.Color;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FullTileTest {

	@Test
	void testColorIsNull() {
		assertThrows(NullPointerException.class, () -> new FullTile(null));
	}

	@Test
	void testRender() {
		TileRenderer renderer = mock(TileRenderer.class);
		FullTile fullTile = new FullTile(Color.GREEN);

		fullTile.render(renderer, 2, 3);

		verify(renderer).renderTile(2, 3, Color.GREEN);
		verifyNoMoreInteractions(renderer);
	}

}