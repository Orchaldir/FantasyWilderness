package jfw.game.view;

import javafx.scene.input.KeyCode;
import jfw.game.state.State;
import jfw.util.tile.rendering.TileMap;

import java.util.function.Supplier;

public interface View {
	void render(State state, Supplier<TileMap> supplier);

	default void onKeyReleased(KeyCode keyCode) {
		// do nothing
	}
}
