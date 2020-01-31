package jfw.util.redux;

public interface Dispatcher<Action> {

	void dispatch(Action action);

}
