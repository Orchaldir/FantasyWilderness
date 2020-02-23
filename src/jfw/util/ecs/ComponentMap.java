package jfw.util.ecs;

import lombok.AllArgsConstructor;

import java.util.*;

import static jfw.util.Validator.validateNotNull;

@AllArgsConstructor
public class ComponentMap<T> implements ComponentStorage<T> {

	private final Map<Integer, T> map;

	@Override
	public ComponentStorage<T> updateComponent(int entityId, T component) {
		validateNotNull(component, "component");
		Map<Integer, T> newMap = new HashMap<>(map);
		newMap.put(entityId, component);
		return new ComponentMap<>(newMap);
	}

	@Override
	public T get(int entityId) {
		T component = map.get(entityId);

		if (component == null) {
			throw new NullPointerException(String.format("No component for entity %d!", entityId));
		}

		return component;
	}

	@Override
	public Optional<T> getOptional(int entityId) {
		return Optional.ofNullable(map.get(entityId));
	}

	@Override
	public Collection<T> getAll() {
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Set<Integer> getIds() {
		return Collections.unmodifiableSet(map.keySet());
	}

	@Override
	public void visit(Visitor<T> visitor) {
		for (Map.Entry<Integer, T> entry : map.entrySet()) {
			visitor.visit(entry.getKey(),  entry.getValue());
		}
	}

}
