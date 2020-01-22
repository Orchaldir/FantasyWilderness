package jfw.util.rendering.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnicodeTileTest {

	@Test
	void testColorIsNull() {
		assertThrows(NullPointerException.class, () -> new UnicodeTile('c', null));
	}

}