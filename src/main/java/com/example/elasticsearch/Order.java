package com.example.elasticsearch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

	private Long order_id;
	private Long orderitem_id ;
	private String product;
	private String category;
	private String user_name;
	private Boolean existing_user;

	public Order(@JsonProperty("order_id") Long order_id, @JsonProperty("orderitem_id") Long orderitem_id,
			@JsonProperty("product") String product, @JsonProperty("category") String category,
			@JsonProperty("user_name") String user_name, @JsonProperty("existing_user") Boolean existing_user) {

		this.order_id = order_id;
		this.orderitem_id = orderitem_id;
		this.product = product;
		this.category = category;
		this.user_name = user_name;
		this.existing_user = existing_user;

	}

	@Override
	public String toString() {
		return "OrderId:"+ order_id +" OrderItemId:"+orderitem_id+" Product:"+product+" Category:"+category+" UserName:"+user_name+" ExistingUser:"+existing_user;
	}

}
