package jfw.util.redux;

import jfw.util.redux.middleware.Middleware;
import lombok.Getter;

import java.util.*;

import static jfw.util.Validator.validateNotNull;

public class Store<A, S> {

	private final Set<Subscriber<S>> consumers = new HashSet<>();
	private final Reducer<A, S> reducer;
	private final Dispatcher<A> dispatcher;

	@Getter
	private S state;

	public Store(Reducer<A, S> reducer, S state) {
		this(reducer, state, Collections.emptyList());
	}

	public Store(Reducer<A, S> reducer, S state, List<Middleware<A, S>> middlewares) {
		this.reducer = validateNotNull(reducer, "reducer");
		this.state = validateNotNull(state, "state");

		Dispatcher<A> wrappedDispatcher = action -> {
			validateNotNull(action, "action");

			S newState = this.reducer.reduce(action, this.state);

			if (newState != this.state) {
				this.state = newState;

				notifyConsumers();
			}
		};

		for (Middleware<A, S> middleware : middlewares) {
			wrappedDispatcher = middleware.apply(wrappedDispatcher, this::getState);
		}

		dispatcher = wrappedDispatcher;
	}

	public void dispatch(A action) {
		dispatcher.dispatch(action);
	}

	public Subscription subscribe(Subscriber<S> consumer) {
		validateNotNull(consumer, "consumer");
		consumers.add(consumer);

		consumer.onStateChanged(state);

		return () -> consumers.remove(consumer);
	}

	private void notifyConsumers() {
		for (Subscriber<S> consumer : consumers) {
			consumer.onStateChanged(state);
		}
	}
}
