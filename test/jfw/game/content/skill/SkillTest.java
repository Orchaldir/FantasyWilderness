package jfw.game.content.skill;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SkillTest {

	private static final String NAME = "abcd";

	@Test
	void testGetName() {
		assertThat(new Skill(NAME).getName()).isEqualTo(NAME);
	}

}