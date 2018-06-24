package ua.khpi.krasov.db.entity;

/**
 * Entity Order class. It allows to store data from
 * DB. Class does not have any logic. Class extends Entity class.
 * This is serializable.
 * 
 * @author A.Krasov
 * @see Entity
 *
 */
public class Order extends Entity {

	private static final long serialVersionUID = 2329442001992053581L;
	
	private int userId;
	
	private int serviceId;
	
	private int tariffId;
	
	private int statusId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTariffId() {
		return tariffId;
	}

	public void setTariffId(int tariffId) {
		this.tariffId = tariffId;
	}

	@Override
	public String toString() {
		return "Order [userId=" + userId + ", serviceId=" + serviceId + ", tariffId=" + tariffId + ", statusId="
				+ statusId + "]";
	}
	
}
