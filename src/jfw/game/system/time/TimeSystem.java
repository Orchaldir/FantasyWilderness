package jfw.game.system.time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jfw.util.Validator.validateNotEmpty;

public class TimeSystem {

	private final List<TimeEntry> queue;

	public TimeSystem(List<TimeEntry> entries) {
		validateNotEmpty(entries, "entries");
		queue = new ArrayList<>();

		entries.forEach(entry -> insert(queue, entry));
	}

	public TimeSystem addEntries(List<TimeEntry> entriesToAdd) {
		List<TimeEntry> newQueue = new ArrayList<>(queue);

		entriesToAdd.forEach(entry -> insert(newQueue, entry));

		return new TimeSystem(newQueue);
	}

	public TimeSystem removeEntries(List<TimeEntry> entriesToRemove) {
		List<TimeEntry> newQueue = new ArrayList<>(queue);

		newQueue.removeAll(entriesToRemove);

		return new TimeSystem(newQueue);
	}

	public TimeSystem advanceCurrentEntry(long duration) {
		List<TimeEntry> newQueue = new ArrayList<>(queue);
		TimeEntry firstEntry = getCurrentEntry();
		TimeEntry updatedEntry = firstEntry.apply(duration);

		newQueue.remove(firstEntry);
		insert(newQueue, updatedEntry);

		return new TimeSystem(newQueue);
	}

	private static void insert(List<TimeEntry> queue, TimeEntry entry) {
		for (int i = 0; i < queue.size(); i++) {
			if (entry.getTime() < queue.get(i).getTime()) {
				queue.add(i, entry);
				return;
			}
		}

		queue.add(entry);
	}

	public List<TimeEntry> getAllEntries() {
		return queue.stream()
				.sorted()
				.collect(Collectors.toList());
	}

	public TimeEntry getCurrentEntry() {
		return queue.get(0);
	}

	public long getCurrentTime() {
		return getCurrentEntry().getTime();
	}

}
