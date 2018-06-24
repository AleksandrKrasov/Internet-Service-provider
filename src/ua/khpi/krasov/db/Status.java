package ua.khpi.krasov.db;

import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;


/**
 * Status sets enumeration for statuses used in class User: opened
 * confirmed, blocked and for class Order: paid, unpaid.
 * 
 *  @author A.Krasov
 *  @version 1.0
 */
public enum Status {
	
	OPENED, CONFIRMED, BLOCKED, PAID, UNPAID;
	
	public static Status getStatus(User user) {
		int statusId = user.getStatusId();
		return Status.values()[statusId];
	}
	
	public static Status getStatus(Order order) {
		int statusId = order.getStatusId();
		return Status.values()[statusId];
	}
	
	public int getStatusId() {
		return this.ordinal();
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	
	/**
	 * Returns status name in Russian language.
	 * @return A String value.
	 */
	public String getNameRu() {
		if (name().equals(OPENED.name())) {
			return "Неподтвержден";
		} 
		if (name().equals(CONFIRMED.name())) {
			return "Подтвержден";
		} 
		if (name().equals(BLOCKED.name())) {
			return "Заблокирован";
		} 
		if (name().equals(PAID.name())) {
			return "Оплачено";
		} 
		if (name().equals(UNPAID.name())) {
			return "Не оплачено";
		}
		return name().toLowerCase();
	}
}
