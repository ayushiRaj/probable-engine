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

	private Long runsScored;
	private Long BallsPlayed;
	private boolean notOut;
	private Long oversBowled;
	private Long runsGiven;

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

	public Long getRunsScored() {
		return runsScored;
	}

	public void setRunsScored(Long runsScored) {
		this.runsScored = runsScored;
	}

	public Long getBallsPlayed() {
		return BallsPlayed;
	}

	public void setBallsPlayed(Long ballsPlayed) {
		BallsPlayed = ballsPlayed;
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

}
