package jfw.util.map;

import jfw.util.OutsideMapException;

public class MutableArrayMap2d<T> extends ArrayMap2d<T> implements MutableMap2d<T> {

	public MutableArrayMap2d(int width, int height, T[] cells) {
		super(width, height, cells);
	}

	public MutableArrayMap2d(int width, int height, T[] cells, T defaultCell) {
		super(width, height, cells, defaultCell);
	}

	@Override
	public void setNode(T node, int index) {
		if (isInside(index)) {
			cells[index] = node;
			return;
		}

		throw new OutsideMapException(index);
	}

	@Override
	public void setNode(T node, int x, int y) {
		if (isInside(x, y)) {
			cells[getIndex(x, y)] = node;
			return;
		}

		throw new OutsideMapException(x, y);
	}
}
