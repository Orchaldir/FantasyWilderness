package jfw.game.state;

import jfw.game.content.skill.Skill;
import jfw.game.state.component.Statistics;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class State {
	private final ArrayMap2d<WorldCell> worldMap;
	private final ComponentStorage<String> names;
	private final ComponentStorage<Integer> positions;
	private final ComponentStorage<Statistics> statisticsStorage;
	private final TimeSystem timeSystem;

	// name

	public String getCurrentName() {
		return getName(getCurrentEntityId());
	}

	public String getName(int entityId) {
		return names.getOptional(entityId).orElse("Entity " + entityId);
	}

	// statistics

	public Map<Skill, Integer> getSkillMap(int entityId) {
		return statisticsStorage.getOptional(entityId).
				map(Statistics::getMap).
				orElse(Collections.emptyMap());
	}

	// time

	public int getCurrentEntityId() {
		return timeSystem.getCurrentEntry().getEntityId();
	}

	public long getCurrentTime() {
		return timeSystem.getCurrentTime();
	}
}
