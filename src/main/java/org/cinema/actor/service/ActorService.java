package org.cinema.actor.service;

import java.util.List;

import org.cinema.actor.domain.Actor;
import org.cinema.actor.repository.ActorRepository;
import org.cinema.exceptions.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActorService {
	
	@Autowired
	private ActorRepository actorRepository;
	
	
	@Transactional(readOnly = true)
	public List<Actor> getAll() {
		return this.actorRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Actor getById(Integer id) {
		return this.actorRepository.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Actor> getByNom(String nom) {
		return this.actorRepository.findByNom(nom);
	}
	
	@Transactional(readOnly = true)
	public List<Actor> getByPrenom(String prenom) {
		return this.actorRepository.findByPrenom(prenom);
	}
	
	@Transactional
	public Actor save(Actor actor) {
		if (this.actorRepository.exists(actor.getId())) {
			throw new AlreadyExistsException("Actor already exists exception ..");
		}
		
		return this.actorRepository.save(actor);
	}
	
	@Transactional
	public List<Actor> save(List<Actor> actors) {
		return this.actorRepository.save(actors);
	}
	
	@Transactional
	public Actor update(Actor actor) {
		return this.actorRepository.save(actor);
	}
	
	@Transactional
	public Boolean delete(Integer id) {
		this.actorRepository.delete(id);
		return Boolean.TRUE;
	}
}
