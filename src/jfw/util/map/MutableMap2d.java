package jfw.util.map;

public interface MutableMap2d<T> extends Map2d<T> {

	void setNode(T node, int index);
	void setNode(T node, int x, int y);

}
