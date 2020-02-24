package jfw.game.selector;

import jfw.game.state.State;

import static jfw.game.selector.TimeSystemSelector.getCurrentEntityId;

public interface NameSelector {

	static String getName(State state, int entityId) {
		return state.getNames().getOptional(entityId).orElse("Entity " + entityId);
	}

	static String getCurrentName(State state) {
		return getName(state, getCurrentEntityId(state));
	}

}
