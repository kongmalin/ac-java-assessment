package com.avenuecode.orders.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

	@TestConfiguration
	static class ProductServiceTestContextConfiguration {
		@Bean
		public ProductService productService() {
			return new ProductService();
		}
	}

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Before
	public void init() {
		Product p1 = new Product("1234", "1234", "Diva Jeansdfs", new BigDecimal("34.99"));
		Mockito.when(productRepository.findOne(8)).thenReturn(p1);
	}

	@Test
	public void getProductTest() {
		Product p2 = productService.getProduct(8);
		assertThat(p2.getUpc()).isEqualTo("1234");
	}

}
