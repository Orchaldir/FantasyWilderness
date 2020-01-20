package jfw.util;

public class Validator {

	public static <T> T validateNotNull(T object, String name) {
		if (object == null) {
			String message = String.format("%s is null!", name);
			throw new NullPointerException(message);
		}

		return object;
	}

	public static <T> T[] validateSize(T[] array, int desiredLength, String name) {
		if (array == null) {
			String message = String.format("%s is null!", name);
			throw new NullPointerException(message);
		}
		else if (array.length != desiredLength) {
			String message = String.format("%s has length %d instead of %d!", name, array.length, desiredLength);
			throw new IllegalArgumentException(message);
		}

		return array;
	}

	public static int validateGreater(int value, int threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("%s requires %d > %d", name, value, threshold);
		throw new IllegalArgumentException(message);
	}
}
