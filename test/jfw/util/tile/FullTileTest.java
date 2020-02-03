package jfw.util.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FullTileTest {

	@Test
	void testColorIsNull() {
		assertThrows(NullPointerException.class, () -> new FullTile(null));
	}

}