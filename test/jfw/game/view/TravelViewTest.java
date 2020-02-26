package jfw.game.view;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import jfw.game.action.MoveEntity;
import jfw.game.state.State;
import jfw.game.state.world.WorldCell;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Direction;
import jfw.util.redux.Store;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static jfw.game.state.world.WorldCell.TILE_CONVERTER;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelViewTest {

	private static final Color COLOR = Color.BLUE;

	@Mock
	private State state;
	@Mock
	private Store<Object, State> store;
	@Mock
	private TileRenderer tileRenderer;

	private TravelView view;

	@BeforeEach
	void setUp() {
		view = new TravelView(store, tileRenderer, COLOR);
	}

	@Nested
	class TestRender {

		@Mock
		private ComponentStorage<Integer> positions;
		@Mock
		private ArrayMap2d<WorldCell> worldMap;
		@Mock
		private Supplier<TileMap> supplier;
		@Mock
		private TileMap backgroundTileMap;
		@Mock
		private TileMap uiTileMap;

		@BeforeEach
		void setUp() {
			when(supplier.get()).thenReturn(backgroundTileMap, uiTileMap);
		}

		@Test
		void testRender() {
			when(state.getPositions()).thenReturn(positions);
			when(state.getCurrentName()).thenReturn("Name");
			when(state.getCurrentTime()).thenReturn(0L);
			when(state.getWorldMap()).thenReturn(worldMap);

			view.render(state, supplier);

			verify(backgroundTileMap).setMap(worldMap, 0, 0, TILE_CONVERTER);
			verify(backgroundTileMap).render(tileRenderer);
			verifyNoMoreInteractions(backgroundTileMap);

			verify(positions).visit(any());
			verify(uiTileMap).setText("Day 0 0:00", 0, 0, COLOR);
			verify(uiTileMap).setTextFromBottom("Next: Name", 0, 0, COLOR);
			verify(uiTileMap).getMap();
			verify(uiTileMap).render(tileRenderer);
			verifyNoMoreInteractions(uiTileMap);
		}
	}

	@Nested
	class TestOnKeyReleased {

		private static final int ENTITY_ID = 42;

		@Test
		void testNoTravel() {
			when(store.getState()).thenReturn(state);
			when(state.getCurrentEntityId()).thenReturn(ENTITY_ID);

			view.onKeyReleased(KeyCode.ENTER);

			verify(store).getState();
			verifyNoMoreInteractions(store);
		}

		@Test
		void testTravelNorth() {
			testTravel(KeyCode.UP, Direction.NORTH);
		}

		@Test
		void testTravelEast() {
			testTravel(KeyCode.RIGHT, Direction.EAST);
		}

		@Test
		void testTravelSouth() {
			testTravel(KeyCode.DOWN, Direction.SOUTH);
		}

		@Test
		void testTravelWest() {
			testTravel(KeyCode.LEFT, Direction.WEST);
		}

		private void testTravel(KeyCode keyCode, Direction direction) {
			when(store.getState()).thenReturn(state);
			when(state.getCurrentEntityId()).thenReturn(ENTITY_ID);

			view.onKeyReleased(keyCode);

			verify(store).getState();
			verify(store).dispatch(new MoveEntity(ENTITY_ID, direction));
			verifyNoMoreInteractions(store);
		}
	}

}