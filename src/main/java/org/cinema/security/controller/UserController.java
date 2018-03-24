package org.cinema.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cinema.security.domain.Role;
import org.cinema.security.domain.User;
import org.cinema.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	
	@GetMapping("/sessions")
	public ResponseEntity<?> sessions() {
		final List<SessionInformation> sessions = new ArrayList<>();
		
		for (Object o: this.sessionRegistry.getAllPrincipals()) {
			for (SessionInformation s: this.sessionRegistry.getAllSessions(o, true)) {
				sessions.add(s);
			}
		}
		
		return ResponseEntity.ok(sessions);
	}
	
	@GetMapping("/successful")
	public ResponseEntity<?> successful() {
		return ResponseEntity.ok(this.sessionRegistry.getAllPrincipals());
		//return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
	}
	
	@GetMapping("/failure")
	public ResponseEntity<?> failure() {
		return ResponseEntity.ok("Bad credentials ..");
	}
	
	@GetMapping
	public ResponseEntity<List<User>> searchAll() {
		return ResponseEntity.ok(this.userService.getAll());
	}
	
	@GetMapping("/init")
	public ResponseEntity<List<User>> init() {
		return ResponseEntity.ok(
			this.userService.save(
				Arrays.asList(
					new User("user", "user", true, Arrays.asList(new Role(1, "ROLE_USER"))),
					new User("admin", "admin", true, Arrays.asList(new Role(1, "ROLE_USER"), new Role(2, "ROLE_ADMIN"))),
					new User("ghiles", "ghiles", true, Arrays.asList(new Role(1, "ROLE_USER"), new Role(2, "ROLE_ADMIN"), new Role(3, "ROLE_MANAGE")))
				)
			)
		);
	}
}
