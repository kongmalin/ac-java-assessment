package com.avenuecode.orders.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.repository.OrderRepository;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

	@TestConfiguration
	static class OrderServiceTestContextConfiguration {
		@Bean
		public OrderService orderService() {
			return new OrderService();
		}
	}

	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderRepository orderRepository;

	@Before
	public void init() {
		Order or1 = new Order("RTL_1012", new BigDecimal("11.55"), new BigDecimal("11"), new BigDecimal("21.55"),
				new BigDecimal("11"), new BigDecimal("21.55"), "FULFILLED");
		Mockito.when(orderRepository.findOrdersByStatus("FULFILLED")).thenReturn(new ArrayList<>(Arrays.asList(or1)));
	}

	@Test
	public void findOrdersByStatusTest() {
		List<Order> found = orderService.findOrdersByStatus("FULFILLED");
		assertThat(found.get(0).getOrderNumber()).isEqualTo("RTL_1012");
	}

}
