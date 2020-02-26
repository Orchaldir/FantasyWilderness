package jfw.game.view;

import javafx.scene.paint.Color;
import jfw.game.content.skill.Skill;
import jfw.game.state.State;
import jfw.util.tile.FullTile;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;

import java.util.Map;
import java.util.function.Supplier;

import static jfw.util.Validator.validateNotNull;

public class StatusView implements View {

	public static final FullTile BACKGROUND_TILE = new FullTile(new Color(0.81, 0.78, 0.69, 1.0));

	private final TileRenderer tileRenderer;

	public StatusView(TileRenderer tileRenderer) {
		this.tileRenderer = validateNotNull(tileRenderer, "tileRenderer");
	}

	@Override
	public void render(State state, Supplier<TileMap> supplier) {
		int entityId = state.getCurrentEntityId();
		Map<Skill, Integer> skillMap = state.getSkillMap(entityId);

		TileMap background = supplier.get();
		background.setTile(BACKGROUND_TILE);
		background.render(tileRenderer);

		int row = 0;

		TileMap status = supplier.get();
		status.setText("Name=" + state.getName(entityId), 0, row++, Color.BLACK);

		row++;

		if (!skillMap.isEmpty()) {
			status.setText("Skill:", 0, row++, Color.BLACK);

			for (Map.Entry<Skill, Integer> entry : skillMap.entrySet()) {
				status.setText(entry.getKey().getName() + ":" + entry.getValue(), 2, row++, Color.BLACK);
			}
		}

		status.render(tileRenderer);
	}

}
