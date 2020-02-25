package jfw.game.view;

import javafx.scene.input.KeyCode;
import jfw.game.action.MoveEntity;
import jfw.game.state.State;
import jfw.util.map.Direction;
import jfw.util.redux.Store;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelViewTest {

	@Mock
	private State state;
	@Mock
	private Store<Object, State> store;
	@Mock
	private TileRenderer tileRenderer;
	@InjectMocks
	private TravelView view;

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