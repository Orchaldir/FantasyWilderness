package jfw.util.map;

import jfw.util.OutsideMapException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class ArrayMap2dTest extends SharedData {

	// data

	private static final int X0 = 0, Y0 = 0, INDEX0 = 0;
	private static final int X1 = 2, Y1 = 0, INDEX1 = 2;
	private static final int X2 = 0, Y2 = 3, INDEX2 = 12;
	private static final int X3 = 2, Y3 = 1, INDEX3 = 6;

	private static final Map2d<Integer> MAP;

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
		public void testInvalidHeight() {
			assertHeight(0);
			assertHeight(-1);
		}

		private void assertWidth(int width) {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(width, HEIGHT, createArray())).
					withMessage("%s requires %d > %d", "width", width, 0);
		}

		private void assertHeight(int height) {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, height, createArray())).
					withMessage("%s requires %d > %d", "height", height, 0);
		}

		@Test
		public void testArrayIsNull() {
			assertThatExceptionOfType(NullPointerException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, HEIGHT, null)).
					withMessage("cells is null!");
		}

		@Test
		public void testInvalidArrayLength() {
			assertThatExceptionOfType(IllegalArgumentException.class).
					isThrownBy(() -> new ArrayMap2d<>(WIDTH, 3, createArray())).
					withMessage("cells has length %d instead of %d!", 20, 12);
		}
	}

	@Nested
	class TestGetters {

		@Test
		public void testGetWidth() {
			assertThat(MAP.getWidth()).isEqualTo(WIDTH);
		}

		@Test
		public void testGetHeight() {
			assertThat(MAP.getHeight()).isEqualTo(HEIGHT);
		}

		@Test
		public void testGetSize() {
			assertThat(MAP.getSize()).isEqualTo(SIZE);
		}

		@Test
		public void testGetIndex() {
			assertThat(MAP.getIndex(X0, Y0)).isEqualTo(INDEX0);
			assertThat(MAP.getIndex(X1, Y1)).isEqualTo(INDEX1);
			assertThat(MAP.getIndex(X2, Y2)).isEqualTo(INDEX2);
			assertThat(MAP.getIndex(X3, Y3)).isEqualTo(INDEX3);
		}

		@Test
		public void testGetX() {
			assertThat(MAP.getX(INDEX0)).isEqualTo(X0);
			assertThat(MAP.getX(INDEX1)).isEqualTo(X1);
			assertThat(MAP.getX(INDEX2)).isEqualTo(X2);
			assertThat(MAP.getX(INDEX3)).isEqualTo(X3);
		}

		@Test
		public void testGetY() {
			assertThat(MAP.getY(INDEX0)).isEqualTo(Y0);
			assertThat(MAP.getY(INDEX1)).isEqualTo(Y1);
			assertThat(MAP.getY(INDEX2)).isEqualTo(Y2);
			assertThat(MAP.getY(INDEX3)).isEqualTo(Y3);
		}
	}

	@Nested
	class TestIsInsideForX {

		@Test
		public void testInside() {
			for (int x = 0; x < WIDTH; x++) {
				assertTrue(MAP.isInsideForX(x));
			}
		}

		@Test
		public void testTooFarLeft() {
			assertFalse(MAP.isInsideForX(-1));
		}

		@Test
		public void testTooFarRight() {
			assertFalse(MAP.isInsideForX(WIDTH));
		}
	}

	@Nested
	class TestIsInsideForY {

		@Test
		public void testInside() {
			for (int y = 0; y < HEIGHT; y++) {
				assertTrue(MAP.isInsideForY(y));
			}
		}

		@Test
		public void testBelow() {
			assertFalse(MAP.isInsideForY(-1));
		}

		@Test
		public void testAbove() {
			assertFalse(MAP.isInsideForY(HEIGHT));
		}
	}

	@Nested
	class TestIsInside {

		@Test
		public void testInside() {
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					assertTrue(MAP.isInside(x, y));
				}
			}
		}

		@Test
		public void testOutsideCorners() {
			assertFalse(MAP.isInside(-1, -1));
			assertFalse(MAP.isInside(-1, HEIGHT));
			assertFalse(MAP.isInside(WIDTH, -1));
			assertFalse(MAP.isInside(WIDTH, HEIGHT));

		}

		@Test
		public void testTooFarLeft() {
			for (int y = 0; y < HEIGHT; y++) {
				assertFalse(MAP.isInside(-1, y));
			}
		}

		@Test
		public void testTooFarRight() {
			for (int y = 0; y < HEIGHT; y++) {
				assertFalse(MAP.isInside(WIDTH, y));
			}
		}

		@Test
		public void testBelowTheMap() {
			for (int x = 0; x < WIDTH; x++) {
				assertFalse(MAP.isInside(x, -1));
			}
		}

		@Test
		public void testAboveTheMap() {
			for (int x = 0; x < WIDTH; x++) {
				assertFalse(MAP.isInside(x, HEIGHT));
			}
		}
	}

	@Nested
	class TestIsInsideWithIndex {

		@Test
		public void testInside() {
			for (int index = 0; index < SIZE; index++) {
				assertTrue(MAP.isInside(index));
			}
		}

		@Test
		public void testOutside() {
			assertFalse(MAP.isInside(-1));
			assertFalse(MAP.isInside(SIZE));
		}
	}

	@Nested
	class TestGetCell {

		@Test
		public void testGetCell() {
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
		public void testCoordinatesAreOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> MAP.getNode(-11, -2)).
					withMessage("Column -11 & row -2 is outside map!");
		}

		@Test
		public void testIndexIsOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> MAP.getNode(-3)).
					withMessage("Index -3 is outside map!");
		}
	}

}