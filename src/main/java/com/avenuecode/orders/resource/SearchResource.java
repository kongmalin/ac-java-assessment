package com.avenuecode.orders.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.service.ProductService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/search")
public class SearchResource {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> listOrders() {
		return ok(orderService.listOrders());
	}

	@GetMapping(value = "/get-orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> getShippedOrders(@RequestParam("status") String status) {
		List<Order> orderList = orderService.findOrdersByStatus(status);
		if (orderList == null || orderList.size() == 0) {
			return notFound().build();
		}
		return ok(orderList);
	}

	@GetMapping(value = "/get-discounted-orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> getDiscountedOrders() {
		List<Order> orderList = orderService.findDiscountedOrders();
		if (orderList == null || orderList.size() == 0) {
			return notFound().build();
		}
		return ok(orderList);
	}

	@GetMapping(value = "/get-orders-with-more-than-numberofproduct", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> getOrdersMoreThanNumberOfProduct(
			@RequestParam("numberOfProduct") int numberOfProduct) {
		List<Order> orderList = orderService.findOrdersWithMoreThanNumberOfProduct(numberOfProduct);
		if (orderList == null || orderList.size() == 0) {
			return notFound().build();
		}
		return ok(orderList);
	}

	@GetMapping(value = "/get-product-with-price-more-than", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Product>> getProducts(@RequestParam("price") BigDecimal price) {
		List<Product> productList = productService.findProductsByPrice(price);
		if (productList == null || productList.size() == 0) {
			return notFound().build();
		}
		return ok(productList);
	}

}
