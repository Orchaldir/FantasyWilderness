package jfw.game.content.skill;

import jfw.util.redux.random.RandomNumberGenerator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SkillChecker {

	private final int dieSides;
	private final int criticalThreshold;

	public Result check(RandomNumberGenerator generator, int rank, int difficulty) {
		final int randomModifier = generator.rollPositiveAndNegativeDice(dieSides);
		final int diff = rank - difficulty + randomModifier;

		if(diff >= criticalThreshold) {
			return Result.CRITICAL_SUCCESS;
		}
		else if(diff > 0) {
			return Result.SUCCESS;
		}
		else if(diff <= -criticalThreshold) {
			return Result.CRITICAL_FAILURE;
		}
		else if(diff < 0) {
			return Result.FAILURE;
		}

		return Result.DRAW;
	}
}
