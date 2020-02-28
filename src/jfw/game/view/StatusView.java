package jfw.game.view;

import javafx.scene.paint.Color;
import jfw.game.content.skill.Skill;
import jfw.game.state.State;
import jfw.util.tile.FullTile;
import jfw.util.tile.rendering.TileMap;
import jfw.util.tile.rendering.TileRenderer;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static jfw.util.Validator.validateNotNull;

public class StatusView implements View {

	public static final FullTile BACKGROUND_TILE = new FullTile(new Color(0.81, 0.78, 0.69, 1.0));
	public static final Color FONT_COLOR = Color.BLACK;

	private final TileRenderer tileRenderer;

	public StatusView(TileRenderer tileRenderer) {
		this.tileRenderer = validateNotNull(tileRenderer, "tileRenderer");
	}

	@Override
	public void render(State state, Supplier<TileMap> supplier) {
		TileMap background = supplier.get();
		background.setTile(BACKGROUND_TILE);
		background.render(tileRenderer);

		state.getCurrentEntityId().ifPresent(entityId -> {
			Map<Skill, Integer> skillMap = state.getSkillMap(entityId);

			int row = 0;

			TileMap status = supplier.get();
			status.setText("Name=" + state.getName(entityId), 0, row++, FONT_COLOR);

			row++;

			if (!skillMap.isEmpty()) {
				status.setText("Skills:", 0, row++, FONT_COLOR);

				List<Skill> skills = skillMap.keySet().stream().sorted().collect(Collectors.toList());

				for (Skill skill : skills) {
					status.setText(skill.getName() + ":" + skillMap.get(skill), 2, row++, FONT_COLOR);
				}
			}

			status.render(tileRenderer);
		});
	}

}
