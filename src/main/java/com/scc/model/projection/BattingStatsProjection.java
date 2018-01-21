package com.scc.model.projection;

import org.springframework.data.rest.core.config.Projection;

import com.scc.model.BattingStats;
import com.scc.model.Player;

@Projection(name = "battingStats", types = { Player.class })
public interface BattingStatsProjection {

	String getPlayerName();

	Long getTotalMatchesPlayed();

	BattingStats getBattingStats();

}
