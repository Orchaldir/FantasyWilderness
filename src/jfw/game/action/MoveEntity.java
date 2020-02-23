package jfw.game.action;

import jfw.util.map.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MoveEntity {
	private final int entityId;
	private final Direction direction;
}
