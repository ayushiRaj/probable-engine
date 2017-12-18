package com.scc.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Embeddable
public class BattingStats {

	@Column(nullable=false)
	private Long inningsBatted;
	
	@Column(nullable=false)
	private Long totalRunsScored;
	
	@Column(nullable=false)
	private Long totalBallsPlayed;
	
	@Column(nullable=false)
	private Integer notOuts;
	
	@Column(nullable=false)
	private Double battingAverage;
	
	@Column(nullable=false)
	private Integer highestScore;
	
	private Double strikeRate;

	public Long getInningsBatted() {
		return inningsBatted;
	}

	public void setInningsBatted(Long inningsBatted) {
		this.inningsBatted = inningsBatted;
	}

	public Long getTotalRunsScored() {
		return totalRunsScored;
	}

	public void setTotalRunsScored(Long totalRunsScored) {
		this.totalRunsScored = totalRunsScored;
	}

	public Long getTotalBallsPlayed() {
		return totalBallsPlayed;
	}

	public void setTotalBallsPlayed(Long totalBallsPlayed) {
		this.totalBallsPlayed = totalBallsPlayed;
	}

	public Integer getNotOuts() {
		return notOuts;
	}

	public void setNotOuts(Integer notOuts) {
		this.notOuts = notOuts;
	}

	public Double getBattingAverage() {
		return battingAverage;
	}

	public void setBattingAverage(Double battingAverage) {
		this.battingAverage = battingAverage;
	}

	public Integer getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(Integer highestScore) {
		this.highestScore = highestScore;
	}
	
	public Double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
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
