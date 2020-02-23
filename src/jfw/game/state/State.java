package jfw.game.state;

import jfw.game.state.component.Statistics;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class State {
	private final ArrayMap2d<WorldCell> worldMap;
	private final ComponentStorage<Integer> positions;
	private final ComponentStorage<Statistics> statisticsStorage;
	private final TimeSystem timeSystem;
}
