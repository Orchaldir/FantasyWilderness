package jfw.app.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jfw.game.action.MoveEntity;
import jfw.game.reducer.MoveEntityReducer;
import jfw.game.state.State;
import jfw.game.state.component.Statistics;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.TimeEntry;
import jfw.game.system.time.TimeSystem;
import jfw.game.view.TravelView;
import jfw.util.TileApplication;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TravelDemo extends TileApplication {

	private static final Reducer<Object, State> REDUCER = (action, oldState) -> {
		if (action instanceof MoveEntity) {
			return MoveEntityReducer.REDUCER.reduce((MoveEntity) action, oldState);
		}

		return oldState;
	};

	private Store<Object, State> store;
	private TravelView travelView;

	@Override
	public void start(Stage primaryStage) {
		Scene scene = init(primaryStage, "Travel Demo", 20, 10, 22, 32);

		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));

		create();
	}

	private void create() {
		log.info("create()");

		WorldCell[] cells = new WorldCell[getTiles()];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(getColumns(), getRows(), cells, new WorldCell(TerrainType.PLAIN));

		ComponentStorage<String> names = new ComponentMap<>(Map.of(0, "Aragorn"));

		Map<Integer,Integer> positionMap = new HashMap<>();
		positionMap.put(0, 88);
		positionMap.put(1, 44);
		positionMap.put(2, 122);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		ComponentStorage<Statistics> statisticsStorage = new ComponentMap<>(Collections.emptyMap());

		List<TimeEntry> entries = positions.getIds().stream().map(TimeEntry::new).collect(Collectors.toList());
		TimeSystem timeSystem = new TimeSystem(entries);

		State initState = new State(worldMap, names, positions, statisticsStorage, timeSystem);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>()));

		travelView = new TravelView(store, tileRenderer);

		store.subscribe(this::render);
	}

	private void render(State state) {
		log.info("render()");

		travelView.render(state, this::createTileMap);

		log.info("render(): finished");
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		travelView.onKeyReleased(keyCode);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
