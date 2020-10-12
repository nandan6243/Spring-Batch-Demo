package com.app.batch.model;

public class UberEats {

	private Integer orderId;
	private String dishName;
	private String quantity;
	private String urlInvoked;
	private Boolean isUpdated;
	private String partnerName;

	public UberEats() {
	}

	public UberEats(Integer orderId, String partnerName, String dishName, String quantity, String urlInvoked, Boolean isUpdated) {
		this.orderId = orderId;
		this.partnerName = partnerName;
		this.dishName = dishName;
		this.quantity = quantity;
		this.urlInvoked = urlInvoked;
		this.isUpdated = isUpdated;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUrlInvoked() {
		return urlInvoked;
	}

	public void setUrlInvoked(String urlInvoked) {
		this.urlInvoked = urlInvoked;
	}

	public Boolean getUpdated() {
		return isUpdated;
	}

	public void setUpdated(Boolean updated) {
		isUpdated = updated;
	}
	
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
	public String getPartnerName() {
		return partnerName;
	}
}
