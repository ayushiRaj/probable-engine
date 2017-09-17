package com.scc.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scc.model.PlayerStatPerMatch;

public interface PlayerStatPerMatchRepository
		extends PagingAndSortingRepository<PlayerStatPerMatch, Long>, JpaRepository<PlayerStatPerMatch, Long> {

}
