package jfw.util.redux.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RandomNumberGeneratorTest {

	private static final int START_INDEX = 2;

	private RandomNumberState state0;
	private RandomNumberState state1;
	private RandomNumberGenerator generator;

	@BeforeEach
	void setup() {
		state0 = mock(RandomNumberState.class);
		state1 = mock(RandomNumberState.class);

		when(state0.getIndex()).thenReturn(START_INDEX);

		generator= new RandomNumberGenerator(state0);

		verify(state0).getIndex();
	}

	@Test
	void testGetInt() {
		when(state0.getNumber(START_INDEX)).thenReturn(100);

		assertThat(generator.getInt()).isEqualTo(100);

		verify(state0).getNumber(START_INDEX);
		verifyNoMoreInteractions(state0);
	}

	@Test
	void testGetIntTwice() {
		when(state0.getNumber(START_INDEX)).thenReturn(5);
		when(state0.getNumber(START_INDEX+1)).thenReturn(10);

		assertThat(generator.getInt()).isEqualTo(5);
		assertThat(generator.getInt()).isEqualTo(10);

		verify(state0).getNumber(START_INDEX);
		verify(state0).getNumber(START_INDEX+1);
		verifyNoMoreInteractions(state0);
	}

	@Test
	void testRollDice() {
		when(state0.getNumber(START_INDEX)).thenReturn(3);

		assertThat(generator.rollDice(6)).isEqualTo(4);

		verify(state0).getNumber(START_INDEX);
		verifyNoMoreInteractions(state0);
	}

	@Test
	void testRollDiceWithBiggerNumber() {
		when(state0.getNumber(START_INDEX)).thenReturn(8);

		assertThat(generator.rollDice(6)).isEqualTo(3);

		verify(state0).getNumber(START_INDEX);
		verifyNoMoreInteractions(state0);
	}

	@Test
	void testRollPositiveAndNegativeDice() {
		when(state0.getNumber(START_INDEX)).thenReturn(3);
		when(state0.getNumber(START_INDEX+1)).thenReturn(5);

		assertThat(generator.rollPositiveAndNegativeDice(6)).isEqualTo(-2);

		verify(state0).getNumber(START_INDEX);
		verify(state0).getNumber(START_INDEX+1);
		verifyNoMoreInteractions(state0);
	}

	@Test
	void testGetUpdateState() {
		when(state0.with(START_INDEX)).thenReturn(state1);

		assertThat(generator.updateState()).isEqualTo(state1);

		verify(state0).with(START_INDEX);
		verifyNoMoreInteractions(state0);
	}

}