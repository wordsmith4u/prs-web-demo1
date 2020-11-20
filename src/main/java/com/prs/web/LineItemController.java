package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.business.Product;
import com.prs.db.LineItemRepo;
import com.prs.business.Request;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/line-items")

public class LineItemController {

	/*
	 * A controller will implement 5 CRUD methods: 1) GET ALL 2) GET BY ID 3) POST -
	 * Insert / Create 4) PUT - Update 5) DELETE - Delete
	 */

	@Autowired
	private LineItemRepo lineItemRepo;
	@Autowired
	private RequestRepo requestRepo;

	// GET ALL line items
	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	// GET one line item by ID
	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		return lineItemRepo.findById(id);
	}

	// get line items for a PR
	@GetMapping("/lines-for-pr/{prId}")
	public List<LineItem> getAllLineItems(@PathVariable int prId) {
		return lineItemRepo.findByRequestId(prId);
	}

	// Add a line item
	@PostMapping("/")
	public LineItem addlineItem(@RequestBody LineItem l) {
		l = lineItemRepo.save(l);
		recalculateLineItemValue(l);
		return l;
	}

	private void recalculateLineItemValue(LineItem l) {
		// get all line items for this user
		// loop through them and sum a new total
		double newTotal = 0.0;
		List<LineItem> li = lineItemRepo.findByRequestId(l.getRequest().getId());
		for (LineItem line : li) {
			Product p = line.getProduct();
			newTotal += p.getPrice()*line.getQuantity();
		}
		
		Request r = l.getRequest();
		r.setTotal(newTotal);
		requestRepo.save(r);
	}

	// Update a line item
	@PutMapping("/")
	public LineItem updateLineItem(@RequestBody LineItem l) {
		l = lineItemRepo.save(l);
		recalculateLineItemValue(l);
		return l;
	}

	// Delete a line item
	@DeleteMapping("/{id}")
	public LineItem deleteLineItem(@PathVariable int id) {

		// Optional type will wrap a line item
		Optional<LineItem> l = lineItemRepo.findById(id);

		// Is Present will return true if line item is found
		if (l.isPresent()) {
			lineItemRepo.deleteById(id);
			recalculateLineItemValue(l.get());

		} else {
			System.out.println("Error - line item not found for id " + id);
		}
		return l.get();
	}

}