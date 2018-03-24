package org.cinema.actor.repository;

import java.util.List;

import org.cinema.actor.domain.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorRepository extends MongoRepository<Actor, Integer> {
	
	public List<Actor> findByNom(String nom);
	
	public List<Actor> findByPrenom(String prenom);
}
