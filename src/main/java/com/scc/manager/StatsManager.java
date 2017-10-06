package com.scc.manager;

import javax.inject.Inject;
import javax.transaction.Transactional;

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
			if((player.getBattingStats().getInningsBatted()-player.getBattingStats().getNotOuts()) > 0){
				battingAverage = (1.00 * player.getBattingStats().getTotalRunsScored())
						/ (player.getBattingStats().getInningsBatted() - player.getBattingStats().getNotOuts());
				String df = String.format("%.2f", battingAverage);
				battingAverage = Double.parseDouble(df);
			}
		} catch (ArithmeticException e) {

		}
		player.getBattingStats().setBattingAverage(battingAverage);
		return player;
	}

	protected Player calculateEconomyRate(Player player) {
		double economyRate = 0;
		try {
			if (player.getBowlingStats().getOversBowled() > 0) {
				economyRate = (1.00 * player.getBowlingStats().getTotalRunsGiven())
						/ player.getBowlingStats().getOversBowled();
				String df = String.format("%.2f", economyRate);
				economyRate = Double.parseDouble(df);
			}
		} catch (ArithmeticException e) {
		} catch (Exception e) {
		}
		player.getBowlingStats().setEconomyRate(economyRate);
		return player;
	}

	@Transactional
	public boolean updatePlayerData(PlayerStatPerMatch stat) {
		try {
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
			player.setTotalMatchesPlayed(player.getTotalMatchesPlayed() + 1);
			playerRepo.save(player);
			return true;
		} catch (Exception e) {
			System.out.println("Exception caught");
			return false;
		}

	}

	protected void updateHighScore(PlayerStatPerMatch stat) {
		Player player = stat.getPlayer();
		if (stat.getRunsScored() > player.getBattingStats().getHighestScore()) {
			player.getBattingStats().setHighestScore(stat.getRunsScored());
		}
	}

	protected void updateBestBowlingFigures(PlayerStatPerMatch stat) {
		Player player = stat.getPlayer();
		if (player.getBowlingStats().getBestFiguresWicket() == 0 && stat.getWicketsTaken() == 0) {
			if (player.getBowlingStats().getOversBowled() == 0 && player.getBowlingStats().getTotalRunsGiven() == 0) {
				player.getBowlingStats().setBestFiguresRunsGiven(stat.getRunsGiven());
			}
		}
		System.out.println(player.getBowlingStats().getBestFiguresRunsGiven());
		if (stat.getWicketsTaken() >= player.getBowlingStats().getBestFiguresWicket()) {
			player.getBowlingStats().setBestFiguresWicket(stat.getWicketsTaken());
			if (stat.getRunsGiven() < player.getBowlingStats().getBestFiguresRunsGiven()) {
				player.getBowlingStats().setBestFiguresRunsGiven(stat.getRunsGiven());
			}

		}
		System.out.println(player.getBowlingStats().getBestFiguresRunsGiven());
	}
}
