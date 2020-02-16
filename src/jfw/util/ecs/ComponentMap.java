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

	@Override
	public void visit(Visitor<T> visitor) {
		for (Map.Entry<Integer, T> entry : map.entrySet()) {
			visitor.visit(entry.getKey(),  entry.getValue());
		}
	}

}
