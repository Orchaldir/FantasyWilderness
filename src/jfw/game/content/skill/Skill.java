package jfw.game.content.skill;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Skill implements Comparable<Skill>  {

	private final String name;

	@Override
	public int compareTo(Skill other) {
		return other.name.compareTo(name);
	}

}
