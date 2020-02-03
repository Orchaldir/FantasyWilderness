package jfw.util.ecs;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ComponentMap<COMPONENT> implements ComponentStorage<COMPONENT> {

	private final Map<Integer, COMPONENT> map;

	@Override
	public Optional<COMPONENT> get(int id) {
		return Optional.ofNullable(map.get(id));
	}

	@Override
	public Collection<COMPONENT> getAll() {
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Set<Integer> getIds() {
		return Collections.unmodifiableSet(map.keySet());
	}

}
