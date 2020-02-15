package jfw.util.ecs;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ComponentStorage<T> {

	ComponentStorage<T> updateComponent(int entityId, T component);

	Optional<T> get(int entityId);

	Collection<T> getAll();

	Set<Integer> getIds();

	void visit(Visitor<T> visitor);

}
