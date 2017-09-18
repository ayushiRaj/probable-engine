package com.scc.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

	@Override
	public String toString() {
		return "PlayerStatPerMatch [id=" + id + ", player=" + player + ", runsScored=" + runsScored + ", ballsPlayed="
				+ ballsPlayed + ", hasBatted=" + hasBatted + ", notOut=" + notOut + ", hasBowled=" + hasBowled
				+ ", oversBowled=" + oversBowled + ", runsGiven=" + runsGiven + ", wicketsTaken=" + wicketsTaken + "]";
	}
	
	

}
