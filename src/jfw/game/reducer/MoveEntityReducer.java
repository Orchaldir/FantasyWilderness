package jfw.game.reducer;

import jfw.game.action.MoveEntity;
import jfw.game.state.State;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentStorage;
import jfw.util.redux.Reducer;

import java.util.Optional;

import static jfw.game.action.MoveEntity.MOVE_DURATION;

public interface MoveEntityReducer {

	Reducer<MoveEntity, State> REDUCER = (action, oldState) -> {
		Optional<Integer> optionalPosition = oldState.getPositions().get(action.getEntityId());

		if (optionalPosition.isPresent()) {
			int position = optionalPosition.get();
			Optional<Integer> optionalNewPosition = oldState.getWorldMap().getNeighborIndex(position, action.getDirection());

			if (optionalNewPosition.isPresent()) {
				ComponentStorage<Integer> positions = oldState.getPositions().
						updateComponent(action.getEntityId(), optionalNewPosition.get());

				TimeSystem timeSystem = oldState.getTimeSystem().advanceCurrentEntry(MOVE_DURATION);

				return new State(oldState.getWorldMap(), positions, oldState.getStatisticsStorage(), timeSystem);
			}
		}

		return oldState;
	};
}
