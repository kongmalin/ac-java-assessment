package com.avenuecode.orders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;

import com.avenuecode.orders.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = OrdersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
public class ProductResourceIntegrationTesting {

	@Autowired
	private MockMvc mvc;

	@Test
	public void listProductsTest() throws Exception {
		mvc.perform(get("/products")).andExpect(status().isOk());
	}

	@Test
	public void getOrderTest() throws Exception {
		mvc.perform(get("/products/{productId}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.upc").value("1257833283"));
	}

	@Test
	public void addProductTest() throws Exception {
		ProductDTO productDTO01 = new ProductDTO();
		productDTO01.setUpc("TestUPC03");
		productDTO01.setSku("TestSKU03");
		productDTO01.setDescription("This the test data 03");
		productDTO01.setPrice(new BigDecimal(75));

		MvcResult mvcResult = mvc.perform(post("/products/addProduct").content(asJsonString(productDTO01))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		Assert.assertEquals("{\"message\":\"Product added successfully\"}",
				mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void addProductToOrder() throws Exception {
		ProductDTO productDTO04 = new ProductDTO();
		productDTO04.setUpc("TestUPC04");
		productDTO04.setSku("TestSKU04");
		productDTO04.setDescription("This the test data 04");
		productDTO04.setPrice(new BigDecimal(75));
		productDTO04.setOrderNumber("RTL_1001");

		MvcResult mvcResult = mvc.perform(post("/products/addProduct").content(asJsonString(productDTO04))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		Assert.assertEquals("{\"message\":\"Product added successfully\"}",
				mvcResult.getResponse().getContentAsString());
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
