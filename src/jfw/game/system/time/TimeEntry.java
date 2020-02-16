package jfw.game.system.time;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class TimeEntry implements Comparable<TimeEntry> {

	private final int entityId;
	private final long time;

	public TimeEntry(int entityId) {
		this(entityId, 0);
	}

	TimeEntry apply(long duration) {
		return new TimeEntry(entityId, time + duration);
	}

	@Override
	public int compareTo(TimeEntry o) {
		return Long.compare(time, o.time);
	}
}
