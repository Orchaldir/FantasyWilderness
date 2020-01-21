package jfw.util.redux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreTest {

	private static final String ACTION0 = "action0";

	private static final Integer STATE0 = 100;
	private static final Integer STATE1 = 200;

	@Mock
	private Reducer<String, Integer> reducer;
	private Store<String, Integer> store;

	@BeforeEach
	void setUp() {
		store = new Store<>(reducer, STATE0);
	}

	@Test
	void testInvalidReducer() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() -> new Store<>(null, STATE0)).
				withMessage("reducer is null!");
	}

	@Test
	void testInvalidState() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() -> new Store<>(reducer, null)).
				withMessage("state is null!");
	}

	@Test
	void testGetInitialState() {
		assertThat(store.getState()).isEqualTo(STATE0);
	}

	@Test
	void testDispatch() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		assertThat(store.dispatch(ACTION0)).isEqualTo(STATE1);
		assertThat(store.getState()).isEqualTo(STATE1);

		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}
}