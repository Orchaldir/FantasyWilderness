package jfw.game.content.skill;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SkillManager {

	private final Map<String,Skill> skillMap = new HashMap<>();

	public SkillManager(List<Skill> skills) {
		skills.forEach(s -> skillMap.put(s.getName(), s));
	}

	public Skill get(String name) {
		Skill trait = skillMap.get(name);

		if(trait == null) {
			throw new NoSuchElementException(String.format("Did not find skill '%s'!", name));
		}

		return trait;
	}

	public List<Skill> getAll() {
		return skillMap.
				values().
				stream().
				sorted().
				collect(Collectors.toList());
	}
}
