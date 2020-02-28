package jfw.game.system.time.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityEntryTest {

	private static final int ID0 = 42;
	private static final int TIME0 = 111;

	private static final EntityEntry ENTRY0 = new EntityEntry(ID0, TIME0);
	private static final EntityEntry ENTRY1 = new EntityEntry(5, 456);

	@Test
	void testConstructor() {
		EntityEntry entry = new EntityEntry(ID0);

		assertThat(entry.getEntityId()).isEqualTo(ID0);
		assertThat(entry.getTime()).isEqualTo(0);
	}

	@Test
	void testGetters() {
		assertThat(ENTRY0.getEntityId()).isEqualTo(ID0);
		assertThat(ENTRY0.getTime()).isEqualTo(TIME0);
	}

	@Test
	void testComparator() {
		assertThat(ENTRY0.compareTo(ENTRY1)).isEqualTo(-1);
		assertThat(ENTRY1.compareTo(ENTRY0)).isEqualTo(1);
		assertThat(ENTRY0.compareTo(new EntityEntry(ID0, TIME0))).isEqualTo(0);
	}

}