package jfw.game.content.skill;

import org.junit.jupiter.api.Test;

import static jfw.game.content.skill.Result.*;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

	@Test
	void testIsSuccess() {
		assertTrue(CRITICAL_SUCCESS.isSuccess());
		assertTrue(SUCCESS.isSuccess());
		assertFalse(DRAW.isSuccess());
		assertFalse(FAILURE.isSuccess());
		assertFalse(CRITICAL_FAILURE.isSuccess());
	}

	@Test
	void testIsSuccessOrDraw() {
		assertTrue(CRITICAL_SUCCESS.isSuccessOrDraw());
		assertTrue(SUCCESS.isSuccessOrDraw());
		assertTrue(DRAW.isSuccessOrDraw());
		assertFalse(FAILURE.isSuccessOrDraw());
		assertFalse(CRITICAL_FAILURE.isSuccessOrDraw());
	}

	@Test
	void testIsFailure() {
		assertFalse(CRITICAL_SUCCESS.isFailure());
		assertFalse(SUCCESS.isFailure());
		assertFalse(DRAW.isFailure());
		assertTrue(FAILURE.isFailure());
		assertTrue(CRITICAL_FAILURE.isFailure());
	}

}