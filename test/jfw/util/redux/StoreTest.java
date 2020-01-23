package jfw.util.redux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreTest {

	private static final String ACTION0 = "action0";

	private static final Integer STATE0 = 100;
	private static final Integer STATE1 = 200;

	@Mock
	private Consumer<Integer> consumer0;

	@Mock
	private Consumer<Integer> consumer1;

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
	void testSubscribeWithNull() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() ->store.subscribe(null)).
				withMessage("consumer is null!");
	}

	@Test
	void testDispatchWithNull() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() ->store.dispatch(null)).
				withMessage("action is null!");
	}

	@Test
	void testDispatchWithNoConsumer() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE1);

		verifyNoInteractions(consumer0);
		verifyNoInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}

	@Test
	void testDispatchWithOneConsumer() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		store.subscribe(consumer0);
		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE1);

		verify(consumer0).accept(STATE1);
		verifyNoMoreInteractions(consumer0);
		verifyNoInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}

	@Test
	void testDispatchWithTwoConsumers() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		store.subscribe(consumer0);
		store.subscribe(consumer1);
		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE1);

		verify(consumer0).accept(STATE1);
		verifyNoMoreInteractions(consumer0);
		verify(consumer1).accept(STATE1);
		verifyNoMoreInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}

	@Test
	void testUnsubscribe() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		Subscription subscription = store.subscribe(consumer0);
		store.subscribe(consumer1);
		subscription.unsubscribe();

		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE1);

		verifyNoInteractions(consumer0);
		verify(consumer1).accept(STATE1);
		verifyNoMoreInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}
}