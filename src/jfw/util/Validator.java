package jfw.util;

public class Validator {

	public static int requireGreater(int value, int threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("%s requires %d > %d", name, value, threshold);
		throw new IllegalArgumentException(message);
	}
}
