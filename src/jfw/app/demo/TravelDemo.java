package jfw.app.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfw.game.action.MoveEntity;
import jfw.game.content.skill.Skill;
import jfw.game.reducer.MoveEntityReducer;
import jfw.game.state.State;
import jfw.game.state.component.Statistics;
import jfw.game.state.world.TerrainType;
import jfw.game.state.world.WorldCell;
import jfw.game.system.time.event.EntityEntry;
import jfw.game.system.time.event.Event;
import jfw.game.system.time.TimeSystem;
import jfw.game.view.StatusView;
import jfw.game.view.TravelView;
import jfw.game.view.View;
import jfw.util.TileApplication;
import jfw.util.ecs.ComponentMap;
import jfw.util.ecs.ComponentStorage;
import jfw.util.map.ArrayMap2d;
import jfw.util.redux.Reducer;
import jfw.util.redux.Store;
import jfw.util.redux.middleware.LogActionMiddleware;
import lombok.extern.slf4j.Slf4j;

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
	private StatusView statusView;
	private TravelView travelView;
	private View currentView;

	@Override
	public void start(Stage primaryStage) {
		Scene scene = init(primaryStage, "Travel Demo", 50, 30, 22, 32);

		create();

		scene.setOnKeyReleased(event -> onKeyReleased(event.getCode()));
	}

	private void create() {
		log.info("create()");

		WorldCell[] cells = new WorldCell[getTiles()];
		ArrayMap2d<WorldCell> worldMap = new ArrayMap2d<>(getColumns(), getRows(), cells, new WorldCell(TerrainType.PLAIN));

		int aragornId = 0;
		ComponentStorage<String> names = new ComponentMap<>(Map.of(aragornId, "Aragorn"));

		Map<Integer,Integer> positionMap = new HashMap<>();
		positionMap.put(aragornId, 88);
		positionMap.put(1, 44);
		positionMap.put(2, 122);
		ComponentStorage<Integer> positions = new ComponentMap<>(positionMap);

		Skill survival  = new Skill("Survival");
		Skill fighting  = new Skill("Fighting");
		Statistics statistics = new Statistics(Map.of(survival, 7,  fighting, 9));
		ComponentStorage<Statistics> statisticsStorage = new ComponentMap<>(Map.of(aragornId, statistics));

		List<Event> entries = positions.getIds().stream().map(EntityEntry::new).collect(Collectors.toList());
		TimeSystem timeSystem = new TimeSystem(entries);

		State initState = new State(worldMap, names, positions, statisticsStorage, timeSystem);
		store = new Store<>(REDUCER, initState, List.of(new LogActionMiddleware<>()));

		statusView = new StatusView(tileRenderer);
		travelView = new TravelView(store, tileRenderer, Color.BLACK);
		currentView = travelView;

		store.subscribe(this::render);
	}

	private void render(State state) {
		log.info("render()");

		currentView.render(state, this::createTileMap);

		log.info("render(): finished");
	}

	private void onKeyReleased(KeyCode keyCode) {
		log.info("onKeyReleased(): keyCode={}", keyCode);

		if (keyCode == KeyCode.F1) {
			switchView(travelView);
			return;
		}
		else if (keyCode == KeyCode.F2) {
			switchView(statusView);
			return;
		}

		currentView.onKeyReleased(keyCode);
	}

	private void switchView(View view) {
		currentView = view;
		render(store.getState());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
