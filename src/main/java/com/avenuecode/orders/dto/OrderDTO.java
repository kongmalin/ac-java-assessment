package com.avenuecode.orders.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	@NotNull(message = "Order number is required.")
	private String orderNumber;

	@Valid
	private BigDecimal discount;

	@Valid
	@NotNull(message = "Tax is required.")
	private BigDecimal taxPercent;

	@Valid
	@NotNull(message = "Status is required.")
	private String status;

	@Valid
	@NotNull(message = "Products are required.")
	@JsonProperty("products")
	private List<ProductDTO> productList;

	public OrderDTO() {
		super();
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

}
