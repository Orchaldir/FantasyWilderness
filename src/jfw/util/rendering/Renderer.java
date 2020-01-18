package jfw.util.rendering;

import javafx.scene.paint.Color;

public interface Renderer {

	void clear(int x, int y, int width, int height);

	void setColor(Color color);

	void renderRectangle(int x, int y, int width, int height);
}
