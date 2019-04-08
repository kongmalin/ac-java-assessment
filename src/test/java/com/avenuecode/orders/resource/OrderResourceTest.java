package com.avenuecode.orders.resource;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.avenuecode.orders.dto.OrderDTO;
import com.avenuecode.orders.dto.ProductDTO;
import com.avenuecode.orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderResource.class)
public class OrderResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private OrderService orderService;

	@Test
	public void listOrdersTest() throws Exception {
		mvc.perform(get("/orders")).andExpect(status().isOk());
	}

	@Test
	public void getOrderTest() throws Exception {
		Order order = new Order("RTL_1033", new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("45"),
				new BigDecimal("10"), new BigDecimal("42"), "FULFILLED");
		given(orderService.getOrder(1)).willReturn(order);

		mvc.perform(get("/orders/{orderId}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderNumber").value("RTL_1033"));
	}

	@Test
	public void addOrderTest() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderNumber("Test101");
		orderDTO.setDiscount(new BigDecimal(0));
		orderDTO.setTaxPercent(new BigDecimal(8.9));
		orderDTO.setStatus("FULFILLED");
		ProductDTO productDTO01 = new ProductDTO();
		productDTO01.setUpc("TestUPC01");
		productDTO01.setSku("TestSKU01");
		productDTO01.setDescription("This the test data 01");
		productDTO01.setPrice(new BigDecimal(75));
		ProductDTO productDTO02 = new ProductDTO();
		productDTO02.setUpc("TestUPC02");
		productDTO02.setSku("TestSKU02");
		productDTO02.setDescription("This the test data 02");
		productDTO02.setPrice(new BigDecimal(100));
		List<ProductDTO> productList = new ArrayList<>();
		productList.add(productDTO01);
		productList.add(productDTO02);
		orderDTO.setProductList(productList);

		mvc.perform(post("/orders/addOrder").content(asJsonString(orderDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	private String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
