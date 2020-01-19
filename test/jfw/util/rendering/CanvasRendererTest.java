package jfw.util.rendering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasRendererTest {

	@Test
	void testGraphicsContextIsNull() {
		assertThrows(NullPointerException.class, () -> new CanvasRenderer(null));
	}

}