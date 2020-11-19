package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.User;
import com.prs.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	// GET ALL users
	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
	}

	// GET a user by ID
	@GetMapping("/{id}")
	public Optional<User> getById(@PathVariable int id) {
		return userRepo.findById(id);
	}

	// Add a user
	@PostMapping("/")
	public User adduser(@RequestBody User u) {
		u = userRepo.save(u);
		return u;
	}

	// Update a user
	@PutMapping("/")
	public User updateUser(@RequestBody User u) {
		u = userRepo.save(u);
		return u;
	}

	// Delete a user
	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable int id) {

		// Optional type will wrap a user
		Optional<User> u = userRepo.findById(id);

		// Is Present will return true if user is found
		if (u.isPresent()) {
			userRepo.deleteById(id);

		} else {
			System.out.println("Error - user not found for id " + id);
		}
		return u.get();
	}

	// login via GET with request params
	@GetMapping("/login")
	public Optional<User> login(@RequestParam String userName, @RequestParam String password) {
		Optional<User> u = userRepo.findByUserNameAndPassword(userName, password);
		if (u.isPresent()) {
			return u;
		} else {
			System.out.println("Error - user not found");
		}
		return u;
	}

	// login via POST
	@PostMapping("/login")
	public Optional<User> login(@RequestBody User u) {
		Optional<User> user = userRepo.findByUserNameAndPassword(u.getUserName(), u.getPassword());
		if (user.isPresent()) {
			return user;
		} else {
			System.out.println("Error - user not found");
		}
		return user;
	}
}
