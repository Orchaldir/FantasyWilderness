package jfw.util.redux;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static jfw.util.Validator.validateNotNull;

public class Store<Action, State> {

	private final Set<Subscriber<State>> consumers = new HashSet<>();
	private final Reducer<Action, State> reducer;
	private final Dispatcher<Action> dispatcher;

	@Getter
	private State state;

	public Store(Reducer<Action, State> reducer, State state) {
		this.reducer = validateNotNull(reducer, "reducer");
		this.state = validateNotNull(state, "state");

		this.dispatcher = action -> {
			validateNotNull(action, "action");

			State newState = this.reducer.reduce(action, this.state);

			if (newState != this.state) {
				this.state = newState;

				notifyConsumers();
			}
		};
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
