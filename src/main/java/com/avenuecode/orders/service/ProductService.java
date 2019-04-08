package com.avenuecode.orders.service;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product saveProduct(Product product) {
		Product productPersisted = productRepository.save(product);
		System.out.println("Product added; Product ID: " + productPersisted.getProductId());
		return productPersisted;
	}

	public Product findByUpc(String upc) {
		return productRepository.findByUpc(upc);
	}

	public Product getProduct(Integer productId) {
		return productRepository.findOne(productId);
	}

	public List<Product> listProducts() {
		return productRepository.findAll();
	}

	public List<Product> findProductsByPrice(BigDecimal price) {
		return productRepository.findProductsByPrice(price);
	}

}
