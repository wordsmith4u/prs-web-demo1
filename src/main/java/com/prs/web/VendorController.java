package com.prs.web;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.prs.business.Vendor;
import com.prs.db.VendorRepo;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {

	/*
	 * A controller will implement 5 CRUD methods: 1) GET ALL 2) GET BY ID 3) POST -
	 * Insert 4) PUT - Update 5) DELETE - Delete
	 */

	@Autowired
	private VendorRepo vendorRepo;

	// GET ALL vendors
	@GetMapping("/")
	public List<Vendor> getAll() {
		return vendorRepo.findAll();
	}

	// GET a vendor by ID
	@GetMapping("/{id}")
	public Optional<Vendor> getById(@PathVariable int id) {
		return vendorRepo.findById(id);
	}

	// Add a vendor
	@PostMapping("/")
	public Vendor addvendor(@RequestBody Vendor v) {
		v = vendorRepo.save(v);
		return v;
	}

	// Update a vendor
	@PutMapping("/")
	public Vendor updateVendor(@RequestBody Vendor v) {
		v = vendorRepo.save(v);
		return v;
	}

	// Delete a vendor
	@DeleteMapping("/{id}")
	public Vendor deleteVendor(@PathVariable int id) {

		// Optional type will wrap a vendor
		Optional<Vendor> v = vendorRepo.findById(id);

		// Is Present will return true if vendor is found
		if (v.isPresent()) {
			vendorRepo.deleteById(id);

		} else {
			System.out.println("Error - vendor not found for id " + id);
		}
		return v.get();
	}
}
