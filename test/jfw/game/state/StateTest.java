package jfw.game.state;

import jfw.game.content.skill.Skill;
import jfw.game.state.component.Statistics;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateTest {

	private static final int ENTITY_ID = 66;

	private State state;

	@Nested
	class TestNameSelector {

		private static final String NAME = "Test Name";

		@Mock
		private TimeEntry entry;

		@Mock
		private TimeSystem timeSystem;

		@Mock
		private ComponentStorage<String> names;

		@BeforeEach
		void setup() {
			state = new State(null, names,  null, null, timeSystem);
		}

		@Test
		void testGetName() {
			when(names.getOptional(ENTITY_ID)).thenReturn(Optional.of(NAME));

			assertThat(state.getName(ENTITY_ID)).isEqualTo(NAME);

			verify(names).getOptional(ENTITY_ID);
			verifyNoMoreInteractions(names);
		}

		@Test
		void testGetDefaultName() {
			when(names.getOptional(ENTITY_ID)).thenReturn(Optional.empty());

			assertThat(state.getName(ENTITY_ID)).isEqualTo("Entity 66");

			verify(names).getOptional(ENTITY_ID);
			verifyNoMoreInteractions(names);
		}

		@Test
		void testGetCurrentName() {
			when(timeSystem.getCurrentEntry()).thenReturn(entry);
			when(entry.getEntityId()).thenReturn(ENTITY_ID);
			when(names.getOptional(ENTITY_ID)).thenReturn(Optional.of(NAME));

			assertThat(state.getCurrentName()).isEqualTo(NAME);

			verify(names).getOptional(ENTITY_ID);
			verifyNoMoreInteractions(names);
		}
	}

	@Nested
	class TestStatisticsSelector {

		@Mock
		private Map<Skill,Integer> map;
		@Mock
		private Statistics statistics;
		@Mock
		private ComponentStorage<Statistics> storage;

		@BeforeEach
		void setup() {
			state = new State(null, null,  null, storage, null);
		}

		@Test
		void testGetSkillMap() {
			when(storage.getOptional(ENTITY_ID)).thenReturn(Optional.of(statistics));
			when(statistics.getMap()).thenReturn(map);

			assertThat(state.getSkillMap(ENTITY_ID)).isEqualTo(map);
		}

		@Test
		void testGetEmptySkillMap() {
			when(storage.getOptional(ENTITY_ID)).thenReturn(Optional.empty());

			assertThat(state.getSkillMap(ENTITY_ID)).isEmpty();
		}

	}

	@Nested
	class TestTimeSelector {

		@Mock
		private TimeEntry entry;
		@Mock
		private TimeSystem timeSystem;

		@BeforeEach
		void setup() {
			state = new State(null, null,  null, null, timeSystem);
		}

		@Test
		void testGetCurrentEntityId() {
			when(timeSystem.getCurrentEntry()).thenReturn(entry);
			when(entry.getEntityId()).thenReturn(ENTITY_ID);

			assertThat(state.getCurrentEntityId()).isEqualTo(ENTITY_ID);
		}

		@Test
		void testGetCurrentTime() {
			when(timeSystem.getCurrentTime()).thenReturn(99L);

			assertThat(state.getCurrentTime()).isEqualTo(99);
		}
	}

}