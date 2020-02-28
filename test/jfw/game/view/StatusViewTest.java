package jfw.game.view;

import jfw.game.content.skill.Skill;
import jfw.game.state.State;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static jfw.game.view.StatusView.BACKGROUND_TILE;
import static jfw.game.view.StatusView.FONT_COLOR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusViewTest {

	private static final String NAME = "Character0";

	private static final String SKILL_NAME0 = "Abc";
	private static final String SKILL_NAME1 = "Skill7";

	private static final int SKILL_RANK0 = 5;
	private static final int SKILL_RANK1 = 12;

	private static final Skill SKILL0 = new Skill(SKILL_NAME0);
	private static final Skill SKILL1 = new Skill(SKILL_NAME1);

	@Mock
	private State state;
	@Mock
	private TileRenderer tileRenderer;
	@InjectMocks
	private StatusView view;

	@Nested
	class TestRender {

		private static final int ENTITY_ID = 42;

		@Mock
		private Supplier<TileMap> supplier;
		@Mock
		private TileMap backgroundTileMap;
		@Mock
		private TileMap statusTileMap;

		@BeforeEach
		void setUp() {
			when(supplier.get()).thenReturn(backgroundTileMap, statusTileMap);
		}

		@Test
		void testRender() {
			when(state.getCurrentEntityId()).thenReturn(Optional.of(ENTITY_ID));
			when(state.getName(ENTITY_ID)).thenReturn(NAME);
			when(state.getSkillMap(ENTITY_ID)).thenReturn(Map.of(SKILL0, SKILL_RANK0, SKILL1, SKILL_RANK1));

			view.render(state, supplier);

			verifyBackground();

			verify(statusTileMap).setText("Name=Character0", 0, 0, FONT_COLOR);
			verify(statusTileMap).setText("Skills:", 0, 2, FONT_COLOR);
			verify(statusTileMap).setText("Abc:5", 2, 3, FONT_COLOR);
			verify(statusTileMap).setText("Skill7:12", 2, 4, FONT_COLOR);
			verify(statusTileMap).render(tileRenderer);
			verifyNoMoreInteractions(statusTileMap);
		}

		@Test
		void testRenderWithoutSkills() {
			when(state.getCurrentEntityId()).thenReturn(Optional.of(ENTITY_ID));
			when(state.getName(ENTITY_ID)).thenReturn(NAME);

			view.render(state, supplier);

			verifyBackground();

			verify(statusTileMap).setText("Name=Character0", 0, 0, FONT_COLOR);
			verify(statusTileMap).render(tileRenderer);
			verifyNoMoreInteractions(statusTileMap);
		}

		@Test
		void testRenderNoCurrentEntity() {
			when(state.getCurrentEntityId()).thenReturn(Optional.empty());

			view.render(state, supplier);

			verifyBackground();

			verifyNoInteractions(statusTileMap);
		}

		private void verifyBackground() {
			verify(backgroundTileMap).setTile(BACKGROUND_TILE);
			verify(backgroundTileMap).render(tileRenderer);
			verifyNoMoreInteractions(backgroundTileMap);
		}
	}
}