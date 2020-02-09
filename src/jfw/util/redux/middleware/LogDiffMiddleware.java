package jfw.util.redux.middleware;

import jfw.util.redux.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.util.function.Supplier;

@Slf4j
public class LogDiffMiddleware<A, S> implements Middleware<A, S> {

	private Javers javers = JaversBuilder.javers().build();

	@Override
	public Dispatcher<A> apply(Dispatcher<A> dispatcher, Supplier<S> stateSupplier) {
		return action -> {
			S oldState = stateSupplier.get();

			dispatcher.dispatch(action);

			S newState = stateSupplier.get();
			Diff diff = javers.compare(oldState, newState);

			log.info("{}", diff.prettyPrint());
		};
	}

}
