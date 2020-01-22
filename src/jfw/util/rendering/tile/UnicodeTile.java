package jfw.util.rendering.tile;

import javafx.scene.paint.Color;
import jfw.util.rendering.TileMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import static jfw.util.Validator.validateNotNull;
import static jfw.util.Validator.validateUnicode;

@AllArgsConstructor
@Getter
public class UnicodeTile implements Tile {

	private final int codePoint;
	@NonNull
	private final Color color;

	public UnicodeTile(String symbol,  Color color) {
		validateUnicode(symbol, 1, "symbol");
		this.codePoint = symbol.codePointAt(0);
		this.color = validateNotNull(color, "color");
	}

	@Override
	public void render(TileMap tileMap, int column, int row) {
		tileMap.renderCharacter(codePoint, column, row, color);
	}

}
