package jfw.util.ecs;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ComponentStorage<T> {

	Optional<T> get(int entityId);

	Collection<T> getAll();

	Set<Integer> getIds();

}
