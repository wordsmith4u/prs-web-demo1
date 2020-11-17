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
@RequestMapping("/lineItems")

public class LineItemController {

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
	
	// Add a line item
	@PostMapping("/")
	public LineItem addlineItem(@RequestBody LineItem l) {
		l = lineItemRepo.save(l);
		return l;
	}

	// Update a line item
	@PutMapping("/")
	public LineItem updateLineItem(@RequestBody LineItem l) {
		l = lineItemRepo.save(l);
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

		} else {
			System.out.println("Error - line item not found for id " + id);
		}
		return l.get();
	}
	
	// Method will recalculate total and save it in the user instance  
		public void recalculateTotal(int requestID) {
			// get a list of line items 
			List<LineItem> lines = lineItemRepo.findByRequestId(requestID);
			// loop through list to get a sum of total
			double total = 0.0;
			for (LineItem line : lines) {
				Product p = line.getProduct();
				total += p.getPrice()*line.getQuantity();
			}
			
			// save that total in the instance of request
			Request r = requestRepo.findById(requestID).get();
			r.setTotal(total);
			requestRepo.save(r);
		}
	}