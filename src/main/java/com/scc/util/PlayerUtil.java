package com.scc.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scc.model.BattingStats;
import com.scc.model.BowlingStats;
import com.scc.model.Player;
import com.scc.model.PlayerAttribute;
import com.scc.model.PlayerStatPerMatch;

public class PlayerUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(PlayerUtil.class);

	public static Player mapNewPlayer(Map<String, String> playerData) {
		Player player = new Player();
		player.setPlayerName(playerData.get("name"));
		player.setPlayerAttribute(PlayerAttribute.valueOf(playerData.get("attribute")));
		player.setTotalMatchesPlayed(0L);
		BattingStats battingStats = new BattingStats();
		BowlingStats bowlingStats = new BowlingStats();
		player.setBattingStats(battingStats);
		player.setBowlingStats(bowlingStats);
		// Initializing batting stats
		initializeBattingStats(player);
		// Initializing bowling stats
		initializeBowlingStats(player);
		LOGGER.info("Saving new player : {}", player.getPlayerName());
		return player;
	}

	private static void initializeBowlingStats(Player player) {
		if (Objects.nonNull(player)) {
			player.getBowlingStats().setEconomyRate(0.00);
			player.getBowlingStats().setOversBowled(0L);
			player.getBowlingStats().setWicketsTaken(0L);
			player.getBowlingStats().setBestFiguresRunsGiven(0L);
			player.getBowlingStats().setBestFiguresWicket(0L);
			player.getBowlingStats().setTotalRunsGiven(0L);
			player.getBowlingStats().setTotalNoBallsBowled(0);
			player.getBowlingStats().setTotalWidesBowled(0);
		}
	}

	private static void initializeBattingStats(Player player) {
		if (Objects.nonNull(player)) {
			player.getBattingStats().setHighestScore(0);
			player.getBattingStats().setNotOuts(0);
			player.getBattingStats().setTotalBallsPlayed(0L);
			player.getBattingStats().setTotalRunsScored(0L);
			player.getBattingStats().setBattingAverage(0.0);
			player.getBattingStats().setInningsBatted(0L);
			player.getBattingStats().setStrikeRate(0.0);
			player.getBattingStats().setTotalSixesScored(0);
			player.getBattingStats().setTotalFoursScored(0);
		}
	}

	public static Player recalculateStatsForAPlayer(Player player, List<PlayerStatPerMatch> matchRecords) {

		return player;
	}

}
