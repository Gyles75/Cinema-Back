package org.cinema.actor.controller;

import java.util.Arrays;
import java.util.List;

import org.cinema.actor.domain.Actor;
import org.cinema.actor.service.ActorService;
import org.cinema.annotations.ActorBuilder;
import org.cinema.annotations.AdminRole;
import org.cinema.annotations.ManagerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/actor")
public class ActorController {
	
	@Autowired
	private ActorService actorService;
	
	@ActorBuilder(id=1, nom="Mahrane", prenom="Ghiles")
	private Actor actor;
	
	
	@AdminRole
	@GetMapping("/add/{one}/{two}")
	public ResponseEntity<Integer> add(@PathVariable Integer one, @PathVariable Integer two) {
		return ResponseEntity.ok(one + two);
	}
	
	@AdminRole
	@GetMapping
	public ResponseEntity<List<Actor>> searchAll() {
		return ResponseEntity.ok(this.actorService.getAll());
	}
	
	@ManagerRole
	@GetMapping("/searchById/{id}")
	public ResponseEntity<Actor> searchById(@PathVariable Integer id) {
		System.out.println(this.actor.getNom());
		return ResponseEntity.ok(this.actorService.getById(id));
	}
	
	@GetMapping("/searchByNom/{nom}")
	public ResponseEntity<List<Actor>> searchByNom(@PathVariable String nom) {
		return ResponseEntity.ok(this.actorService.getByNom(nom));
	}
	
	@GetMapping("/searchByPrenom/{prenom}")
	public ResponseEntity<List<Actor>> searchByPrenom(@PathVariable String prenom) {
		return ResponseEntity.ok(this.actorService.getByPrenom(prenom));
	}
	
	@GetMapping("/init")
	public ResponseEntity<List<Actor>> init() {
		return ResponseEntity.ok(
			this.actorService.save(
				Arrays.asList(new Actor(1, "Mahrane", "Ghiles"), new Actor(2, "Boumaza", "Adel"), new Actor(3, "Dali", "Karim"))
			)
		);
	}
}
