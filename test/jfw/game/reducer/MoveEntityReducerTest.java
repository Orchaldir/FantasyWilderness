package jfw.game.reducer;

import jfw.game.action.MoveEntity;
import jfw.game.state.State;
import jfw.game.state.component.Statistics;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Direction;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jfw.game.action.MoveEntity.MOVE_DURATION;
import static jfw.game.reducer.MoveEntityReducer.REDUCER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveEntityReducerTest {

	private static final int ENTITY_ID = 0;
	private static final int START_TIME = 15;

	private static final ArrayMap2d<WorldCell> WORLD_MAP =
			new ArrayMap2d<>(4, 4, new WorldCell[16], new WorldCell(TerrainType.PLAIN));

	private static final ComponentStorage<String> NAMES = new ComponentMap<>(Collections.emptyMap());

	private static final ComponentStorage<Integer> POSITIONS = new ComponentMap<>(Map.of(ENTITY_ID, 4));

	private static final ComponentStorage<Statistics> STATISTICS = new ComponentMap<>(Collections.emptyMap());

	private static final TimeSystem TIME_SYSTEM = new TimeSystem(List.of(new TimeEntry(ENTITY_ID, START_TIME)));

	private static final State INIT_STATE = new State(WORLD_MAP, NAMES, POSITIONS, STATISTICS, TIME_SYSTEM);

	@Test
	void testMoveSuccess() {
		State state = REDUCER.reduce(new MoveEntity(ENTITY_ID, Direction.EAST), INIT_STATE);

		assertThat(state.getWorldMap()).isEqualTo(WORLD_MAP);

		assertThat(state.getNames()).isEqualTo(NAMES);

		assertThat(state.getPositions().getIds()).contains(ENTITY_ID);
		assertThat(state.getPositions().getOptional(ENTITY_ID)).isEqualTo(Optional.of(5));

		assertThat(state.getStatisticsStorage()).isEqualTo(STATISTICS);

		assertThat(state.getTimeSystem().getAllEntries()).contains(new TimeEntry(ENTITY_ID, START_TIME + MOVE_DURATION));
	}

	@Test
	void testMoveInvalidEntity() {
		assertThrows(NullPointerException.class, () -> REDUCER.reduce(new MoveEntity(99, Direction.EAST), INIT_STATE),
				"No component for entity 2!");
	}

	@Test
	void testMoveAgainstBorder() {
		assertThat(REDUCER.reduce(new MoveEntity(ENTITY_ID, Direction.WEST), INIT_STATE)).isEqualTo(INIT_STATE);
	}

}