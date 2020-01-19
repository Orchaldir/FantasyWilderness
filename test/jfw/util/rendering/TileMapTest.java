package jfw.util.rendering;

import javafx.scene.paint.Color;
import jfw.util.OutsideMapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TileMapTest {

	private static final int START_X = 100;
	private static final int START_Y = 200;
	private static final int TILE_WIDTH = 10;
	private static final int TILE_HEIGHT = 20;
	private static final int NUMBER_OF_COLUMNS = 30;
	private static final int NUMBER_OF_ROWS = 40;
	private static final Color COLOR = Color.RED;

	private Renderer renderer;

	private TileMap tileMap;

	@BeforeEach
	void setUp() {
		renderer = mock(Renderer.class);

		tileMap = new TileMap(renderer, START_X, START_Y,
				TILE_WIDTH, TILE_HEIGHT, NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
	}

	@Test
	void testRendererIsNull() {
		assertThrows(NullPointerException.class, () -> new TileMap(null, START_X, START_Y,
				TILE_WIDTH, TILE_HEIGHT, NUMBER_OF_COLUMNS, NUMBER_OF_ROWS));
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
		tileMap.renderCharacter('T', 2, 7, COLOR);

		int x = (int) (START_X + TILE_WIDTH * 2.5);
		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderCharacter('T', x, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderCharacterWithInvalidColumn() {
		assertThatExceptionOfType(OutsideMapException.class).
				isThrownBy(() -> tileMap.renderCharacter('T', -1, 7, COLOR));
		assertThatExceptionOfType(OutsideMapException.class).
				isThrownBy(() -> tileMap.renderCharacter('T', NUMBER_OF_COLUMNS, 7, COLOR));
	}

	@Test
	void testRenderCharacterWithInvalidRow() {
		assertThatExceptionOfType(OutsideMapException.class).
				isThrownBy(() -> tileMap.renderCharacter('T', 5, -1, COLOR));
		assertThatExceptionOfType(OutsideMapException.class).
				isThrownBy(() -> tileMap.renderCharacter('T', 5, NUMBER_OF_ROWS, COLOR));
	}

	@Test
	void testRenderCenteredText() {
		tileMap.renderCenteredText("test", 5, COLOR);

		int y = (int) (START_Y + TILE_HEIGHT * 5.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderCharacter('t', 235, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('e', 245, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('s', 255, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('t', 265, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderText() {
		tileMap.renderText("A test!", 2, 7, COLOR);

		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
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
	void testRenderTextWithEndOutside() {
		tileMap.renderText("A test!", 27, 7, COLOR);

		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderCharacter('A', 375, y, TILE_HEIGHT);
		verify(renderer).renderCharacter(' ', 385, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('t', 395, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderTextWithStartOutside() {
		tileMap.renderText("A test!", -3, 7, COLOR);

		int y = (int) (START_Y + TILE_HEIGHT * 7.5);

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderCharacter('e', 105, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('s', 115, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('t', 125, y, TILE_HEIGHT);
		verify(renderer).renderCharacter('!', 135, y, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

	@Test
	void testRenderTile() {
		tileMap.renderTile(3, 5, COLOR);

		int x = START_X + TILE_WIDTH * 3;
		int y = START_Y + TILE_HEIGHT * 5;

		verify(renderer).setColor(ArgumentMatchers.eq(COLOR));
		verify(renderer).renderRectangle(x, y, TILE_WIDTH, TILE_HEIGHT);
		verifyNoMoreInteractions(renderer);
	}

}