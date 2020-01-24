package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import jfw.util.rendering.TileMap;
import lombok.Getter;

import static jfw.util.Unicode.stringToCodePoint;
import static jfw.util.Validator.validateNotNull;

@Getter
public class UnicodeTile implements Tile {

	private final int codePoint;
	private final Color color;

	public UnicodeTile(int codePoint,  Color color) {
		this.codePoint = codePoint;
		this.color = validateNotNull(color, "color");
	}

	public UnicodeTile(String symbol,  Color color) {
		this(stringToCodePoint(symbol), color);
	}

	@Override
	public void render(TileMap tileMap, int column, int row) {
		tileMap.renderCharacter(codePoint, column, row, color);
	}

}
