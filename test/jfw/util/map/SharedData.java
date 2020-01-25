package jfw.util.map;

public class SharedData {

	protected static final int WIDTH = 4;
	protected static final int HEIGHT = 5;
	protected static final int SIZE = WIDTH * HEIGHT;

	protected static Integer[] createArray() {
		Integer[] array = new Integer[SIZE];

		for(int i = 0; i < SIZE; i++) {
			array[i] = i;
		}

		return array;
	}
}
