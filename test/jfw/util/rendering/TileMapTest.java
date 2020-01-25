package jfw.util.rendering;

import javafx.scene.paint.Color;
import jfw.util.OutsideMapException;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.UnicodeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static jfw.util.rendering.tile.Tile.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TileMapTest {

	private static final int NUMBER_OF_COLUMNS = 30;
	private static final int NUMBER_OF_ROWS = 40;
	private static final Color COLOR = Color.RED;

	private Tile tile;

	private TileMap tileMap;

	@BeforeEach
	void setUp() {
		tile = mock(Tile.class);

		tileMap = new TileMap(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS, EMPTY);
	}

	@Test
	void testDefaultTileIsNull() {
		assertThrows(NullPointerException.class, () -> new TileMap(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS, null));
	}

	@Nested
	class TestSetTile {

		@Test
		void testSetTile() {
			tileMap.setTile(tile, 2, 7);

			assertTile(tile, 2, 7);

			verifyNoInteractions(tile);
		}

		@Test
		void testSetTileWithNull() {
			assertThrows(NullPointerException.class, () -> tileMap.setTile(null, 2, 7));
		}

		@Test
		void testSetTileWithInvalidColumn() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> tileMap.setTile(tile, -1, 7));
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> tileMap.setTile(tile, NUMBER_OF_COLUMNS, 7));
		}

		@Test
		void testSetTilerWithInvalidRow() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> tileMap.setTile(tile, 5, -1));
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> tileMap.setTile(tile, 5, NUMBER_OF_ROWS));
		}
	}

	@Test
	void testRenderCenteredText() {
		tileMap.setCenteredText("test", 5, COLOR);
	}

	@Nested
	class TestSetText {

		@Test
		void testRenderText() {
			tileMap.setText("A test!", 2, 7, COLOR);

			assertUnicode("A", 2, 7);
			assertUnicode(" ", 3, 7);
			assertUnicode("t", 4, 7);
			assertUnicode("e", 5, 7);
			assertUnicode("s", 6, 7);
			assertUnicode("t", 7, 7);
			assertUnicode("!", 8, 7);
		}

		@Test
		void testRenderTextWithEndOutside() {
			tileMap.setText("A test!", 27, 7, COLOR);

			assertUnicode("A", 27, 7);
			assertUnicode(" ", 28, 7);
			assertUnicode("t", 29, 7);
		}

		@Test
		void testRenderTextWithStartOutside() {
			tileMap.setText("A test!", -3, 7, COLOR);

			assertUnicode("e", 0, 7);
			assertUnicode("s", 1, 7);
			assertUnicode("t", 2, 7);
			assertUnicode("!", 3, 7);
		}
	}

	private void assertTile(Tile tile, int x, int y) {
		assertThat(tileMap.getMap().getNode(x, y)).isEqualTo(tile);
	}

	private void assertUnicode(String symbol, int x, int y) {
		assertTile(new UnicodeTile(symbol, COLOR), x, y);
	}
}