package com.scc.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scc.data.repository.PlayerRepository;
import com.scc.manager.StatsManager;
import com.scc.model.BattingStats;
import com.scc.model.BowlingStats;
import com.scc.model.Player;
import com.scc.model.PlayerAttribute;
import com.scc.model.PlayerStatPerMatch;

@RestController
public class PlayerController {

	@Inject
	private PlayerRepository playerRepository;
	
	@Inject
	private StatsManager statsManager;

	@RequestMapping(value = "/createPlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<String> addANewPlayer(@RequestBody Map<String, String> details, HttpServletRequest request) {
		ResponseEntity<String> response = null;
		Player player = mapPlayer(details);
		playerRepository.save(player);
		return response;
	}
	
	@RequestMapping(value = "/addPlayerData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<String> addDataForAPlayer(@RequestBody PlayerStatPerMatch stat){
		ResponseEntity<String> response = null;
		System.out.println(stat.toString());
		System.out.println(stat.getPlayer().toString());
		statsManager.updatePlayerData(stat);
		return response;
	}

	private Player mapPlayer(Map<String, String> playerData) {
		Player player = new Player();
		player.setPlayerName(playerData.get("name"));
		player.setPlayerAttribute(PlayerAttribute.valueOf(playerData.get("attribute")));
		player.setTotalMatchesPlayed(0L);
		BattingStats battingStats = new BattingStats();
		BowlingStats bowlingStats = new BowlingStats();
		player.setBattingStats(battingStats);
		player.setBowlingStats(bowlingStats);
		// Initializing batting stats
		player.getBattingStats().setHighestScore(0);
		player.getBattingStats().setNotOuts(0);
		player.getBattingStats().setTotalBallsPlayed(0L);
		player.getBattingStats().setTotalRunsScored(0L);
		player.getBattingStats().setBattingAverage(0.0);
		player.getBattingStats().setInningsBatted(0L);
		// Initializing bowling stats
		player.getBowlingStats().setEconomyRate(0.00);
		player.getBowlingStats().setOversBowled(0L);
		player.getBowlingStats().setWicketsTaken(0L);
		player.getBowlingStats().setBestFiguresRunsGiven(0L);
		player.getBowlingStats().setBestFiguresWicket(0L);
		player.getBowlingStats().setTotalRunsGiven(0L);
		return player;
	}
}
