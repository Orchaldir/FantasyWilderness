package jfw.util.redux;

public interface Subscriber<State> {

	void onStateChanged(State state);

}
