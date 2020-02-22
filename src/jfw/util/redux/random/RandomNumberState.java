package jfw.util.redux.random;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@AllArgsConstructor
public class RandomNumberState {

	private final int[] numbers;
	@Getter
	private final int index;

	public RandomNumberState(Random random, int size) {
		index = 0;
		numbers = new int[size];

		for (int i = 0; i < size; i++) {
			numbers[i] = random.nextInt();
		}
	}

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
