package jfw.util.ecs;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ComponentStorage<COMPONENT> {

	Optional<COMPONENT> get(int entityId);

	Collection<COMPONENT> getAll();

	Set<Integer> getIds();

}
