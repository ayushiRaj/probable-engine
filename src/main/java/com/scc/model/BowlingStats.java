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

	@Column(nullable = false)
	private Long totalRunsGiven;

	@Column(nullable = false)
	private Integer totalWidesBowled;

	@Column(nullable = false)
	private Integer totalNoBallsBowled;

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

	public Long getTotalRunsGiven() {
		return totalRunsGiven;
	}

	public void setTotalRunsGiven(Long totalRunsGiven) {
		this.totalRunsGiven = totalRunsGiven;
	}

	public Integer getTotalWidesBowled() {
		return totalWidesBowled;
	}

	public void setTotalWidesBowled(Integer totalWidesBowled) {
		this.totalWidesBowled = totalWidesBowled;
	}

	public Integer getTotalNoBallsBowled() {
		return totalNoBallsBowled;
	}

	public void setTotalNoBallsBowled(Integer totalNoBallsBowled) {
		this.totalNoBallsBowled = totalNoBallsBowled;
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
