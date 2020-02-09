package jfw.util;

import org.junit.jupiter.api.Test;

import static jfw.util.Unicode.stringToCodePoint;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UnicodeTest {

	@Test
	void testStringToCodePoint() {
		assertThat(stringToCodePoint("🌳")).isEqualTo(127795);
	}

	@Test
	void testTooManySymbols() {
		assertThatIllegalArgumentException().
				isThrownBy(() -> stringToCodePoint("abc")).
				withMessage("symbol has 3 code points instead of 1!");
	}

	@Test
	void testBigSymbol() {
		assertThat(stringToCodePoint("\uD83D\uDD25")).
				isEqualTo(128293);
	}

	@Test
	void testCodePointToString() {
		assertThat(Unicode.codePointToString(127795)).isEqualTo("🌳");
	}

}