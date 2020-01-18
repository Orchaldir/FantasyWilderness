package jfw.util.rendering;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CanvasRenderer implements Renderer {

	private final GraphicsContext graphicsContext;

	@Override
	public void clear(int x, int y, int width, int height) {
		graphicsContext.clearRect(x, y, width, height);
	}

	@Override
	public void setColor(Color color) {
		graphicsContext.setFill(color);
	}

	@Override
	public void renderRectangle(int x, int y, int width, int height) {
		graphicsContext.fillRect(x, y, width, height);
	}
}