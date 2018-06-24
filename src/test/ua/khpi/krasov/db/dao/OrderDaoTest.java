package test.ua.khpi.krasov.db.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.User;

class OrderDaoTest {
	
	OrderDao od = new OrderDao();

	@Test
	void testGetAllOrders() {
		Assertions.assertNotNull(od.getAllOrders());
	}

	@Test
	void testInsertOrder() {
		Assertions.assertFalse(od.insertOrder(new Order()));
	}

	@Test
	void testGetOrdersByUser() {
		Assertions.assertNull(od.getOrdersByUser(new User()));
	}

	@Test
	void testDeleteOrder() {
		Assertions.assertFalse(od.deleteOrder(new Order()));
	}

	@Test
	void testUpdateStatus() {
		Assertions.assertFalse(od.updateStatus((new Order())));
	}

}
