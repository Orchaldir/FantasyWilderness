package jfw.game.content.skill;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Skill implements Comparable<Skill>  {

	private final String name;

	@Override
	public int compareTo(Skill other) {
		return name.compareTo(other.name);
	}

}
