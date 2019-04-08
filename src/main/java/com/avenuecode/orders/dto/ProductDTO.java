package com.avenuecode.orders.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	@NotNull(message = "UPC is required.")
	private String upc;

	@Valid
	@NotNull(message = "SKU is required.")
	private String sku;

	@Valid
	@NotNull(message = "Product description is required.")
	private String description;

	@Valid
	@NotNull(message = "Product price is required.")
	private BigDecimal price;

	@Valid
	private String orderNumber;

	public ProductDTO() {
		super();
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

}
