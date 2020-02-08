package jfw.game.system.time;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeEntryTest {

	private static final int ID0 = 42;
	private static final int TIME0 = 111;

	private static final TimeEntry ENTRY0 = new TimeEntry(ID0, TIME0);
	private static final TimeEntry ENTRY1 = new TimeEntry(5, 456);

	@Test
	void testGetters() {
		assertThat(ENTRY0.getEntityId()).isEqualTo(ID0);
		assertThat(ENTRY0.getTime()).isEqualTo(TIME0);
	}

	@Test
	void testComparator() {
		assertThat(ENTRY0.compareTo(ENTRY1)).isEqualTo(-1);
		assertThat(ENTRY1.compareTo(ENTRY0)).isEqualTo(1);
		assertThat(ENTRY0.compareTo(new TimeEntry(ID0, TIME0))).isEqualTo(0);
	}

}