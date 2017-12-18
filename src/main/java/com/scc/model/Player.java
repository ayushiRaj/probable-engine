package com.scc.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String playerName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PlayerAttribute playerAttribute;

	@Column(nullable = false)
	private Long totalMatchesPlayed;

	@Embedded
	private BattingStats battingStats;

	@Embedded
	private BowlingStats bowlingStats;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public PlayerAttribute getPlayerAttribute() {
		return playerAttribute;
	}

	public void setPlayerAttribute(PlayerAttribute playerAttribute) {
		this.playerAttribute = playerAttribute;
	}

	public Long getTotalMatchesPlayed() {
		return totalMatchesPlayed;
	}

	public void setTotalMatchesPlayed(Long totalMatchesPlayed) {
		this.totalMatchesPlayed = totalMatchesPlayed;
	}

	public BattingStats getBattingStats() {
		return battingStats;
	}

	public void setBattingStats(BattingStats battingStats) {
		this.battingStats = battingStats;
	}

	public BowlingStats getBowlingStats() {
		return bowlingStats;
	}

	public void setBowlingStats(BowlingStats bowlingStats) {
		this.bowlingStats = bowlingStats;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
