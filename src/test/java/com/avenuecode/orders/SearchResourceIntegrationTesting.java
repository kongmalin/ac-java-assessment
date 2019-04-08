package com.avenuecode.orders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.avenuecode.orders.OrdersApplication;
import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = OrdersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
public class SearchResourceIntegrationTesting {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Test
	public void getShippedOrdersTest() throws Exception {
		Order or1 = new Order("RTL_1021", new BigDecimal("32.55"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("42.55"), "SHIPPED");
		orderService.saveOrder(or1);

		mvc.perform(get("/search/get-orders?status=SHIPPED").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	public void getDiscountedOrdersTest() throws Exception {
		Order or1 = new Order("RTL_1022", new BigDecimal("52.55"), new BigDecimal("10"), new BigDecimal("62.55"),
				new BigDecimal("10"), new BigDecimal("62.55"), "FULFILLED");
		orderService.saveOrder(or1);

		mvc.perform(get("/search/get-discounted-orders").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	public void getOrdersMoreThanNumberOfProductTest() throws Exception {
		Order or1 = new Order("RTL_1035", new BigDecimal("32.55"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("42.55"), "FULFILLED");
		Product p1 = new Product("2233", "12342", "Diva Jeansdfs", new BigDecimal("34.99"));
		Product p2 = new Product("2345", "12345", "Polo Shisdfrt", new BigDecimal("55.99"));
		Product p3 = new Product("3424", "34223", "asdfasd", new BigDecimal("12.50"));
		List<Product> productList = new ArrayList<>(Arrays.asList(p1, p2, p3));
		or1.setProducts(productList);
		orderService.saveOrder(or1);

		mvc.perform(get("/search/get-orders-with-more-than-numberofproduct?numberOfProduct=2")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void getProductsTest() throws Exception {
		Product p1 = new Product("2233344444", "1234232412341", "Diva Jeansdfs", new BigDecimal("34.99"));
		Product p2 = new Product("2345555555", "2324123354334", "Polo Shisdfrt", new BigDecimal("55.99"));
		Product p3 = new Product("3424232323", "3422342313443", "asdfasd", new BigDecimal("12.50"));
		productService.saveProduct(p1);
		productService.saveProduct(p2);
		productService.saveProduct(p3);

		mvc.perform(get("/search/get-product-with-price-more-than?price=30").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(5)));
	}

}
