package jfw.game.action;

import jfw.util.map.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MoveEntity {

	public static final int MOVE_DURATION = 30;

	private final int entityId;
	private final Direction direction;

}
