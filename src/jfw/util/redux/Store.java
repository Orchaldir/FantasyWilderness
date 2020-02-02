package jfw.util.redux;

import jfw.util.redux.middleware.Middleware;
import lombok.Getter;

import java.util.*;

import static jfw.util.Validator.validateNotNull;

public class Store<Action, State> {

	private final Set<Subscriber<State>> consumers = new HashSet<>();
	private final Reducer<Action, State> reducer;
	private final Dispatcher<Action> dispatcher;

	@Getter
	private State state;

	public Store(Reducer<Action, State> reducer, State state) {
		this(reducer, state, Collections.emptyList());
	}

	public Store(Reducer<Action, State> reducer, State state, List<Middleware<Action,State>> middlewares) {
		this.reducer = validateNotNull(reducer, "reducer");
		this.state = validateNotNull(state, "state");

		Dispatcher<Action> wrappedDispatcher = action -> {
			validateNotNull(action, "action");

			State newState = this.reducer.reduce(action, this.state);

			if (newState != this.state) {
				this.state = newState;

				notifyConsumers();
			}
		};

		for (Middleware<Action,State> middleware : middlewares) {
			wrappedDispatcher = middleware.apply(wrappedDispatcher, this::getState);
		}

		dispatcher = wrappedDispatcher;
	}

	public void dispatch(Action action) {
		dispatcher.dispatch(action);
	}

	public Subscription subscribe(Subscriber<State> consumer) {
		validateNotNull(consumer, "consumer");
		consumers.add(consumer);

		consumer.onStateChanged(state);

		return () -> consumers.remove(consumer);
	}

	private void notifyConsumers() {
		for (Subscriber<State> consumer : consumers) {
			consumer.onStateChanged(state);
		}
	}
}
