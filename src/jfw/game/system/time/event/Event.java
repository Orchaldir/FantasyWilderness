package jfw.game.system.time.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public abstract class Event implements Comparable<Event> {

	protected final long time;

	public abstract Event apply(long duration);

	@Override
	public int compareTo(Event o) {
		return Long.compare(time, o.time);
	}
}
