package com.scc.manager;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.scc.data.repository.PlayerRepository;
import com.scc.model.Player;

@Component
public class StatsManager {

	@Inject
	private PlayerRepository repo;

	public Player calculateEconomyAndBattingAverage(Player player) {
		this.calculateBattingAverage(player);
		this.calculateEconomyRate(player);
		repo.save(player);
		return player;
	}

	protected Player calculateBattingAverage(Player player) {
		double battingAverage = player.getBattingStats().getTotalRunsScored()
				/ (player.getBattingStats().getInningsBatted() - player.getBattingStats().getNotOuts());
		player.getBattingStats().setBattingAverage(battingAverage);
		return player;
	}

	protected Player calculateEconomyRate(Player player) {
		double economyRate = player.getBowlingStats().getTotalRunsGiven() / player.getBowlingStats().getOversBowled();
		player.getBowlingStats().setEconomyRate(economyRate);
		return player;
	}
}
