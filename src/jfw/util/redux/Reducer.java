package jfw.util.redux;


public interface Reducer<A, S> {

	S reduce(A action, S state);

}
