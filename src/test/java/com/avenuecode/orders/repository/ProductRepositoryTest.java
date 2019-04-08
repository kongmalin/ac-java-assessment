package com.avenuecode.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
	
	// @Autowired
	// private TestEntityManager entityManager;

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void saveTest() {
		
		Product p1 = new Product("1234222222", "1234444444444", "Diva Jeansdfs", new BigDecimal("34.99"));

		productRepository.saveAndFlush(p1);

		Product p3 = productRepository.findOne(8);
		assertThat(p3.getPrice()).isEqualTo(p1.getPrice());
	}
	
	@Test
	public void findProductsByPriceTest() {
		List<Product> productList = productRepository.findProductsByPrice(new BigDecimal(30));
		assertThat(productList.size()).isEqualTo(3);
	}

}
