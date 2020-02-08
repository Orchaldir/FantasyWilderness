package jfw.game.system.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeSystemTest {

	private static final TimeEntry ENTRY0 = new TimeEntry(0, 100);
	private static final TimeEntry ENTRY1 = new TimeEntry(1, 200);
	private static final TimeEntry ENTRY2 = new TimeEntry(2, 300);
	private static final TimeEntry ENTRY3 = new TimeEntry(3, 400);
	private static final TimeEntry ENTRY4 = new TimeEntry(4, 500);
	private static final TimeEntry ENTRY5 = new TimeEntry(5, 600);
	private static final TimeEntry ENTRY6 = new TimeEntry(6, 700);

	private static final List<TimeEntry> ALL = List.of(ENTRY4, ENTRY3, ENTRY2, ENTRY1, ENTRY0);

	private TimeSystem timeSystem;

	@BeforeEach
	void setup() {
		timeSystem = new TimeSystem(ALL);
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

		assertThat(newTimeSystem.getAllEntries()).containsExactly(ENTRY1, new TimeEntry(0, 250), ENTRY2, ENTRY3, ENTRY4);
		assertStartEntries();
	}

	private void assertStartEntries() {
		assertThat(timeSystem.getAllEntries()).containsExactly(ENTRY0, ENTRY1, ENTRY2, ENTRY3, ENTRY4);
	}

}