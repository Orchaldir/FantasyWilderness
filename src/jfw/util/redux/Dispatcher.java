package jfw.util.redux;

public interface Dispatcher<A> {

	void dispatch(A action);

}
