package jfw.util.tile;

import javafx.scene.paint.Color;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnicodeTileTest {

	@Test
	void testColorIsNull() {
		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile('c', null));

		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile("c", null));
	}

	@Test
	void testSymbolIsNull() {
		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile(null, Color.BLACK));
	}

	@Test
	void testRender() {
		TileRenderer renderer = mock(TileRenderer.class);
		UnicodeTile tile = new UnicodeTile("🔥", Color.RED);

		tile.render(renderer, 2, 3);

		verify(renderer).renderCharacter(128293, 2, 3, Color.RED);
		verifyNoMoreInteractions(renderer);
	}

}