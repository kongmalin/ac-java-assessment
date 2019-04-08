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
import com.avenuecode.orders.dto.ProductDTO;
import com.avenuecode.orders.dto.RespObject;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Product>> listProducts() {
		return ok(productService.listProducts());
	}

	@GetMapping(value = "/{productId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
		Product product = productService.getProduct(productId);
		if (product == null) {
			return notFound().build();
		}
		return ok(product);
	}

	@PostMapping(value = "/addProduct", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RespObject> addProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult result) {
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
		Product product = new Product();
		product.setUpc(productDTO.getUpc());
		product.setSku(productDTO.getSku());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		productService.saveProduct(product);
		respObject.setMessage("Product added successfully");
		return new ResponseEntity<RespObject>(respObject, HttpStatus.OK);
	}

	@PostMapping(value = "/addProductToOrder", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RespObject> addProductToOrder(@Validated @RequestBody ProductDTO productDTO,
			BindingResult result) {
		RespObject respObject = new RespObject();
		Order order = orderService.findByOrderNumber(productDTO.getOrderNumber());
		Product product = new Product();
		product.setUpc(productDTO.getUpc());
		product.setSku(productDTO.getSku());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		order.getProducts().add(product);
		product.setOrders(new ArrayList<Order>() {
			{
				add(order);
			}
		});
		productService.saveProduct(product);
		respObject.setMessage("Product added successfully");
		return new ResponseEntity<RespObject>(respObject, HttpStatus.OK);
	}

}
