package jfw.util.redux;

import lombok.Getter;

import static jfw.util.Validator.validateNotNull;

public class Store<Action, State> {

	private final Reducer<Action, State> reducer;

	@Getter
	private State state;

	public Store(Reducer<Action, State> reducer, State state) {
		this.reducer = validateNotNull(reducer, "reducer");
		this.state = validateNotNull(state, "state");
	}

	public State dispatch(Action action) {
		state = reducer.reduce(action, state);

		return state;
	}
}
