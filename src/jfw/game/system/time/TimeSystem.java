package jfw.game.system.time;

import jfw.game.system.time.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jfw.util.Validator.validateNotEmpty;

public class TimeSystem {

	private final List<Event> queue;

	public TimeSystem(List<Event> entries) {
		validateNotEmpty(entries, "entries");
		queue = new ArrayList<>();

		entries.forEach(entry -> insert(queue, entry));
	}

	public TimeSystem addEntries(List<Event> entriesToAdd) {
		List<Event> newQueue = new ArrayList<>(queue);

		entriesToAdd.forEach(entry -> insert(newQueue, entry));

		return new TimeSystem(newQueue);
	}

	public TimeSystem removeEntries(List<Event> entriesToRemove) {
		List<Event> newQueue = new ArrayList<>(queue);

		newQueue.removeAll(entriesToRemove);

		return new TimeSystem(newQueue);
	}

	public TimeSystem advanceCurrentEntry(long duration) {
		List<Event> newQueue = new ArrayList<>(queue);
		Event firstEntry = getCurrentEntry();
		Event updatedEntry = firstEntry.apply(duration);

		newQueue.remove(firstEntry);
		insert(newQueue, updatedEntry);

		return new TimeSystem(newQueue);
	}

	private static void insert(List<Event> queue, Event entry) {
		for (int i = 0; i < queue.size(); i++) {
			if (entry.getTime() < queue.get(i).getTime()) {
				queue.add(i, entry);
				return;
			}
		}

		queue.add(entry);
	}

	public List<Event> getAllEntries() {
		return queue.stream()
				.sorted()
				.collect(Collectors.toList());
	}

	public Event getCurrentEntry() {
		return queue.get(0);
	}

	public long getCurrentTime() {
		return getCurrentEntry().getTime();
	}

}
