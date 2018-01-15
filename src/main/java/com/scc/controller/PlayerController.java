package com.scc.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
import com.scc.util.PlayerUtil;

@RestController
public class PlayerController {

	@Inject
	private PlayerRepository playerRepository;

	@Inject
	private StatsManager statsManager;

	@RequestMapping(value = "/createPlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<String> addANewPlayer(@RequestBody Map<String, String> details, HttpServletRequest request) {
		ResponseEntity<String> response = null;
		Player player = PlayerUtil.mapNewPlayer(details);
		playerRepository.save(player);
		return response;
	}

	@RequestMapping(value = "/addPlayerData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<String> addDataForAPlayer(@RequestBody PlayerStatPerMatch stat) {
		ResponseEntity<String> response = null;
		boolean dataAdded = false;
		try {
			dataAdded = statsManager.updatePlayerData(stat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dataAdded) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

}
