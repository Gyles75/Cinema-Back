package org.cinema.security.repository;

import org.cinema.security.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUsername(String username);
}
