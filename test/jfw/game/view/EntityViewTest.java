package jfw.game.view;

import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.Map2d;
import jfw.util.tile.Tile;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static jfw.game.view.EntityView.view;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityViewTest {

	private static final int ID0 = 0;
	private static final int POSITION0 = 99;
	private static final int X0 = 6;
	private static final int Y0 = 3;

	private ComponentStorage<Integer> positions;

	@Mock
	private Map2d<Tile> map;
	@Mock
	private TileMap tileMap;
	@Mock
	private TileConverter<Integer> tileConverter;
	@Mock
	private Tile tile;

	@Test
	void test() {
		positions = new ComponentMap<>(Map.of(ID0,POSITION0));

		when(tileMap.getMap()).thenReturn(map);
		when(map.getX(POSITION0)).thenReturn(X0);
		when(map.getY(POSITION0)).thenReturn(Y0);
		when(tileConverter.convert(ID0)).thenReturn(tile);

		view(positions, tileMap, tileConverter);

		verify(tileMap).setTile(tile, X0, Y0);
		verifyNoMoreInteractions(tileMap);
	}

}