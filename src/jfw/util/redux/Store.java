package jfw.util.redux;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static jfw.util.Validator.validateNotNull;

public class Store<Action, State> {

	private final Set<Subscriber<State>> consumers = new HashSet<>();
	private final Reducer<Action, State> reducer;

	@Getter
	private State state;

	public Store(Reducer<Action, State> reducer, State state) {
		this.reducer = validateNotNull(reducer, "reducer");
		this.state = validateNotNull(state, "state");
	}

	public void dispatch(Action action) {
		validateNotNull(action, "action");

		State newState = reducer.reduce(action, state);

		if (newState != state) {
			state = newState;

			notifyConsumers();
		}
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
