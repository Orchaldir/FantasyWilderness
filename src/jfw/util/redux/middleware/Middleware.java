package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;

import java.util.function.Supplier;

public interface Middleware<A, S> {

	Dispatcher<A> apply(Dispatcher<A> dispatcher, Supplier<S> stateSupplier);

}
