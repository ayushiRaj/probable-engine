package com.scc.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

import com.scc.model.PlayerStatPerMatch;

public class StatsUtilTest {

	@Test
	public void testIfTotalRunsCalculationIsProper() {
		PlayerStatPerMatch record1 = createNewMatchRecord(true, true, false, 1, 1, 1, 1, 1);
		PlayerStatPerMatch record2 = createNewMatchRecord(false, true, false, null, null, 1, 1, 1);
		PlayerStatPerMatch record3 = createNewMatchRecord(true, true, false, 10, 1, 1, 1, 1);
		PlayerStatPerMatch record4 = createNewMatchRecord(true, true, false, 12, 1, 1, 1, 1);
		List<PlayerStatPerMatch> records = new ArrayList<>();
		records.add(record1);
		records.add(record2);
		records.add(record3);
		records.add(record4);
		Long totalRunsScored = StatsUtil.calculateTotalNumberOfRunsScored(records);
		assertThat(totalRunsScored, is(equalTo(23L)));
	}

	@Test
	public void checkIfNotOutAreCalProperly() {
		PlayerStatPerMatch record1 = createNewMatchRecord(true, true, false, 1, 1, 1, 1, 1);
		PlayerStatPerMatch record2 = createNewMatchRecord(false, true, true, null, null, 1, 1, 1);
		PlayerStatPerMatch record3 = createNewMatchRecord(true, true, true, 10, 1, 1, 1, 1);
		PlayerStatPerMatch record4 = createNewMatchRecord(true, true, true, 12, 1, 1, 1, 1);
		List<PlayerStatPerMatch> records = new ArrayList<>();
		records.add(record1);
		records.add(record2);
		records.add(record3);
		records.add(record4);
		Long notOuts = StatsUtil.calculateTotalNumberOfNotOuts(records);
		assertThat(notOuts, is(equalTo(2L)));
	}

	private PlayerStatPerMatch createNewMatchRecord(boolean hasBatted, boolean hasBowled, boolean notout,
			Integer runsScored, Integer ballsPlayed, Integer wickets, Integer overs, Integer runsGiven) {
		PlayerStatPerMatch record = new PlayerStatPerMatch();
		record.setBallsPlayed(Objects.nonNull(ballsPlayed) ? ballsPlayed + 0L : null);
		record.setHasBatted(hasBatted);
		record.setHasBowled(hasBowled);
		record.setNotOut(notout);
		record.setOversBowled(Objects.nonNull(overs) ? overs + 0L : null);
		record.setRunsGiven(Objects.nonNull(runsGiven) ? runsGiven + 0L : 0);
		record.setRunsScored(Objects.nonNull(runsScored) ? runsScored : null);
		record.setWicketsTaken(Objects.nonNull(wickets) ? wickets + 0L : 0);
		return record;
	}
}
