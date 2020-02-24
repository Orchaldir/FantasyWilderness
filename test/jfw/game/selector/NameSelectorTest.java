package jfw.game.selector;

import jfw.game.state.State;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import jfw.util.ecs.ComponentStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static jfw.game.selector.NameSelector.getCurrentName;
import static jfw.game.selector.NameSelector.getName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NameSelectorTest {

	private static final int ENTITY_ID = 66;
	private static final String NAME = "Test Name";

	@Mock
	private State state;

	@Mock
	private TimeEntry entry;

	@Mock
	private TimeSystem timeSystem;

	@Mock
	private ComponentStorage<String> names;

	@Test
	void testGetName() {
		when(state.getNames()).thenReturn(names);
		when(names.getOptional(ENTITY_ID)).thenReturn(Optional.of(NAME));

		assertThat(getName(state, ENTITY_ID)).isEqualTo(NAME);

		verify(state).getNames();
		verifyNoMoreInteractions(state);
		verify(names).getOptional(ENTITY_ID);
		verifyNoMoreInteractions(names);
	}

	@Test
	void testGetDefaultName() {
		when(state.getNames()).thenReturn(names);
		when(names.getOptional(ENTITY_ID)).thenReturn(Optional.empty());

		assertThat(getName(state, ENTITY_ID)).isEqualTo("Entity 66");

		verify(state).getNames();
		verifyNoMoreInteractions(state);
		verify(names).getOptional(ENTITY_ID);
		verifyNoMoreInteractions(names);
	}

	@Test
	void testGetCurrentName() {
		when(state.getTimeSystem()).thenReturn(timeSystem);
		when(timeSystem.getCurrentEntry()).thenReturn(entry);
		when(entry.getEntityId()).thenReturn(ENTITY_ID);
		when(state.getNames()).thenReturn(names);
		when(names.getOptional(ENTITY_ID)).thenReturn(Optional.of(NAME));

		assertThat(getCurrentName(state)).isEqualTo(NAME);

		verify(state).getNames();
		verifyNoMoreInteractions(state);
		verify(names).getOptional(ENTITY_ID);
		verifyNoMoreInteractions(names);
	}

}