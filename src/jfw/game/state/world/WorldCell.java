package jfw.game.state.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class WorldCell {
	private final TerrainType terrainType;
}
