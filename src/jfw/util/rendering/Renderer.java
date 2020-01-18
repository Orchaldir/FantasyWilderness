package jfw.util.rendering;

import javafx.scene.paint.Color;

public interface Renderer {

	void clear(int x, int y, int width, int height);

	void renderCharacter(char character, int centerX, int centerY, int size);

	void renderRectangle(int x, int y, int width, int height);

	void setColor(Color color);
}
