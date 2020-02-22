package jfw.util.redux.random;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RandomNumberState {

	private final int[] numbers;
	@Getter
	private final int index;

	public RandomNumberState with(int newIndex) {
		if (newIndex == index) {
			return this;
		}

		return new RandomNumberState(numbers, newIndex);
	}

	public int getNumber(int index) {
		return numbers[index % numbers.length];
	}
}
