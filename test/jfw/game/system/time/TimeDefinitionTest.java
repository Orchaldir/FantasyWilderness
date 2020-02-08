package jfw.game.system.time;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDefinitionTest {

	private static final int MINUTE = 35;
	private static final int HOUR = 5;
	private static final int DAY = 3;

	private static final long  HOURS = DAY * 24 + HOUR;
	private static final long  TIME = HOURS * 60 + MINUTE;

	private static final TimeDefinition TIME_DEFINITION = new TimeDefinition();

	@Test
	void testMinute() {
		assertThat(TIME_DEFINITION.getMinute(TIME)).isEqualTo(TIME);
		assertThat(TIME_DEFINITION.getMinuteOfHour(TIME)).isEqualTo(MINUTE);
	}

	@Test
	void testHour() {
		assertThat(TIME_DEFINITION.getHour(TIME)).isEqualTo(HOURS);
		assertThat(TIME_DEFINITION.getHourOfDay(TIME)).isEqualTo(HOUR);
	}

	@Test
	void testDay() {
		assertThat(TIME_DEFINITION.getDay(TIME)).isEqualTo(DAY);
	}

	@Test
	void testToString() {
		assertThat(TIME_DEFINITION.toString(TIME)).isEqualTo("Day 3 5:35");
	}

}