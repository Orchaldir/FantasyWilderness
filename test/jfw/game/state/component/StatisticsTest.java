package jfw.game.state.component;

import jfw.game.content.skill.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static jfw.game.state.component.Statistics.DEFAULT_RANK;
import static org.assertj.core.api.Assertions.assertThat;

class StatisticsTest {

	private static final String NAME0 = "skill0";
	private static final String NAME1 = "skill1";
	private static final String NAME2 = "skill2";

	private static final int RANK0 = 3;
	private static final int RANK1 = 5;

	private static final Skill SKILL0 = new Skill(NAME0);
	private static final Skill SKILL1 = new Skill(NAME1);
	private static final Skill SKILL2 = new Skill(NAME2);

	private Statistics statistics;

	@BeforeEach
	void setup() {
		statistics = new Statistics(Map.of(SKILL0, RANK0, SKILL1, RANK1));
	}

	@Test
	void testWithModifiedSkill() {
		Statistics newStatistics = statistics.with(SKILL1, 10);

		assertThat(newStatistics.getRank(SKILL0)).isEqualTo(RANK0);
		assertThat(newStatistics.getRank(SKILL1)).isEqualTo(10);

		assertInit(statistics);
	}

	@Test
	void testWithNewSkill() {
		Statistics newStatistics = statistics.with(SKILL2, 10);

		assertInit(newStatistics);
		assertThat(newStatistics.getRank(SKILL2)).isEqualTo(10);

		assertInit(statistics);
	}

	@Test
	void testGetRank() {
		assertInit(statistics);
	}

	@Test
	void testGetDefaultRank() {
		assertThat(statistics.getRank(SKILL2)).isEqualTo(DEFAULT_RANK);
	}

	void assertInit(Statistics statistics) {
		assertThat(statistics.getRank(SKILL0)).isEqualTo(RANK0);
		assertThat(statistics.getRank(SKILL1)).isEqualTo(RANK1);
	}

}