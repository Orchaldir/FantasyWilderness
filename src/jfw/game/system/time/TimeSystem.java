package jfw.game.system.time;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static jfw.util.Validator.validateNotEmpty;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class TimeSystem {

	private final PriorityQueue<TimeEntry> queue;

	public TimeSystem(List<TimeEntry> entries) {
		validateNotEmpty(entries, "entries");
		queue = new PriorityQueue<>(entries);
	}

	public TimeSystem addEntries(List<TimeEntry> entriesToAdd) {
		PriorityQueue<TimeEntry> newQueue = new PriorityQueue<>(queue);

		newQueue.addAll(entriesToAdd);

		return new TimeSystem(newQueue);
	}

	public TimeSystem removeEntries(List<TimeEntry> entriesToRemove) {
		PriorityQueue<TimeEntry> newQueue = new PriorityQueue<>(queue);

		newQueue.removeAll(entriesToRemove);

		return new TimeSystem(newQueue);
	}

	public TimeSystem advanceCurrentEntry(long duration) {
		PriorityQueue<TimeEntry> newQueue = new PriorityQueue<>(queue);
		TimeEntry firstEntry = newQueue.poll();
		TimeEntry updatedEntry = firstEntry.apply(duration);

		newQueue.add(updatedEntry);

		return new TimeSystem(newQueue);
	}

	public List<TimeEntry> getAllEntries() {
		return queue.stream()
				.sorted()
				.collect(Collectors.toList());
	}

	public TimeEntry getCurrentEntry() {
		return queue.peek();
	}

	public long getCurrentTime() {
		return queue.peek().getTime();
	}

}
