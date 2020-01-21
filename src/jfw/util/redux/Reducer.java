package jfw.util.redux;


public interface Reducer<Action, State> {

	State reduce(Action action, State state);

}
