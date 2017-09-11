package com.scc.model.projection;

import org.springframework.data.rest.core.config.Projection;

import com.scc.model.Player;

@Projection(name="getPlayerNames",types={Player.class})
public interface PlayerNameProjection {

	String getPlayerName();
	Long getId();
}
