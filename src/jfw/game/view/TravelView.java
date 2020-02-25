package jfw.game.view;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import jfw.game.action.MoveEntity;
import jfw.game.state.State;
import jfw.game.system.time.TimeDefinition;
import jfw.util.map.Direction;
import jfw.util.redux.Store;
import jfw.util.tile.Tile;
import jfw.util.tile.UnicodeTile;
import jfw.util.tile.rendering.TileConverter;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;

import java.util.function.Supplier;

import static jfw.game.state.world.WorldCell.TILE_CONVERTER;

public class TravelView {

	private static final Tile CHARACTER_TILE = new UnicodeTile("@", Color.BLACK);
	private static final Tile ACTIVE_CHARACTER_TILE = new UnicodeTile("@", Color.WHITE);

	private final Store<Object, State> store;
	private final TimeDefinition timeDefinition = new TimeDefinition();

	private final TileConverter<Integer> characterTileConverter;
	private final TileRenderer tileRenderer;

	public TravelView(Store<Object, State> store, TileRenderer tileRenderer) {
		this.store = store;
		this.tileRenderer = tileRenderer;

		characterTileConverter = id -> {
			if (store.getState().getCurrentEntityId() == id) {
				return ACTIVE_CHARACTER_TILE;
			}
			return CHARACTER_TILE;
		};
	}

	public void render(State state, Supplier<TileMap> supplier) {
		TileMap worldMap = supplier.get();
		worldMap.setMap(state.getWorldMap(), 0, 0, TILE_CONVERTER);
		worldMap.render(tileRenderer);

		TileMap uiMap = supplier.get();
		EntityView.view(state.getPositions(), uiMap, characterTileConverter);
		uiMap.setText(getCurrentTimeString(state), 0, 0, Color.BLACK);
		uiMap.setText(state.getCurrentName(), 0, 9, Color.BLACK);
		uiMap.render(tileRenderer);
	}

	public void onKeyReleased(KeyCode keyCode) {
		int entityId = store.getState().getCurrentEntityId();

		if (keyCode == KeyCode.UP) {
			store.dispatch(new MoveEntity(entityId, Direction.NORTH));
		}
		else if (keyCode == KeyCode.RIGHT) {
			store.dispatch(new MoveEntity(entityId, Direction.EAST));
		}
		else if (keyCode == KeyCode.DOWN) {
			store.dispatch(new MoveEntity(entityId, Direction.SOUTH));
		}
		else if (keyCode == KeyCode.LEFT) {
			store.dispatch(new MoveEntity(entityId, Direction.WEST));
		}
	}

	private String getCurrentTimeString(State state) {
		return timeDefinition.toString(state.getCurrentTime());
	}
}
