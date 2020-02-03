package jfw.util.tile.rendering;

import javafx.scene.paint.Color;
import jfw.util.rendering.Renderer;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TileRendererTest {

	private static final int START_X = 100;
	private static final int START_Y = 200;
	private static final int TILE_WIDTH = 10;
	private static final int TILE_HEIGHT = 20;
	private static final Color COLOR = Color.RED;

	private Renderer renderer;

	private TileRenderer tileRenderer;

	@BeforeEach
	void setUp() {
		renderer = mock(Renderer.class);

		tileRenderer = new TileRenderer(renderer, START_X, START_Y, TILE_WIDTH, TILE_HEIGHT);
	}

	@Test
	void testRendererIsNull() {
		assertThrows(NullPointerException.class, () -> new TileRenderer(null, START_X, START_Y,
				TILE_WIDTH, TILE_HEIGHT));
	}

	@Test
	void testRenderCharacterWithNullForColor() {
		assertThrows(NullPointerException.class, () -> tileRenderer.renderCharacter('T', 2, 7, null));
	}

	@Test
	void testRenderCharacter() {
		tileRenderer.renderCharacter('T', 2, 7, COLOR);

		int x = (int) (START_X + TILE_WIDTH * 2.5);
		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderCharacter('T', x, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderTileWithNullForColor() {
		assertThrows(NullPointerException.class, () -> tileRenderer.renderTile(2, 7, null));
	}

	@Test
	void testRenderTile() {
		tileRenderer.renderTile(3, 5, COLOR);

		int x = START_X + TILE_WIDTH * 3;
		int y = START_Y + TILE_HEIGHT * 5;

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderRectangle(x, y, TILE_WIDTH, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

}