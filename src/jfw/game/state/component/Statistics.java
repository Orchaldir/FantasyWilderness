package jfw.game.state.component;

import jfw.game.content.skill.Skill;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Statistics {

	public static final int DEFAULT_RANK = 0;

	private final Map<Skill, Integer> skillMap;

	public Statistics with(Skill skill, int rank) {
		Map<Skill, Integer> newSkillMap = new HashMap<>(skillMap);
		newSkillMap.put(skill, rank);

		return new Statistics(newSkillMap);
	}

	public int getRank(Skill skill) {
		return skillMap.getOrDefault(skill, DEFAULT_RANK);
	}

}
