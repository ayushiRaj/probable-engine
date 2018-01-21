package com.scc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class PlayerStatPerMatch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Player player;

	private Integer runsScored;
	private Long ballsPlayed;
	private boolean hasBatted;
	private boolean notOut;
	private boolean hasBowled;
	private Long oversBowled;
	private Long runsGiven;
	private Long wicketsTaken;
	private Integer sixes;
	private Integer fours;
	private Integer wides;
	private Integer noBalls;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Enumerated(EnumType.STRING)
	private MatchType matchType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getRunsScored() {
		return runsScored;
	}

	public void setRunsScored(Integer runsScored) {
		this.runsScored = runsScored;
	}

	public Long getBallsPlayed() {
		return ballsPlayed;
	}

	public void setBallsPlayed(Long ballsPlayed) {
		this.ballsPlayed = ballsPlayed;
	}

	public boolean isNotOut() {
		return notOut;
	}

	public void setNotOut(boolean notOut) {
		this.notOut = notOut;
	}

	public Long getOversBowled() {
		return oversBowled;
	}

	public void setOversBowled(Long oversBowled) {
		this.oversBowled = oversBowled;
	}

	public Long getRunsGiven() {
		return runsGiven;
	}

	public void setRunsGiven(Long runsGiven) {
		this.runsGiven = runsGiven;
	}

	public boolean hasBatted() {
		return hasBatted;
	}

	public void setHasBatted(boolean hasBatted) {
		this.hasBatted = hasBatted;
	}

	public boolean hasBowled() {
		return hasBowled;
	}

	public void setHasBowled(boolean hasBowled) {
		this.hasBowled = hasBowled;
	}

	public Long getWicketsTaken() {
		return wicketsTaken;
	}

	public void setWicketsTaken(Long wicketsTaken) {
		this.wicketsTaken = wicketsTaken;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public Integer getSixes() {
		return sixes;
	}

	public void setSixes(Integer sixes) {
		this.sixes = sixes;
	}

	public Integer getFours() {
		return fours;
	}

	public void setFours(Integer fours) {
		this.fours = fours;
	}

	public Integer getWides() {
		return wides;
	}

	public void setWides(Integer wides) {
		this.wides = wides;
	}

	public Integer getNoBalls() {
		return noBalls;
	}

	public void setNoBalls(Integer noBalls) {
		this.noBalls = noBalls;
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
