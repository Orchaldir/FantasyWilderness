package jfw.util;

import org.junit.jupiter.api.Test;

import static jfw.util.Validator.validateGreater;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ValidatorTest {

	private static final int THRESHOLD = 2;

	@Test
	void testRequireGreaterIsFalse() {
		for (int i = -2; i <= THRESHOLD; i++) {
			final int finalI = i;
			assertThatIllegalArgumentException().
					isThrownBy(() -> validateGreater(finalI, THRESHOLD, "test"));
		}
	}

	@Test
	void testRequireGreaterIsTrue() {
		for (int i = THRESHOLD+1; i <= THRESHOLD+4; i++) {
			assertThat(validateGreater(i, THRESHOLD, "test")).isEqualTo(i);
		}
	}

}