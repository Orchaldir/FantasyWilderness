package jfw.util.ecs;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ComponentMap<Component> implements ComponentStorage<Component> {

	private final Map<Integer, Component> map;

	@Override
	public Optional<Component> get(int id) {
		return Optional.ofNullable(map.get(id));
	}

	@Override
	public Collection<Component> getAll() {
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Set<Integer> getIds() {
		return Collections.unmodifiableSet(map.keySet());
	}

}
