package jfw.util.rendering;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;

class TileMapTest {

	private static final int START_X = 100;
	private static final int START_Y = 200;
	private static final int TILE_WIDTH = 10;
	private static final int TILE_HEIGHT = 20;
	private static final int NUMBER_OF_COLUMNS = 30;
	private static final int NUMBER_OF_ROWS = 40;

	private Renderer renderer;
	private Color color = Color.RED;

	private TileMap tileMap;

	@BeforeEach
	void setUp() {
		renderer = mock(Renderer.class);

		tileMap = new TileMap(renderer, START_X, START_Y,
				TILE_WIDTH, TILE_HEIGHT, NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
	}

	@Test
	void testClear() {
		tileMap.clear();

		verify(renderer).clear(START_X, START_Y,
				TILE_WIDTH * NUMBER_OF_COLUMNS, TILE_HEIGHT * NUMBER_OF_ROWS);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderCharacter() {
		tileMap.renderCharacter('T', 2, 7, color);

		int x = (int) (START_X + TILE_WIDTH * 2.5);
		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(color));
		verify(renderer).renderCharacter('T', x, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderText() {
		tileMap.renderText("A test!", 2, 7, color);

		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(color));
		verify(renderer).renderCharacter('A', 125, y, TILE_HEIGHT);
		verify(renderer).renderCharacter(' ', 135, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('t', 145, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('e', 155, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('s', 165, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('t', 175, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('!', 185, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderTile() {
		tileMap.renderTile(3, 5, color);

		int x = START_X + TILE_WIDTH * 3;
		int y = START_Y + TILE_HEIGHT * 5;

		verify(renderer).setColor(ArgumentMatchers.eq(color));
		verify(renderer).renderRectangle(x, y, TILE_WIDTH, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

}