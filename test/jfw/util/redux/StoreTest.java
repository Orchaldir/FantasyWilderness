package jfw.util.redux;

import jfw.util.redux.middleware.Middleware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class StoreTest {

	private static final String ACTION0 = "action0";

	private static final Integer STATE0 = 100;
	private static final Integer STATE1 = 200;

	@Mock
	private Subscriber<Integer> consumer0;

	@Mock
	private Subscriber<Integer> consumer1;

	@Mock
	private Reducer<String, Integer> reducer;

	@Mock
	private Middleware<String, Integer> middleware;

	@Mock
	private Dispatcher<String> dispatcher;

	private Store<String, Integer> store;

	@BeforeEach
	void setUp() {
		store = new Store<>(reducer, STATE0);
	}

	@Test
	void testInvalidReducer() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() -> new Store<>(null, STATE0)).
				withMessage("'reducer' is null!");
	}

	@Test
	void testInvalidState() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() -> new Store<>(reducer, null)).
				withMessage("'state' is null!");
	}

	@Test
	void testGetInitialState() {
		assertThat(store.getState()).isEqualTo(STATE0);
	}

	@Test
	void testSubscribeWithNull() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() ->store.subscribe(null)).
				withMessage("'consumer' is null!");
	}

	@Test
	void testPublishAfterSubscribe() {
		store.subscribe(consumer0);

		verify(consumer0).onStateChanged(STATE0);
		verifyNoMoreInteractions(consumer0);
		verifyNoInteractions(reducer);
	}

	@Test
	void testDispatchWithNull() {
		assertThatExceptionOfType(NullPointerException.class).
				isThrownBy(() ->store.dispatch(null)).
				withMessage("'action' is null!");
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

		verify(consumer0).onStateChanged(STATE0);
		verify(consumer0).onStateChanged(STATE1);
		verifyNoMoreInteractions(consumer0);
		verifyNoInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}

	@Test
	void testDispatchButNoStateChange() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE0);

		store.subscribe(consumer0);
		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE0);

		verify(consumer0).onStateChanged(STATE0);
		verifyNoMoreInteractions(consumer0);
		verifyNoInteractions(consumer1);
		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}

	@Test
	void testMiddleware() {
		when(middleware.apply(any(), any())).thenReturn(dispatcher);

		store = new Store<>(reducer, STATE0, List.of(middleware));

		store.subscribe(consumer0);
		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE0);

		verify(consumer0).onStateChanged(STATE0);
		verifyNoMoreInteractions(consumer0);
		verifyNoInteractions(consumer1);
		verifyNoInteractions(reducer);
		verify(middleware).apply(any(), any());
		verifyNoMoreInteractions(middleware);
		verify(dispatcher).dispatch(ACTION0);
		verifyNoMoreInteractions(dispatcher);
	}

	@Test
	void testDispatchWithTwoConsumers() {
		when(reducer.reduce(ACTION0, STATE0)).thenReturn(STATE1);

		store.subscribe(consumer0);
		store.subscribe(consumer1);
		store.dispatch(ACTION0);

		assertThat(store.getState()).isEqualTo(STATE1);

		verify(consumer0).onStateChanged(STATE0);
		verify(consumer0).onStateChanged(STATE1);
		verifyNoMoreInteractions(consumer0);

		verify(consumer1).onStateChanged(STATE0);
		verify(consumer1).onStateChanged(STATE1);
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

		verify(consumer0).onStateChanged(STATE0);
		verifyNoMoreInteractions(consumer0);

		verify(consumer1).onStateChanged(STATE0);
		verify(consumer1).onStateChanged(STATE1);
		verifyNoMoreInteractions(consumer1);

		verify(reducer).reduce(ACTION0, STATE0);
		verifyNoMoreInteractions(reducer);
	}
}