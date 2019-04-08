package com.avenuecode.orders.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.dto.OrderDTO;
import com.avenuecode.orders.dto.ProductDTO;
import com.avenuecode.orders.dto.RespObject;
import com.avenuecode.orders.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> listOrders() {
		return ok(orderService.listOrders());
	}

	@GetMapping(value = "/{orderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Order> getOrder(@PathVariable Integer orderId) {
		Order order = orderService.getOrder(orderId);
		if (order == null) {
			return notFound().build();
		}
		return ok(order);
	}

	@PostMapping(value = "/addOrder", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RespObject> addOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult result) {
		RespObject respObject = new RespObject();
		if (result.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			for (Object object : result.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					errorMessage.append(fieldError.getDefaultMessage());
				}
			}
			respObject.setMessage(errorMessage.toString());
			return new ResponseEntity<RespObject>(respObject, HttpStatus.BAD_REQUEST);
		}
		orderService.saveOrder(generateOrder(orderDTO));
		respObject.setMessage("Order added successfully");
		return new ResponseEntity<RespObject>(respObject, HttpStatus.OK);
	}

	private Order generateOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderNumber(orderDTO.getOrderNumber());
		order.setDiscount(orderDTO.getDiscount());
		order.setTaxPercent(orderDTO.getTaxPercent());
		order.setStatus(orderDTO.getStatus());
		List<Product> productList = new ArrayList<>();
		for (ProductDTO productDTO : orderDTO.getProductList()) {
			Product product = new Product(productDTO.getUpc(), productDTO.getSku(), productDTO.getDescription(),
					productDTO.getPrice());
			List<Order> orderList = new ArrayList<>();
			orderList.add(order);
			product.setOrders(orderList);
			productList.add(product);
		}
		order.setProducts(productList);
		return order;
	}

}
