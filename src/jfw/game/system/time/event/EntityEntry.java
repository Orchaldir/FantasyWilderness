package jfw.game.system.time.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class EntityEntry extends Event {

	private final int entityId;

	public EntityEntry(int entityId, long time) {
		super(time);

		this.entityId = entityId;
	}

	public EntityEntry(int entityId) {
		this(entityId, 0);
	}

	@Override
	public Event apply(long duration) {
		return new EntityEntry(entityId, time + duration);
	}
}
