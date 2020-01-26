package jfw.util.map;

import jfw.util.OutsideMapException;
import lombok.Getter;

import static jfw.util.Validator.*;

public class ArrayMap2d<T> implements Map2d<T> {

	@Getter
	protected final int width;
	@Getter
	protected final int height;

	protected final T[] cells;

	public ArrayMap2d(int width, int height, T[] cells) {
		this.width = validateGreater(width, 0, "width");
		this.height = validateGreater(height, 0, "height");
		final int size = width * height;
		this.cells = validateNotEmpty(cells, size, "cells");
	}

	public ArrayMap2d(int width, int height, T[] cells, T defaultCell) {
		validateNotNull(defaultCell, "defaultCell");
		this.width = validateGreater(width, 0, "width");
		this.height = validateGreater(height, 0, "height");
		final int size = width * height;
		this.cells = validateSize(cells, size, "cells");

		for (int i = 0; i < size; i++) {
			this.cells[i] = defaultCell;
		}
	}

	@Override
	public int getSize() {
		return cells.length;
	}

	@Override
	public int getIndex(int x, int y) {
		return y * width + x;
	}

	@Override
	public int getX(int index) {
		return index % width;
	}

	@Override
	public int getY(int index) {
		return index / width;
	}

	@Override
	public boolean isInsideForX(int x) {
		return x >= 0 && x < width;
	}

	@Override
	public boolean isInsideForY(int y) {
		return y >= 0 && y < height;
	}

	@Override
	public boolean isInside(int x, int y) {
		return isInsideForX(x) && isInsideForY(y);
	}

	@Override
	public boolean isInside(int index) {
		return index >=0 && index < getSize();
	}

	@Override
	public T getNode(int index) {
		if(isInside(index)) {
			return cells[index];
		}

		throw new OutsideMapException(index);
	}

	@Override
	public T getNode(int x, int y) {
		if(isInside(x, y)) {
			return cells[getIndex(x, y)];
		}

		throw new OutsideMapException(x, y);
	}
}
