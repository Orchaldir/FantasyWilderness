package jfw.util.redux.random;

import org.javers.common.collections.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RandomNumberStateTest {

	private static final int[] NUMBERS = Arrays.intArray(1, 2, 3, 4);

	private static final int INDEX0 = 2;
	private static final int INDEX1 = 5;

	private RandomNumberState state;

	@BeforeEach
	void setup() {
		state = new RandomNumberState(NUMBERS, INDEX0);
	}

	@Test
	void testConstructor() {
		Random random = mock(Random.class);

		when(random.nextInt()).thenReturn(30, 20, 10);

		state = new RandomNumberState(random, 3);

		assertThat(state.getIndex()).isEqualTo(0);

		assertThat(state.getNumber(0)).isEqualTo(30);
		assertThat(state.getNumber(1)).isEqualTo(20);
		assertThat(state.getNumber(2)).isEqualTo(10);
		assertThat(state.getNumber(3)).isEqualTo(30);
	}

	@Test
	void testGetIndex() {
		assertThat(state.getIndex()).isEqualTo(INDEX0);
	}

	@Test
	void testGetIndexAfterGetNumber() {
		state.getNumber(0);

		assertThat(state.getIndex()).isEqualTo(INDEX0);
	}

	@Test
	void testWith() {
		RandomNumberState newState = state.with(INDEX1);

		assertThat(state.getIndex()).isEqualTo(INDEX0);
		assertThat(newState.getIndex()).isEqualTo(INDEX1);
	}

	@Test
	void testWithSameIndex() {
		assertThat(state.with(INDEX0)).isEqualTo(state);
	}

	@Test
	void testGetNumber() {
		assertThat(state.getNumber(0)).isEqualTo(1);
		assertThat(state.getNumber(1)).isEqualTo(2);
		assertThat(state.getNumber(2)).isEqualTo(3);
		assertThat(state.getNumber(3)).isEqualTo(4);
		assertThat(state.getNumber(4)).isEqualTo(1);
		assertThat(state.getNumber(5)).isEqualTo(2);
		assertThat(state.getNumber(6)).isEqualTo(3);
		assertThat(state.getNumber(7)).isEqualTo(4);
	}

	@Test
	void testGetNumberForNegativeIndex() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> state.getNumber(-1), "-1");
	}

}