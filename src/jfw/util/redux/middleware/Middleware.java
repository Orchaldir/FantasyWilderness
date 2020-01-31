package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;

import java.util.function.Supplier;

public interface Middleware<Action, State> {

	Dispatcher<Action> apply(Dispatcher<Action> dispatcher, Supplier<State> stateSupplier);

}
