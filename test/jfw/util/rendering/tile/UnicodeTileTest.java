package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UnicodeTileTest {

	@Test
	void testColorIsNull() {
		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile('c', null));

		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile("c", null));
	}

	@Test
	void testSymbolIsNull() {
		assertThatNullPointerException().
				isThrownBy(() -> new UnicodeTile(null, Color.BLACK));
	}

	@Test
	void testTooManySymbols() {
		assertThatIllegalArgumentException().
				isThrownBy(() -> new UnicodeTile("abc", Color.BLACK)).
				withMessage("symbol has 3 code points instead of 1!");
	}

	@Test
	void testBigSymbol() {
		assertThat(new UnicodeTile("\uD83D\uDD25", Color.BLACK).getCodePoint()).
				isEqualTo(128293);
	}

}