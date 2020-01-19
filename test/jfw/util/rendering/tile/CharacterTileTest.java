package jfw.util.rendering.tile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTileTest {

	@Test
	void testColorIsNull() {
		assertThrows(NullPointerException.class, () -> new CharacterTile('c', null));
	}

}