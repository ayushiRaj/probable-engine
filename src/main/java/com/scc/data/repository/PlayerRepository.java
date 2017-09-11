package com.scc.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.scc.model.Player;

@RepositoryRestResource(collectionResourceRel="players" , path= "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

}
