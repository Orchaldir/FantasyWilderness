package jfw.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OutsideMapExceptionTest {

	@Test
	void testMessageWithIndex() {
		assertThat(new OutsideMapException(9)).hasMessage("Index 9 is outside map!");
	}

	@Test
	void testMessageWithColumnAndRow() {
		assertThat(new OutsideMapException(3, 4)).hasMessage("Column 3 & row 4 is outside map!");
	}

}