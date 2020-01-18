package jfw.util.rendering;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CanvasRenderer implements Renderer {

	private final GraphicsContext graphicsContext;

	@Override
	public void clear(int x, int y, int width, int height) {
		graphicsContext.clearRect(x, y, width, height);
	}

	@Override
	public void renderCharacter(char character, int centerX, int centerY, int size) {
		graphicsContext.setFont(new Font("Liberation Mono", size));
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		graphicsContext.fillText(String.valueOf(character), centerX, centerY);
	}

	@Override
	public void renderRectangle(int x, int y, int width, int height) {
		graphicsContext.fillRect(x, y, width, height);
	}

	@Override
	public void setColor(Color color) {
		graphicsContext.setFill(color);
	}
}
