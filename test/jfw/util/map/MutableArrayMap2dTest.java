package jfw.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MutableArrayMap2dTest extends SharedData {

	private static final int VALUE = 42;

	private Integer[] array;
	private MutableMap2d<Integer> map;

	@BeforeEach
	void setUp() {
		array = createArray();
		map = new MutableArrayMap2d<>(WIDTH, HEIGHT, array);
	}

	@Nested
	class TestSetCell {

		@Test
		public void testSetCellWithIndex() {
			map.setNode(VALUE, 9);

			assertThat(array[9]).isEqualTo(VALUE);
		}

		@Test
		public void testSetCellWithCoordinates() {
			map.setNode(VALUE, 2, 3);

			assertThat(array[14]).isEqualTo(VALUE);
		}

		@Test
		public void testCoordinatesAreOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> map.setNode(0, -11, -2)).
					withMessage("Column -11 & row -2 is outside map!");
		}

		@Test
		public void testIndexIsOutside() {
			assertThatExceptionOfType(OutsideMapException.class).
					isThrownBy(() -> map.setNode(0, -3)).
					withMessage("Index -3 is outside map!");
		}
	}

}