package jfw.util.redux;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static jfw.util.Validator.validateNotNull;

public class Store<Action, State> {

	private final Set<Consumer<State>> consumers = new HashSet<>();
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

	public Subscription subscribe(Consumer<State> consumer) {
		validateNotNull(consumer, "consumer");
		consumers.add(consumer);

		consumer.accept(state);

		return () -> consumers.remove(consumer);
	}

	private void notifyConsumers() {
		for (Consumer<State> consumer : consumers) {
			consumer.accept(state);
		}
	}
}
