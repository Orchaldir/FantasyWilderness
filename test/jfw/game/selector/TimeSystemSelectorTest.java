package jfw.game.selector;

import jfw.game.state.State;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static jfw.game.selector.TimeSystemSelector.getCurrentEntityId;
import static jfw.game.selector.TimeSystemSelector.getCurrentTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TimeSystemSelectorTest {

	private static final int ENTITY_ID = 44;

	private State state;
	private TimeEntry entry;
	private TimeSystem timeSystem;

	@BeforeEach
	void setup() {
		state = mock(State.class);
		entry = mock(TimeEntry.class);
		timeSystem = mock(TimeSystem.class);

		when(state.getTimeSystem()).thenReturn(timeSystem);
	}

	@Test
	void testGetCurrentEntityId() {
		when(timeSystem.getCurrentEntry()).thenReturn(entry);
		when(entry.getEntityId()).thenReturn(ENTITY_ID);

		assertThat(getCurrentEntityId(state)).isEqualTo(ENTITY_ID);
	}

	@Test
	void testGetCurrentTime() {
		when(timeSystem.getCurrentTime()).thenReturn(99L);
		when(entry.getEntityId()).thenReturn(ENTITY_ID);

		assertThat(getCurrentTime(state)).isEqualTo(99);
	}

}