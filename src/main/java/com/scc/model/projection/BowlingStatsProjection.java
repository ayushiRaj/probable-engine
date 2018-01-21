package com.scc.model.projection;

import org.springframework.data.rest.core.config.Projection;

import com.scc.model.BowlingStats;
import com.scc.model.Player;

@Projection(name = "bowlingStats", types = { Player.class })
public interface BowlingStatsProjection {

	String getPlayerName();
	
	Long getTotalMatchesPlayed();

	BowlingStats getBowlingStats();
}
