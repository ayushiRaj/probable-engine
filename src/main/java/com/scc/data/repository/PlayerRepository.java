package com.scc.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.scc.model.Player;

@RepositoryRestResource(collectionResourceRel="players" , path= "players")
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
