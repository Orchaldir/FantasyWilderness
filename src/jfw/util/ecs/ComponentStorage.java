package jfw.util.ecs;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ComponentStorage<Component> {

	Optional<Component> get(int entityId);

	Collection<Component> getAll();

	Set<Integer> getIds();

}
