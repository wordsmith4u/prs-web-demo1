package com.prs.db;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.business.Request;
import com.prs.business.User;

public interface RequestRepo extends JpaRepository<Request, Integer> {
	
	Optional<Request> findByUserNotAndStatus(User user, String status);

}
