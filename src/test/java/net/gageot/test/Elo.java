package net.gageot.test;

import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;

@Singleton
public class Elo {
	private static final int MAX = 10;
	private static final int START_SCORE = 1000;

	private final AtomicInteger[] playedPerKitten;
	private final AtomicInteger[] scorePerKitten;

	public Elo() {
		playedPerKitten = new AtomicInteger[MAX];
		scorePerKitten = new AtomicInteger[MAX];
		for (int i = 0; i < MAX; i++) {
			playedPerKitten[i] = new AtomicInteger(0);
			scorePerKitten[i] = new AtomicInteger(START_SCORE);
		}
	}

	public int get(int kittenId) {
		int score = scorePerKitten[kittenId].get();

		int ranking = 1;
		for (AtomicInteger element : scorePerKitten) {
			if (element.get() > score) {
				ranking++;
			}
		}

		return ranking;
	}

	public void vote(int kittenIdWinner, int kittenIdLoser) {
		int score1 = scorePerKitten[kittenIdWinner].get();
		int score2 = scorePerKitten[kittenIdLoser].get();
		int d = Math.min(400, Math.abs(score1 - score2));
		float p = 1f / (1f + (float) Math.pow(10, -d / 400f));
		int k1 = k(score1, playedPerKitten[kittenIdWinner].incrementAndGet());
		int k2 = k(score2, playedPerKitten[kittenIdLoser].incrementAndGet());

		int r1 = Math.round(score1 + (k1 * (1 - p)));
		int r2 = Math.round(score2 + (k2 * (0 - p)));

		scorePerKitten[kittenIdWinner].set(r1);
		scorePerKitten[kittenIdLoser].set(r2);
	}

	private int k(int score, int played) {
		return played < 30 ? 25 : score < 2400 ? 15 : 10;
	}
}