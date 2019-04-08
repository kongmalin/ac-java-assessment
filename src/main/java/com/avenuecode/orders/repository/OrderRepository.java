package com.avenuecode.orders.repository;

import com.avenuecode.orders.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Serializable> {

	public Order save(Order order);

	public Order findByOrderNumber(String orderNumber);

	public List<Order> findOrdersByStatus(String status);

	@Query("SELECT order FROM Order order WHERE order.discount is not null")
	public List<Order> findDiscountedOrders();

	@Query(nativeQuery = true, value = "SELECT o.* FROM orders o inner join order_product op on o.order_id = op.order_id inner join products p on p.product_id = op.product_id GROUP BY o.order_id HAVING COUNT(o.order_id) > :numberOfProduct")
	public List<Order> findOrdersWithMoreThanNumberOfProduct(@Param("numberOfProduct") int numberOfProduct);

}
