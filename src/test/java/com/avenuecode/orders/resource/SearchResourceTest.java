package com.avenuecode.orders.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.service.ProductService;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchResource.class)
public class SearchResourceTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private OrderService orderService;
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void getShippedOrdersTest() throws Exception {
		Order or1 = new Order("RTL_1012", new BigDecimal("11.55"), new BigDecimal("11"), new BigDecimal("21.55"),
				new BigDecimal("11"), new BigDecimal("21.55"), "FULFILLED");
		List<Order> orderList = Arrays.asList(or1);
	    given(orderService.findOrdersByStatus("FULFILLED")).willReturn(orderList);
	 
	    mvc.perform(get("/search/get-orders?status=FULFILLED")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$[0].orderNumber", is(or1.getOrderNumber())));
	}
	
	@Test
	public void getDiscountedOrdersTest() throws Exception {
		Order or1 = new Order("RTL_1013", new BigDecimal("5"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("42.55"), "FULFILLED");
		List<Order> orderList = Arrays.asList(or1);
	    given(orderService.findDiscountedOrders()).willReturn(orderList);
	 
	    mvc.perform(get("/search/get-discounted-orders")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$[0].discount").value("5"));
	}
	
	@Test
	public void getOrdersMoreThanNumberOfProductTest() throws Exception {
		Order or1 = new Order("RTL_1015", new BigDecimal("32.55"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("42.55"), "FULFILLED");
		Order or2 = new Order("RTL_1016", new BigDecimal("334.55"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("334.55"), "SHIPPED");
		Order or3 = new Order("RTL_1017", new BigDecimal("51.55"), new BigDecimal("10"), new BigDecimal("42.55"),
				new BigDecimal("10"), new BigDecimal("51.55"), "SHIPPED");
		List<Order> orderList = Arrays.asList(or1, or2, or3);
	    given(orderService.findOrdersWithMoreThanNumberOfProduct(2)).willReturn(orderList);
	 
	    mvc.perform(get("/search/get-orders-with-more-than-numberofproduct?numberOfProduct=2")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void getProductsTest() throws Exception {
		Product p1 = new Product("1234", "1234", "Diva Jeansdfs", new BigDecimal("34.99"));
		Product p2 = new Product("324", "3444", "Diva Jeans", new BigDecimal("29.99"));
		List<Product> productList = Arrays.asList(p1, p2);
	    given(productService.findProductsByPrice(new BigDecimal(30))).willReturn(productList);
	 
	    mvc.perform(get("/search/get-product-with-price-more-than?price=30")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(2)));
	}

}
