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
		array = validateNotNull(array, name);

		if (array.length != desiredLength) {
			String message = String.format("%s has length %d instead of %d!", name, array.length, desiredLength);
			throw new IllegalArgumentException(message);
		}

		return array;
	}

	public static String validateUnicode(String text, int desiredCodePoints, String name) {
		text = validateNotNull(text, name);

		long codePoints = text.codePoints().count();

		if (codePoints != desiredCodePoints) {
			String message = String.format("%s has %d code points instead of %d!", name, codePoints, desiredCodePoints);
			throw new IllegalArgumentException(message);
		}

		return text;
	}

	public static int validateGreater(int value, int threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("%s requires %d > %d", name, value, threshold);
		throw new IllegalArgumentException(message);
	}
}
