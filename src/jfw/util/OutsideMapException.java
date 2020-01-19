package jfw.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Thrown if a node outside the map is accessed.
@AllArgsConstructor
@Getter
public class OutsideMapException extends RuntimeException {

	private final int x;
	private final int y;
	private final int index;
	private final boolean usedIndex;

	public OutsideMapException(int x, int y) {
		this(x, y, -1, false);
	}

	public OutsideMapException(int index) {
		this(-1, -1, index, true);
	}

	public String getMessage() {
		return usedIndex ?
				String.format("Index %d is outside map!", index) :
				String.format("Column %d & row %d is outside map!", x, y);
	}
}
