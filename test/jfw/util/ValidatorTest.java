package jfw.util;

import org.junit.jupiter.api.Test;

import static jfw.util.Validator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

	private static final int THRESHOLD = 2;

	@Test
	void testRequireGreaterIsFalse() {
		for (int i = -2; i <= THRESHOLD; i++) {
			final int finalI = i;
			assertThatIllegalArgumentException().
					isThrownBy(() -> validateGreater(finalI, THRESHOLD, "int"));
		}
	}

	@Test
	void testRequireGreaterLongIsFalse() {
		for (long i = -2; i <= THRESHOLD; i++) {
			final long finalInt = i;
			assertThatIllegalArgumentException().
					isThrownBy(() -> validateGreater(finalInt, THRESHOLD, "long"));
		}
	}

	@Test
	void testRequireGreaterIsTrue() {
		for (int i = THRESHOLD+1; i <= THRESHOLD+4; i++) {
			assertThat(validateGreater(i, THRESHOLD, "int")).isEqualTo(i);
		}
	}

	@Test
	void testRequireGreaterLongIsTrue() {
		for (long i = THRESHOLD+1; i <= THRESHOLD+4; i++) {
			assertThat(validateGreater(i, THRESHOLD, "long")).isEqualTo(i);
		}
	}

	@Test
	void testValidateTrue() {
		assertTrue(validateTrue(true, "test"));
		assertThatIllegalArgumentException().
				isThrownBy(() -> validateTrue(false, "test")).
				withMessage("'test' must be true!");
	}

	@Test
	void testValidateFalse() {
		assertFalse(validateFalse(false, "test"));
		assertThatIllegalArgumentException().
				isThrownBy(() -> validateFalse(true, "test")).
				withMessage("'test' must be false!");
	}

}