package jfw.util.map;

import java.util.Optional;

public interface Map2d<T> {

	int getWidth();
	int getHeight();
	int getSize();

	int getIndex(int x, int y);
	int getX(int index);
	int getY(int index);

	boolean isInsideForX(int x);
	boolean isInsideForY(int y);
	boolean isInside(int x, int y);
	boolean isInside(int index);

	T getNode(int index);
	T getNode(int x, int y);

	Optional<Integer> getNeighborIndex(int index, Direction direction);

}
