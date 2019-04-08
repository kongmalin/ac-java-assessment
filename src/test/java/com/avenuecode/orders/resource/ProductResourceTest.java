package com.avenuecode.orders.resource;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.dto.ProductDTO;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private OrderService orderService;

	@MockBean
	private ProductService productService;

	@Test
	public void listProductsTest() throws Exception {
		mvc.perform(get("/products")).andExpect(status().isOk());
	}

	@Test
	public void getProductTest() throws Exception {
		Product product = new Product("TestUPC1234", "TestSKU1234", "Diva Jeansdfs", new BigDecimal("34.99"));
		given(productService.getProduct(1)).willReturn(product);

		mvc.perform(get("/products/{productId}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.upc").value("TestUPC1234"));
	}

	@Test
	public void addProductTest() throws Exception {
		ProductDTO productDTO01 = new ProductDTO();
		productDTO01.setUpc("TestUPC03");
		productDTO01.setSku("TestSKU03");
		productDTO01.setDescription("This the test data 03");
		productDTO01.setPrice(new BigDecimal(75));

		mvc.perform(post("/products/addProduct").content(asJsonString(productDTO01))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void addProductToOrder() throws Exception {
		ProductDTO productDTO04 = new ProductDTO();
		productDTO04.setUpc("TestUPC04");
		productDTO04.setSku("TestSKU04");
		productDTO04.setDescription("This the test data 04");
		productDTO04.setPrice(new BigDecimal(75));
		productDTO04.setOrderNumber("RTL_1001");

		mvc.perform(post("/products/addProduct").content(asJsonString(productDTO04))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
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
