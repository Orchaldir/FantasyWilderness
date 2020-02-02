package jfw.util;

import static jfw.util.Validator.validateUnicode;

public interface Unicode {

	static int stringToCodePoint(String symbol) {
		validateUnicode(symbol, 1, "symbol");
		return symbol.codePointAt(0);
	}

	static String codePointToString(int codePoint) {
		StringBuilder stringOut = new StringBuilder();
		stringOut.appendCodePoint(codePoint);
		return stringOut.toString();
	}

}
