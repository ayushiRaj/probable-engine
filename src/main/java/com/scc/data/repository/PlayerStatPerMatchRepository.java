package com.scc.data.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.scc.model.PlayerStatPerMatch;

@RepositoryRestResource(collectionResourceRel = "perMatchStat", path = "perMatchStat")
public interface PlayerStatPerMatchRepository extends JpaRepository<PlayerStatPerMatch, Long> {

	List<PlayerStatPerMatch> findByPlayerIdOrderByIdDesc(@Param("playerId") Long playerId, Pageable pageable);

	List<PlayerStatPerMatch> findByPlayerId(@Param("playerId") Long playerId, Pageable pageable);

}
