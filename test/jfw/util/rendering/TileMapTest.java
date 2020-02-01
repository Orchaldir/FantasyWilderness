package jfw.util.rendering;

import javafx.scene.paint.Color;
import jfw.util.OutsideMapException;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;
import jfw.util.rendering.tile.Tile;
import jfw.util.rendering.tile.TileSelector;
import jfw.util.rendering.tile.UnicodeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static jfw.util.rendering.tile.EmptyTile.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TileMapTest {

	private static final int NUMBER_OF_COLUMNS = 30;
	private static final int NUMBER_OF_ROWS = 40;
	private static final Color COLOR = Color.RED;
	private static final TileSelector<Integer> INTEGER_TILE_SELECTOR = i -> new UnicodeTile(i, COLOR);

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

	@Nested
	class TestSetMap {

		private Map2d<Integer> map;

		@BeforeEach
		void setUp() {
			Integer[] integers = {0, 1, 2, 3, 4, 5};
			map = new ArrayMap2d<>(3, 2, integers);
		}

		@Test
		void testSetMap() {
			tileMap.setMap(map, 4, 5, INTEGER_TILE_SELECTOR);

			assertUnicode(0, 4, 5);
			assertUnicode(1, 5, 5);
			assertUnicode(2, 6, 5);
			assertUnicode(3, 4, 6);
			assertUnicode(4, 5, 6);
			assertUnicode(5, 6, 6);
		}

		@Test
		void testSetMapOutside() {
			tileMap.setMap(map, -1, -1, INTEGER_TILE_SELECTOR);

			assertUnicode(4, 0, 0);
			assertUnicode(5, 1, 0);
		}
	}

	@Test
	void testRenderCenteredText() {
		tileMap.setCenteredText("Test", 5, COLOR);

		assertUnicode("T", 13, 5);
		assertUnicode("e", 14, 5);
		assertUnicode("s", 15, 5);
		assertUnicode("t", 16, 5);
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

	private void assertUnicode(int codePoint, int x, int y) {
		assertTile(new UnicodeTile(codePoint, COLOR), x, y);
	}
}