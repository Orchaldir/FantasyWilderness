package jfw.game.content.skill;

import jfw.util.redux.random.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static jfw.game.content.skill.Result.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SkillCheckerTest {

	private static final int DIE_SIZE = 6;
	private static final int CRITICAL_THRESHOLD = 5;

	private RandomNumberGenerator generator;
	private SkillChecker checker;

	@BeforeEach
	void setup() {
		generator = mock(RandomNumberGenerator.class);
		checker = new SkillChecker(DIE_SIZE, CRITICAL_THRESHOLD);
	}

	@Test
	void testRollCriticalSuccess() {
		assertCheck(3, 4, 2, CRITICAL_SUCCESS);
	}

	@Test
	void testRollSuccess() {
		assertCheck(4, 3, 5, SUCCESS);
	}

	@Test
	void testRollDraw() {
		assertCheck(1, 2, 3, DRAW);
	}

	@Test
	void testRollFailure() {
		assertCheck(-2, 2, 3, FAILURE);
	}

	@Test
	void testRollCriticalFailure() {
		assertCheck(2, 3, 10, CRITICAL_FAILURE);
	}

	private void assertCheck(int diceResult, int rank, int difficulty, Result result) {
		when(generator.rollPositiveAndNegativeDice(DIE_SIZE)).thenReturn(diceResult);

		assertThat(checker.check(generator, rank, difficulty)).isEqualTo(result);

		verify(generator).rollPositiveAndNegativeDice(DIE_SIZE);
		verifyNoMoreInteractions(generator);
	}

}