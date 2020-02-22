package jfw.util.redux.random;

public class RandomNumberGenerator {

	private final RandomNumberState state;
	private int index;

	public RandomNumberGenerator(RandomNumberState state) {
		this.state = state;
		this.index = state.getIndex();
	}

	public RandomNumberState updateState() {
		return state.with(index);
	}

	public int getInt() {
		return state.getNumber(index++);
	}
}
