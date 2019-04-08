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

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.domain.Order;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

	// @Autowired
	// private TestEntityManager entityManager;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void saveTest() {

		Product p1 = new Product("1234", "1234", "Diva Jeansdfs", new BigDecimal("34.99"));
		Product p2 = new Product("12345", "12345", "Polo Shisdfrt", new BigDecimal("25.99"));
		List<Product> productList = new ArrayList<>();
		productList.add(p1);
		productList.add(p2);

		Order or1 = new Order("RTL_1012", new BigDecimal("11.55"), new BigDecimal("11"),
				new BigDecimal("21.55"), new BigDecimal("11"), new BigDecimal("21.55"), "FULFILLED");

		or1.setProducts(productList);

		orderRepository.saveAndFlush(or1);

		Order or2 = orderRepository.findOne(6);
		assertThat(or2.getOrderNumber()).isEqualTo(or1.getOrderNumber());
	}
	
	@Test
	public void findOrdersByStatusTest() {
		List<Order> orderList = orderRepository.findOrdersByStatus("SHIPPED");
		assertThat(orderList.size()).isEqualTo(3);
	}
	
	@Test
	public void findDiscountedOrdersTest() {
		List<Order> orderList = orderRepository.findDiscountedOrders();
		assertThat(orderList.size()).isEqualTo(2);
	}
	
	@Test
	public void findOrdersWithMoreThanNumberOfProductTest() {
		List<Order> orderList = orderRepository.findOrdersWithMoreThanNumberOfProduct(2);
		assertThat(orderList.get(0).getOrderNumber()).isEqualTo("RTL_1003");
	}

}
