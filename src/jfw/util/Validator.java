package jfw.util;

import java.util.List;

public interface Validator {

	static <T> T validateNotNull(T object, String name) {
		if (object == null) {
			String message = String.format("'%s' is null!", name);
			throw new NullPointerException(message);
		}

		return object;
	}

	static <T> T[] validateSize(T[] array, int desiredLength, String name) {
		validateNotNull(array, name);

		if (array.length != desiredLength) {
			String message = String.format("'%s' has length %d instead of %d!", name, array.length, desiredLength);
			throw new IllegalArgumentException(message);
		}

		return array;
	}

	static <T> T[] validateNotEmpty(T[] array, int desiredLength, String name) {
		validateSize(array, desiredLength, name);

		for (int i = 0; i < desiredLength; i++) {
			validateNotNull(array[i], name+"[i]");
		}

		return array;
	}

	static <T> List<T> validateNotEmpty(List<T> list, String name) {
		validateNotNull(list, name);

		if (list.isEmpty()) {
			String message = String.format("'%s' is empty!", name);
			throw new IllegalArgumentException(message);
		}

		return list;
	}

	static String validateUnicode(String text, int desiredCodePoints, String name) {
		validateNotNull(text, name);

		long codePoints = text.codePoints().count();

		if (codePoints != desiredCodePoints) {
			String message = String.format("'%s' has %d code points instead of %d!", name, codePoints, desiredCodePoints);
			throw new IllegalArgumentException(message);
		}

		return text;
	}

	static int validateGreater(int value, int threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("'%s' requires %d > %d!", name, value, threshold);
		throw new IllegalArgumentException(message);
	}

	static long validateGreater(long value, long threshold, String name) {
		if (value > threshold) {
			return value;
		}

		String message = String.format("'%s' requires %d > %d!", name, value, threshold);
		throw new IllegalArgumentException(message);
	}

	static boolean validateTrue(boolean value, String name) {
		if (!value) {
			String message = String.format("'%s' must be true!", name);
			throw new IllegalArgumentException(message);
		}

		return true;
	}

	static boolean validateFalse(boolean value, String name) {
		if (value) {
			String message = String.format("'%s' must be false!", name);
			throw new IllegalArgumentException(message);
		}

		return false;
	}
}
