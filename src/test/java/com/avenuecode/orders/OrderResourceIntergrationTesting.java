package com.avenuecode.orders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.avenuecode.orders.dto.OrderDTO;
import com.avenuecode.orders.dto.ProductDTO;
import com.avenuecode.orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = OrdersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
public class OrderResourceIntergrationTesting {

	@Autowired
	private MockMvc mvc;

	@Test
	public void listOrdersTest() throws Exception {
		mvc.perform(get("/orders")).andExpect(status().isOk());
	}

	@Test
	public void getOrderTest() throws Exception {
		mvc.perform(get("/orders/{orderId}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderNumber").value("RTL_1001"));
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

		MvcResult mvcResult = mvc.perform(post("/orders/addOrder").content(asJsonString(orderDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		Assert.assertEquals("{\"message\":\"Order added successfully\"}", mvcResult.getResponse().getContentAsString());
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
