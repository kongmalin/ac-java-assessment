package com.avenuecode.orders.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public Order saveOrder(Order order) {
		Order orderPersisted = orderRepository.save(order);
		System.out.println("Order added; Order ID: " + orderPersisted.getOrderId());
		return orderPersisted;
	}

	public Order findByOrderNumber(String orderNumber) {
		return orderRepository.findByOrderNumber(orderNumber);
	}

	public List<Order> listOrders() {
		return orderRepository.findAll();
	}

	public Order getOrder(Integer orderId) {
		return orderRepository.findOne(orderId);
	}

	public List<Order> findOrdersByStatus(String status) {
		return orderRepository.findOrdersByStatus(status);
	}

	public List<Order> findDiscountedOrders() {
		return orderRepository.findDiscountedOrders();
	}

	public List<Order> findOrdersWithMoreThanNumberOfProduct(int numberOfProduct) {
		return orderRepository.findOrdersWithMoreThanNumberOfProduct(numberOfProduct);
	}

}
