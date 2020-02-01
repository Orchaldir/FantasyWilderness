package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class LogStateMiddleware<Action, State> implements Middleware<Action, State> {

	@Override
	public Dispatcher<Action> apply(Dispatcher<Action> dispatcher, Supplier<State> stateSupplier) {
		return action -> {
			dispatcher.dispatch(action);

			log.info("New state {}", stateSupplier.get());
		};
	}

}
