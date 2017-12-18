package com.scc.manager;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger LOGGER = LoggerFactory.getLogger(StatsManager.class);

	public Player calculateEconomyAndBattingAverageAndStrikeRate(Player player) {
		this.calculateBattingAverage(player);
		this.calculateEconomyRate(player);
		this.calculateStrikeRate(player);
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
			LOGGER.error(
					"Arithmetic exception while calculating batting average, default setting to 0 for player name : {}",
					player.getPlayerName());
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
			LOGGER.error("Arithmetic exception while calculating economy, default setting to 0 for player name : {}",
					player.getPlayerName());
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
			this.calculateEconomyAndBattingAverageAndStrikeRate(player);
			player.setTotalMatchesPlayed(player.getTotalMatchesPlayed() + 1);
			playerRepo.save(player);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error caught while adding match data for playerId: {}", stat.getPlayer().getId());
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
		if (stat.getWicketsTaken() >= player.getBowlingStats().getBestFiguresWicket()) {
			player.getBowlingStats().setBestFiguresWicket(stat.getWicketsTaken());
			if (stat.getRunsGiven() < player.getBowlingStats().getBestFiguresRunsGiven()) {
				player.getBowlingStats().setBestFiguresRunsGiven(stat.getRunsGiven());
			}
		}
	}

	protected Player calculateStrikeRate(Player player) {
		double strikeRate = 0;
		try {
			strikeRate = (100.00 * player.getBattingStats().getTotalRunsScored())
					/ (player.getBattingStats().getTotalBallsPlayed());
			String df = String.format("%.2f", strikeRate);
			strikeRate = Double.parseDouble(df);
		} catch (ArithmeticException e) {
			LOGGER.error(
					"Arithmetic exception while calculating strike rate, default setting to 0 for player name : {}",
					player.getPlayerName());
		}
		 player.getBattingStats().setStrikeRate(strikeRate);
		return player;
	}
}
