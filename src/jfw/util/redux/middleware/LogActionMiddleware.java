package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class LogActionMiddleware<A, S> implements Middleware<A, S> {

	@Override
	public Dispatcher<A> apply(Dispatcher<A> dispatcher, Supplier<S> stateSupplier) {
		return action -> {
			log.info("Dispatch {}", action);

			dispatcher.dispatch(action);
		};
	}

}
