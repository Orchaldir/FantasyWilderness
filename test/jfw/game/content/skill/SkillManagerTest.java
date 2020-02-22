package jfw.game.content.skill;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SkillManagerTest {

	private static final String NAME0 = "skill0";
	private static final String NAME1 = "skill1";
	private static final String NAME2 = "skill2";

	private static final Skill SKILL0 = new Skill(NAME0);
	private static final Skill SKILL1 = new Skill(NAME1);
	private static final Skill SKILL2 = new Skill(NAME2);

	private SkillManager manager;

	@BeforeEach
	void setup() {
		manager = new SkillManager(List.of(SKILL2, SKILL0, SKILL1));
	}

	@Test
	void testGetSkill() {
		assertThat(manager.get(NAME0)).isEqualTo(SKILL0);
		assertThat(manager.get(NAME1)).isEqualTo(SKILL1);
		assertThat(manager.get(NAME2)).isEqualTo(SKILL2);
	}

	@Test
	void testGetNotExistingSkill() {
		assertThrows(NoSuchElementException.class, () -> manager.get("Not a skill!"),
				"Did not find skill 'Not a skill!'!");
	}

	@Test
	void testGetAll() {
		assertThat(manager.getAll()).containsExactly(SKILL0, SKILL1, SKILL2);
	}

}