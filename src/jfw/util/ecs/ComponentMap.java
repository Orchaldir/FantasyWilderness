package jfw.util.ecs;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ComponentMap<T> implements ComponentStorage<T> {

	private final Map<Integer, T> map;

	@Override
	public Optional<T> get(int id) {
		return Optional.ofNullable(map.get(id));
	}

	@Override
	public Collection<T> getAll() {
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Set<Integer> getIds() {
		return Collections.unmodifiableSet(map.keySet());
	}

}
