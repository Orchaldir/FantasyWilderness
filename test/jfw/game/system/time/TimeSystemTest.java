package jfw.game.system.time;

import jfw.game.system.time.event.EntityEntry;
import jfw.game.system.time.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TimeSystemTest {

	private static final Event ENTRY0 = new EntityEntry(0, 100);
	private static final Event ENTRY1 = new EntityEntry(1, 200);
	private static final Event ENTRY2 = new EntityEntry(2, 300);
	private static final Event ENTRY3 = new EntityEntry(3, 400);
	private static final Event ENTRY4 = new EntityEntry(4, 500);
	private static final Event ENTRY5 = new EntityEntry(5, 600);
	private static final Event ENTRY6 = new EntityEntry(6, 700);

	private static final List<Event> ALL = List.of(ENTRY4, ENTRY3, ENTRY2, ENTRY1, ENTRY0);

	private TimeSystem timeSystem;

	@BeforeEach
	void setup() {
		timeSystem = new TimeSystem(ALL);
	}

	@Test
	void testConstructorWithNull() {
		assertThatNullPointerException().
				isThrownBy(() -> new TimeSystem(null));
	}

	@Test
	void testConstructorWithEmptyList() {
		assertThatIllegalArgumentException().
				isThrownBy(() -> new TimeSystem(Collections.emptyList()));
	}

	@Test
	void testGetCurrentEntry() {
		assertThat(timeSystem.getCurrentEntry()).isEqualTo(ENTRY0);
	}

	@Test
	void testGetCurrentEntryTwice() {
		assertThat(timeSystem.getCurrentEntry()).isEqualTo(ENTRY0);
		assertThat(timeSystem.getCurrentEntry()).isEqualTo(ENTRY0);
	}

	@Test
	void testGetAllEntries() {
		assertStartEntries();
	}

	@Test
	void testGetAllEntriesTwice() {
		assertStartEntries();
		assertStartEntries();
	}

	@Test
	void testGetAllEntriesModified() {
		timeSystem.getAllEntries().add(ENTRY6);

		assertStartEntries();
	}

	@Test
	void testGetCurrentTime() {
		assertThat(timeSystem.getCurrentTime()).isEqualTo(ENTRY0.getTime());
	}

	@Test
	void testAddEntries() {
		TimeSystem newTimeSystem = this.timeSystem.addEntries(List.of(ENTRY6, ENTRY5));

		assertThat(newTimeSystem.getAllEntries()).containsExactly(ENTRY0, ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5, ENTRY6);
		assertStartEntries();
	}

	@Test
	void testRemoveEntries() {
		TimeSystem newTimeSystem = this.timeSystem.removeEntries(List.of(ENTRY0, ENTRY4));

		assertThat(newTimeSystem.getAllEntries()).containsExactly(ENTRY1, ENTRY2, ENTRY3);
		assertStartEntries();
	}

	@Test
	void testAdvanceCurrentEntry() {
		TimeSystem newTimeSystem = this.timeSystem.advanceCurrentEntry(150);

		assertThat(newTimeSystem.getAllEntries()).containsExactly(ENTRY1, new EntityEntry(0, 250), ENTRY2, ENTRY3, ENTRY4);
		assertStartEntries();
	}

	@Test
	void testCorrectOrder() {
		Event e0 = new EntityEntry(0, 100);
		Event e0b = new EntityEntry(0, 200);
		Event e1 = new EntityEntry(1, 100);
		Event e1b = new EntityEntry(1, 200);
		Event e2 = new EntityEntry(2, 100);
		Event e2b = new EntityEntry(2, 200);

		List<Event> entries = List.of(e0, e1, e2);
		TimeSystem newTimeSystem = new TimeSystem(entries).advanceCurrentEntry(100);

		assertThat(newTimeSystem.getAllEntries()).containsExactly(e1, e2, e0b);

		newTimeSystem = newTimeSystem.advanceCurrentEntry(100);

		assertThat(newTimeSystem.getAllEntries()).containsExactly(e2, e0b, e1b);

		newTimeSystem = newTimeSystem.advanceCurrentEntry(100);

		assertThat(newTimeSystem.getAllEntries()).containsExactly(e0b, e1b, e2b);
	}

	private void assertStartEntries() {
		assertThat(timeSystem.getAllEntries()).containsExactly(ENTRY0, ENTRY1, ENTRY2, ENTRY3, ENTRY4);
	}

}