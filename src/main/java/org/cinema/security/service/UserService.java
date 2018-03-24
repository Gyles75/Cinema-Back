package org.cinema.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.cinema.exceptions.AlreadyExistsException;
import org.cinema.security.domain.User;
import org.cinema.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional(readOnly = true)
	public List<User> getAll() {
		return this.userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	@Transactional
	public User save(User user) {
		if (this.userRepository.exists(user.getUsername())) {
			throw new AlreadyExistsException("User already exists exception ..");
		}
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}
	
	@Transactional
	public List<User> save(List<User> users) {
		users = users.stream()
			.map(user -> new User(user.getUsername(), this.passwordEncoder.encode(user.getPassword()), user.getIsEnabled(), user.getRoles()))
			.collect(Collectors.toList())
		;
		
		return this.userRepository.save(users);
	}
	
	@Transactional
	public User update(User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public Boolean delete(String username) {
		this.userRepository.delete(username);
		return Boolean.TRUE;
	}
}
