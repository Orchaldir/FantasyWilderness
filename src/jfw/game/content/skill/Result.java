package jfw.game.content.skill;

public enum Result {
	CRITICAL_SUCCESS,
	SUCCESS,
	DRAW,
	FAILURE,
	CRITICAL_FAILURE;

	public boolean isSuccess() {
		return this == CRITICAL_SUCCESS || this == SUCCESS;
	}

	public boolean isSuccessOrDraw() {
		return isSuccess() || this == DRAW;
	}

	public boolean isFailure() {
		return this == CRITICAL_FAILURE || this == FAILURE;
	}
}
