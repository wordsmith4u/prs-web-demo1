package com.prs.web;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.prs.business.Product;
import com.prs.db.ProductRepo;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductRepo productRepo;

	// GET ALL products
	@GetMapping("/")
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	// GET a product by ID
	@GetMapping("/{id}")
	public Optional<Product> getById(@PathVariable int id) {
		return productRepo.findById(id);
	}

	// Add a product
	@PostMapping("/")
	public Product addproduct(@RequestBody Product p) {
		p = productRepo.save(p);
		return p;
	}

	// Update a product
	@PutMapping("/")
	public Product updateProduct(@RequestBody Product p) {
		p = productRepo.save(p);
		return p;
	}

	// Delete a product
	@DeleteMapping("/{id}")
	public Product deleteProduct(@PathVariable int id) {

		// Optional type will wrap a user
		Optional<Product> p = productRepo.findById(id);

		// Is Present will return true if user is found
		if (p.isPresent()) {
			productRepo.deleteById(id);

		} else {
			System.out.println("Error - product not found for id " + id);
		}
		return p.get();
	}
}
