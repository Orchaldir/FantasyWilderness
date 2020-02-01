package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.util.function.Supplier;

@Slf4j
public class LogDiffMiddleware<Action, State> implements Middleware<Action, State> {

	private Javers javers = JaversBuilder.javers().build();

	@Override
	public Dispatcher<Action> apply(Dispatcher<Action> dispatcher, Supplier<State> stateSupplier) {
		return action -> {
			State oldState = stateSupplier.get();

			dispatcher.dispatch(action);

			State newState = stateSupplier.get();
			Diff diff = javers.compare(oldState, newState);

			log.info("{}", diff.prettyPrint());
		};
	}

}
