package com.docker.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docker.demo.model.Product;
import com.docker.demo.service.ProductService;
import com.docker.demo.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class ProductController {
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;

	// -------------------Retrieve All Products.---------------------------------------------
	// Test comment to trigger a buidl

	@RequestMapping(value = "/products/", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> listAllProducts() {
		List<Product> products = productService.findAllProducts();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	// -------------------Retrieve Single Product By Id------------------------------------------

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("productId") long productId) {
		logger.info("Fetching Product with id {}", productId);
		Product product = productService.findById(productId);
		if (product == null) {
			logger.error("Product with id {} not found.", productId);
			return new ResponseEntity(new CustomErrorType("Product with id " + productId 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
}
