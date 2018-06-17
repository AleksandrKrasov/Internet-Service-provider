package ua.khpi.krasov.db;

import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

public enum Status {
	
	OPENED, CONFIRMED, BLOCKED, PAID, UNPAID;
	
	public static Status getStatus(User user) {
		int statusId = user.getStatus_id();
		return Status.values()[statusId];
	}
	
	public int getStatusId() {
		return this.ordinal();
	}
	
	public static Status getStatus(Order order) {
		int statusId = order.getStatusId();
		return Status.values()[statusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
