package jfw.util.redux;

public interface Subscriber<S> {

	void onStateChanged(S state);

}
