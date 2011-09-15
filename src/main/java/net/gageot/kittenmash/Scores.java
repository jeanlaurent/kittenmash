package net.gageot.kittenmash;

public class Scores {
	private final int[] scores = new int[11];

	public int get(String kittenId) {
		return scores[Integer.parseInt(kittenId)];
	}

	public void win(String kittenId) {
		scores[Integer.parseInt(kittenId)]++;
	}
}
