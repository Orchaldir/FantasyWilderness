package jfw.app.demo;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.util.TileApplication;
import jfw.util.map.ArrayMap2d;
import jfw.util.map.Map2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import jfw.util.redux.middleware.LogDiffMiddleware;
import jfw.util.tile.rendering.TileMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static jfw.game.state.world.WorldCell.TILE_CONVERTER;

@Slf4j
public class WorldDemo extends TileApplication {

	@AllArgsConstructor
	@ToString
	private static class DemoState {
		private final ArrayMap2d<WorldCell> worldMap;
		private final TerrainType tool;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	private static class ChangeTerrain {
		private final int index;
		private final TerrainType terrainType;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	private static class SwitchTool {
		private final TerrainType tool;
	}

	private static final Reducer<Object, DemoState> REDUCER = (action, oldState) -> {
		if (action instanceof ChangeTerrain) {
			ChangeTerrain changeTerrain = (ChangeTerrain) action;

			if (oldState.worldMap.getNode(changeTerrain.index).getTerrainType() == changeTerrain.terrainType) {
				return oldState;
			}

			WorldCell newCell = new WorldCell(changeTerrain.terrainType);
			ArrayMap2d<WorldCell> newWorldMap = oldState.worldMap.withCell(newCell, changeTerrain.index);

			return new DemoState(newWorldMap, oldState.tool);
		}
		else if (action instanceof SwitchTool) {
			SwitchTool switchTool = (SwitchTool) action;

			if (switchTool.tool != oldState.tool) {
				return new DemoState(oldState.worldMap, switchTool.tool);
			}
		}

		return oldState;
	};

	private Store<Object, DemoState> store;

	@Override
	public void start(Stage primaryStage) {
		Scene scene = init(primaryStage, "World Demo", 20, 10, 22, 32);

		EventHandler<MouseEvent> mouseEventEventHandler = event -> onMouseClicked((int) event.getX(), (int) event.getY());

		scene.setOnMouseClicked(mouseEventEventHandler);
		scene.setOnMouseDragged(mouseEventEventHandler);

		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));
		create();
	}

	private void create() {
		log.info("create()");

		WorldCell[] cells = new WorldCell[getTiles()];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(getColumns(), getRows(), cells, new WorldCell(TerrainType.PLAIN));

		DemoState initState = new DemoState(worldMap, TerrainType.MOUNTAIN);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>(), new LogDiffMiddleware<>()));

		store.subscribe(this::render);
	}

	private void render(DemoState state) {
		log.info("render()");

		TileMap worldMap = createTileMap();
		worldMap.setMap(state.worldMap, 0, 0, TILE_CONVERTER);
		worldMap.render(tileRenderer);

		TileMap uiMap = createTileMap();
		uiMap.setText("Tool=" + state.tool, 0, 9, Color.BLACK);
		uiMap.render(tileRenderer);

		log.info("render(): finished");
	}

	private void onMouseClicked(int x, int y) {
		int column = tileRenderer.getColumn(x);
		int row = tileRenderer.getRow(y);
		log.info("onMouseClicked(): screen={}|{} -> tile={}|{}", x, y, column, row);

		DemoState state = store.getState();
		Map2d<WorldCell> map = state.worldMap;

		if (map.isInside(column, row)) {
			ChangeTerrain action = new ChangeTerrain(map.getIndex(column, row), state.tool);
			store.dispatch(action);
		}
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		if (keyCode == KeyCode.DIGIT1) {
			store.dispatch(new SwitchTool(TerrainType.PLAIN));
		}
		else if (keyCode == KeyCode.DIGIT2) {
			store.dispatch(new SwitchTool(TerrainType.HILL));
		}
		else if (keyCode == KeyCode.DIGIT3) {
			store.dispatch(new SwitchTool(TerrainType.MOUNTAIN));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
