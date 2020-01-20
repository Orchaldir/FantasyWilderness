package jfw.util;

public class Validator {

	public static <T> T validateNotNull(T object, String name) {
		if (object == null) {
			String message = String.format("%s is null!", name);
			throw new NullPointerException(message);
		}

		return object;
	}

	public static int validateGreater(int value, int threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("%s requires %d > %d", name, value, threshold);
		throw new IllegalArgumentException(message);
	}
}
