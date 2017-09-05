package com.scc.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Embeddable
public class BowlingStats {

	@Column(nullable = false)
	private Long oversBowled;

	@Column(nullable = false)
	private Long wicketsTaken;

	@Column(nullable = false)
	private Double economyRate;

	@Column(nullable = false)
	private Long bestFiguresWicket;

	@Column(nullable = false)
	private Long bestFiguresRunsGiven;

	public Long getOversBowled() {
		return oversBowled;
	}

	public void setOversBowled(Long oversBowled) {
		this.oversBowled = oversBowled;
	}

	public Long getWicketsTaken() {
		return wicketsTaken;
	}

	public void setWicketsTaken(Long wicketsTaken) {
		this.wicketsTaken = wicketsTaken;
	}

	public Double getEconomyRate() {
		return economyRate;
	}

	public void setEconomyRate(Double economyRate) {
		this.economyRate = economyRate;
	}

	public Long getBestFiguresWicket() {
		return bestFiguresWicket;
	}

	public void setBestFiguresWicket(Long bestFiguresWicket) {
		this.bestFiguresWicket = bestFiguresWicket;
	}

	public Long getBestFiguresRunsGiven() {
		return bestFiguresRunsGiven;
	}

	public void setBestFiguresRunsGiven(Long bestFiguresRunsGiven) {
		this.bestFiguresRunsGiven = bestFiguresRunsGiven;
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
