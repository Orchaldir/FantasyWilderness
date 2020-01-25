package jfw.app.demo;

import org.hexworks.cobalt.events.api.Subscription;
import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.MouseEventType;

public class ZirconDemo {

	public static void main(String[] args) {

		final TileGrid tileGrid = SwingApplications.startTileGrid(
				AppConfigs.newConfig()
						.withSize(Sizes.create(34, 18))
						.withDefaultTileset(CP437TilesetResources.aduDhabi16x16())
						.build());
		final Screen screen = Screens.createScreenFor(tileGrid);

		Panel panel = Components.panel()
				//.wrapWithBox(true) // panels can be wrapped in a box
				//.withTitle("Panel") // if a panel is wrapped in a box a title can be displayed
				//.wrapWithShadow(true) // shadow can be added
				.withSize(Sizes.create(32, 16)) // the size must be smaller than the parent's size
				.withPosition(Positions.offset1x1())
				.build(); // position is always relative to the parent

		final Header header = Components.header()
				// this will be 1x1 left and down from the top left
				// corner of the panel
				.withPosition(Positions.offset1x1())
				.withText("Header")
				.build();

		final CheckBox checkBox = Components.checkBox()
				.withText("Check me!")
				.withPosition(Positions.create(0, 1)
						// the position class has some convenience methods
						// for you to specify your component's position as
						// relative to another one
						.relativeToBottomOf(header))
				.build();

		final Button left = Components.button()
				.withPosition(Positions.create(0, 1) // this means 1 row below the check box
						.relativeToBottomOf(checkBox))
				.withText("Left")
				.build();

		final Button right = Components.button()
				.withPosition(Positions.create(1, 0) // 1 column right relative to the left BUTTON
						.relativeToRightOf(left))
				.withText("Right")
				.build();

		panel.addComponent(header);
		panel.addComponent(checkBox);
		panel.addComponent(left);
		panel.addComponent(right);

		screen.addComponent(panel);

		// we can apply color themes to a screen
		screen.applyColorTheme(ColorThemes.monokaiBlue());

		// this is how you can define interactions with a component
		left.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (mouseAction,p) -> {
			screen.applyColorTheme(ColorThemes.monokaiGreen());
			return null;
		});

		right.handleMouseEvents(MouseEventType.MOUSE_RELEASED, (mouseAction,p) -> {
			screen.applyColorTheme(ColorThemes.monokaiViolet());
			return null;
		});

		//right.onMouseReleased((mouseAction -> screen.applyColorTheme(ColorThemes.monokaiViolet())));

		// in order to see the changes you need to display your screen.
		screen.display();
	}
}