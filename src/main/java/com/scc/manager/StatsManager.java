package com.scc.manager;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.scc.data.repository.PlayerRepository;
import com.scc.data.repository.PlayerStatPerMatchRepository;
import com.scc.model.BattingStats;
import com.scc.model.BowlingStats;
import com.scc.model.Player;
import com.scc.model.PlayerStatPerMatch;

@Component
public class StatsManager {

	@Inject
	private PlayerRepository playerRepo;

	@Inject
	private PlayerStatPerMatchRepository perMatchRepository;

	public Player calculateEconomyAndBattingAverage(Player player) {
		this.calculateBattingAverage(player);
		this.calculateEconomyRate(player);
		playerRepo.save(player);
		return player;
	}

	protected Player calculateBattingAverage(Player player) {
		double battingAverage = 0;
		try {
			battingAverage = (1.00 * player.getBattingStats().getTotalRunsScored())
					/ (player.getBattingStats().getInningsBatted() - player.getBattingStats().getNotOuts());
		} catch (ArithmeticException e) {

		}
		player.getBattingStats().setBattingAverage(battingAverage);
		return player;
	}

	protected Player calculateEconomyRate(Player player) {
		double economyRate = 0;
		try {
			economyRate = (1.00 * player.getBowlingStats().getTotalRunsGiven())
					/ player.getBowlingStats().getOversBowled();
		} catch (ArithmeticException e) {
		}
		player.getBowlingStats().setEconomyRate(economyRate);
		return player;
	}

	public void updatePlayerData(PlayerStatPerMatch stat) {
		perMatchRepository.save(stat);
		Player player = playerRepo.findOne(stat.getPlayer().getId());
		stat.setPlayer(player);
		BattingStats batting = player.getBattingStats();
		BowlingStats bowling = player.getBowlingStats();
		if (stat.hasBatted()) {
			this.updateHighScore(stat);
			batting.setInningsBatted(batting.getInningsBatted() + 1);
			if (stat.isNotOut()) {
				batting.setNotOuts(batting.getNotOuts() + 1);
			}
			player.setTotalMatchesPlayed(player.getTotalMatchesPlayed() + 1);
			batting.setTotalBallsPlayed(batting.getTotalBallsPlayed() + stat.getBallsPlayed());
			batting.setTotalRunsScored(batting.getTotalRunsScored() + stat.getRunsScored());
		}
		if (stat.hasBowled()) {
			this.updateBestBowlingFigures(stat);
			bowling.setWicketsTaken(bowling.getWicketsTaken() + stat.getWicketsTaken());
			bowling.setOversBowled(bowling.getOversBowled() + stat.getOversBowled());
			bowling.setTotalRunsGiven(bowling.getTotalRunsGiven() + stat.getRunsGiven());
		}
		this.calculateEconomyAndBattingAverage(player);
		playerRepo.save(player);

	}

	protected void updateHighScore(PlayerStatPerMatch stat) {
		Player player = stat.getPlayer();
		if (stat.getRunsScored() > player.getBattingStats().getHighestScore()) {
			player.getBattingStats().setHighestScore(stat.getRunsScored());
		}
	}

	protected void updateBestBowlingFigures(PlayerStatPerMatch stat) {
		Player player = stat.getPlayer();
		if (stat.getWicketsTaken() >= player.getBowlingStats().getBestFiguresWicket()) {
			if (stat.getRunsGiven() < player.getBowlingStats().getBestFiguresRunsGiven()) {
				player.getBowlingStats().setBestFiguresRunsGiven(stat.getRunsGiven());
			}
			player.getBowlingStats().setBestFiguresWicket(stat.getWicketsTaken());
		}
	}
}
