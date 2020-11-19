package com.prs.web;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Request;
import com.prs.business.User;
import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {

	@Autowired
	private RequestRepo requestRepo;
	@Autowired
	private UserRepo userRepo;

	// GET ALL requests
	@GetMapping("/")
	public List<Request> getAll() {
		return requestRepo.findAll();
	}

	// GET a request by ID
	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id) {
		return requestRepo.findById(id);
	}

	// GET request review
	@GetMapping("/list-review/{id}")
	public Optional<Request> reviewRequest(@PathVariable int id) {
		User u = userRepo.findById(id).get();
		return requestRepo.findByUserNotAndStatus(u, "Review");

	}

	// Add a request
	@PostMapping("/")
	public Request addRequest(@RequestBody Request r) {
		r.setSubmittedDate(LocalDateTime.now());
		r.setStatus("New");
		r = requestRepo.save(r);
		return r;
	}

	// Update a request
	@PutMapping("/")
	public Request updateRequest(@RequestBody Request r) {
		r = requestRepo.save(r);
		return r;
	}

	// Delete a request
	@DeleteMapping("/{id}")
	public Request deleteRequest(@PathVariable int id) {

		// Optional type will wrap a request
		Optional<Request> r = requestRepo.findById(id);

		// Is Present will return true if request is found
		if (r.isPresent()) {
			requestRepo.deleteById(id);

		} else {
			System.out.println("Error - request not found for id " + id);
		}
		return r.get();
	}

	// request review
	@PutMapping("/submit-review")
	public Request submitForReview(@RequestBody Request r) {

		if (r.getTotal() <= 50.0) {
			r.setStatus("Approved");
		} else {
			r.setStatus("Review");
		}

		r.setSubmittedDate(LocalDateTime.now());

		return r;

	}

	// Reject a request
	@PutMapping("/reject")
	public Request rejectRequest(@RequestBody Request r) {
		r.setStatus("Rejected");
		r = requestRepo.save(r);
		return r;
	}

	// Approve a request
	@PutMapping("/approve")
	public Request approveRequest(@RequestBody Request r) {
		r.setStatus("Approved");
		r = requestRepo.save(r);
		return r;
	}
}
