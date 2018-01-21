package com.scc.manager;

import java.util.List;

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
import com.scc.util.PlayerUtil;
import com.scc.util.StatsUtil;

@Component
public class StatsManager {

	@Inject
	private PlayerRepository playerRepo;

	@Inject
	private PlayerStatPerMatchRepository perMatchRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(StatsManager.class);

	public Player calculateEconomyAndBattingAverageAndStrikeRate(Player player) {
		player = StatsUtil.calculateBattingAverage(player);
		player = StatsUtil.calculateEconomyRate(player);
		player = StatsUtil.calculateStrikeRate(player);
		playerRepo.save(player);
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
				batting.setTotalFoursScored(batting.getTotalFoursScored() + stat.getFours());
				batting.setTotalSixesScored(batting.getTotalSixesScored() + stat.getSixes());
			}
			if (stat.hasBowled()) {
				this.updateBestBowlingFigures(stat);
				bowling.setWicketsTaken(bowling.getWicketsTaken() + stat.getWicketsTaken());
				bowling.setOversBowled(bowling.getOversBowled() + stat.getOversBowled());
				bowling.setTotalRunsGiven(bowling.getTotalRunsGiven() + stat.getRunsGiven());
				bowling.setTotalWidesBowled(bowling.getTotalWidesBowled() + stat.getWides());
				bowling.setTotalNoBallsBowled(bowling.getTotalNoBallsBowled() + stat.getNoBalls());
			}
			this.calculateEconomyAndBattingAverageAndStrikeRate(player);
			player.setTotalMatchesPlayed(player.getTotalMatchesPlayed() + 1);
			playerRepo.save(player);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error caught while adding match data for playerId: {}", stat.getPlayer().getId());
			LOGGER.error("Stringified stat object: {}", stat.toString());
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

	public void reCalculatePlayerStatsAfterEdit(Long playerId) {
		LOGGER.info("Request for recalculation with player-id: {}", playerId);
		List<PlayerStatPerMatch> matchRecords = perMatchRepository.findByPlayerId(playerId, null);
		Player player = playerRepo.findOne(playerId);
		LOGGER.info("Recalculating stats for player name : {}", player.getPlayerName());
		player.setTotalMatchesPlayed(matchRecords.size() + 0L);
		PlayerUtil.recalculateStatsForAPlayer(player, matchRecords);
	}
}
