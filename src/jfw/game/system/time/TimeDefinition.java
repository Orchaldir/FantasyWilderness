package jfw.game.system.time;

public class TimeDefinition {

	private static final int MINUTES_PER_HOUR = 60;
	private static final int HOURS_PER_DAY = 24;

	public long getMinute(long time) {
		return time;
	}

	public int getMinuteOfHour(long time) {
		return (int) (time % MINUTES_PER_HOUR);
	}

	public long getHour(long time) {
		return time / MINUTES_PER_HOUR;
	}

	public int getHourOfDay(long time) {
		return (int) (getHour(time) % HOURS_PER_DAY);
	}

	public int getDay(long time) {
		return (int) (getHour(time) / HOURS_PER_DAY);
	}

	public String toString(long time) {
		return String.format("Day %d %d:%02d", getDay(time), getHourOfDay(time), getMinuteOfHour(time));
	}
}
