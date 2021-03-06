package jfw.util.map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class ArrayMap2dTest extends SharedData {

	// data

	private static final int X0 = 0, Y0 = 0, INDEX0 = 0;
	private static final int X1 = 2, Y1 = 0, INDEX1 = 2;
	private static final int X2 = 0, Y2 = 3, INDEX2 = 12;
	private static final int X3 = 2, Y3 = 1, INDEX3 = 6;

	private static final ArrayMap2d<Integer> MAP;

	static {
		MAP = new ArrayMap2d<>(WIDTH, HEIGHT, createArray());
	}
	
	// tests

	@Nested
	class TestConstructor {

		@Test
		void testInvalidWidth() {
			assertWidth(0);
			assertWidth(-1);
		}

		@Test
		void testInvalidHeight() {
			assertHeight(0);
			assertHeight(-1);
		}

		private void assertWidth(int width) {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(width, HEIGHT, createArray())).
					withMessage("'width' requires %d > 0!", width);
		}

		private void assertHeight(int height) {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, height, createArray())).
					withMessage("'height' requires %d > 0!", height);
		}

		@Test
		void testArrayIsNull() {
			assertThatExceptionOfType(NullPointerException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, HEIGHT, null)).
					withMessage("'cells' is null!");
		}

		@Test
		void testInvalidArrayLength() {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, 3, createArray())).
					withMessage("'cells' has length %d instead of %d!", 20, 12);
		}
	}

	@Nested
	class TestWith {

		private static final int INDEX = 5;
		private static final int CELL = 99;

		@Test
		void testWithSuccess() {
			ArrayMap2d<Integer> newMap = MAP.withCell(CELL, INDEX);

			for (int i = 0; i < SIZE; i++) {
				assertThat(MAP.getNode(i)).isEqualTo(i);
				assertThat(newMap.getNode(i)).isEqualTo(i == INDEX ? CELL : i);
			}
		}

		@Test
		void testWithNull() {
			assertThrows(NullPointerException.class, () -> MAP.withCell(null, INDEX), "cell is null!");
		}

		@Test
		void testWithInvalidIndex() {
			assertThrows(OutsideMapException.class, () -> MAP.withCell(CELL, -1), "Index -1 is outside map!");
		}
	}

	@Nested
	class TestGetters {

		@Test
		void testGetWidth() {
			assertThat(MAP.getWidth()).isEqualTo(WIDTH);
		}

		@Test
		void testGetHeight() {
			assertThat(MAP.getHeight()).isEqualTo(HEIGHT);
		}

		@Test
		void testGetSize() {
			assertThat(MAP.getSize()).isEqualTo(SIZE);
		}

		@Test
		void testGetIndex() {
			assertThat(MAP.getIndex(X0, Y0)).isEqualTo(INDEX0);
			assertThat(MAP.getIndex(X1, Y1)).isEqualTo(INDEX1);
			assertThat(MAP.getIndex(X2, Y2)).isEqualTo(INDEX2);
			assertThat(MAP.getIndex(X3, Y3)).isEqualTo(INDEX3);
		}

		@Test
		void testGetX() {
			assertThat(MAP.getX(INDEX0)).isEqualTo(X0);
			assertThat(MAP.getX(INDEX1)).isEqualTo(X1);
			assertThat(MAP.getX(INDEX2)).isEqualTo(X2);
			assertThat(MAP.getX(INDEX3)).isEqualTo(X3);
		}

		@Test
		void testGetY() {
			assertThat(MAP.getY(INDEX0)).isEqualTo(Y0);
			assertThat(MAP.getY(INDEX1)).isEqualTo(Y1);
			assertThat(MAP.getY(INDEX2)).isEqualTo(Y2);
			assertThat(MAP.getY(INDEX3)).isEqualTo(Y3);
		}
	}

	@Nested
	class TestIsInsideForX {

		@Test
		void testInside() {
			for (int x = 0; x < WIDTH; x++) {
				assertTrue(MAP.isInsideForX(x));
			}
		}

		@Test
		void testTooFarLeft() {
			assertFalse(MAP.isInsideForX(-1));
		}

		@Test
		void testTooFarRight() {
			assertFalse(MAP.isInsideForX(WIDTH));
		}
	}

	@Nested
	class TestIsInsideForY {

		@Test
		void testInside() {
			for (int y = 0; y < HEIGHT; y++) {
				assertTrue(MAP.isInsideForY(y));
			}
		}

		@Test
		void testBelow() {
			assertFalse(MAP.isInsideForY(-1));
		}

		@Test
		void testAbove() {
			assertFalse(MAP.isInsideForY(HEIGHT));
		}
	}

	@Nested
	class TestIsInside {

		@Test
		void testInside() {
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					assertTrue(MAP.isInside(x, y));
				}
			}
		}

		@Test
		void testOutsideCorners() {
			assertFalse(MAP.isInside(-1, -1));
			assertFalse(MAP.isInside(-1, HEIGHT));
			assertFalse(MAP.isInside(WIDTH, -1));
			assertFalse(MAP.isInside(WIDTH, HEIGHT));

		}

		@Test
		void testTooFarLeft() {
			for (int y = 0; y < HEIGHT; y++) {
				assertFalse(MAP.isInside(-1, y));
			}
		}

		@Test
		void testTooFarRight() {
			for (int y = 0; y < HEIGHT; y++) {
				assertFalse(MAP.isInside(WIDTH, y));
			}
		}

		@Test
		void testBelowTheMap() {
			for (int x = 0; x < WIDTH; x++) {
				assertFalse(MAP.isInside(x, -1));
			}
		}

		@Test
		void testAboveTheMap() {
			for (int x = 0; x < WIDTH; x++) {
				assertFalse(MAP.isInside(x, HEIGHT));
			}
		}
	}

	@Nested
	class TestIsInsideWithIndex {

		@Test
		void testInside() {
			for (int index = 0; index < SIZE; index++) {
				assertTrue(MAP.isInside(index));
			}
		}

		@Test
		void testOutside() {
			assertFalse(MAP.isInside(-1));
			assertFalse(MAP.isInside(SIZE));
		}
	}

	@Nested
	class TestGetCell {

		@Test
		void testGetCell() {
			int i = 0;

			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					assertThat(MAP.getNode(x, y)).isEqualTo(i);
					assertThat(MAP.getNode(i)).isEqualTo(i);
					i++;
				}
			}
		}

		@Test
		void testCoordinatesAreOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> MAP.getNode(-11, -2)).
					withMessage("Column -11 & row -2 is outside map!");
		}

		@Test
		void testIndexIsOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> MAP.getNode(-3)).
					withMessage("Index -3 is outside map!");
		}
	}

	@Nested
	class TestGetNeighborIndex {

		@Test
		void testNorth() {
			assertNeighbor(Direction.NORTH, 2);
		}

		@Test
		void testEast() {
			assertNeighbor(Direction.EAST, 7);
		}

		@Test
		void testSouth() {
			assertNeighbor(Direction.SOUTH, 10);
		}

		@Test
		void testWest() {
			assertNeighbor(Direction.WEST, 5);
		}

		@Test
		void testNorthBorder() {
			assertThat(MAP.getNeighborIndex(2, Direction.NORTH)).isEqualTo(Optional.empty());
		}

		@Test
		void testEastBorder() {
			assertThat(MAP.getNeighborIndex(7, Direction.EAST)).isEqualTo(Optional.empty());
		}

		@Test
		void testSouthBorder() {
			assertThat(MAP.getNeighborIndex(22, Direction.SOUTH)).isEqualTo(Optional.empty());
		}

		@Test
		void testWestBorder() {
			assertThat(MAP.getNeighborIndex(4, Direction.WEST)).isEqualTo(Optional.empty());
		}

		private void assertNeighbor(Direction direction, int neighbor) {
			assertThat(MAP.getNeighborIndex(INDEX3, direction)).isEqualTo(Optional.of(neighbor));
		}
	}

	@Test
	void testToString() {
		assertThat(MAP.toString()).isEqualTo("[4*5]");
	}

}