package com.avenuecode.orders.repository;

import com.avenuecode.orders.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable> {

	public Product save(Product product);
	
	public Product findByUpc(String upc);

	@Query("SELECT product FROM Product product WHERE product.price > :price")
	public List<Product> findProductsByPrice(@Param("price") BigDecimal price);

}
