package jfw.game.selector;

import jfw.game.state.State;

public interface TimeSystemSelector {

	static int getCurrentEntityId(State state) {
		return state.getTimeSystem().getCurrentEntry().getEntityId();
	}

	static long getCurrentTime(State state) {
		return state.getTimeSystem().getCurrentTime();
	}

}
