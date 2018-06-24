package test.ua.khpi.krasov.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.entity.User;

class UserDaoTest {
	
	UserDao ud = new UserDao();

	@Test
	void testGetAllUsers() {
		Assertions.assertNotNull(ud.getAllUsers());
	}

	@Test
	void testInsertUser() {
		Assertions.assertFalse(ud.insertUser(new User()));
	}

	@Test
	void testUpdateLogin() {
		Assertions.assertFalse(ud.updateLogin(new User()));
	}

	@Test
	void testUpdatePassword() {
		Assertions.assertFalse(ud.updatePassword(new User()));
	}

	@Test
	void testUpdateFirstName() {
		Assertions.assertFalse(ud.updateFirstName(new User()));
	}

	@Test
	void testUpdateLastName() {
		Assertions.assertFalse(ud.updateLastName(new User()));
	}

	@Test
	void testGetUserByLogin() {
		Assertions.assertNull(ud.getUserByLogin("asdasd"));
	}

	@Test
	void testDeleteUserByLogin() {
		Assertions.assertFalse(ud.deleteUserByLogin("adsasdasd"));
	}

	@Test
	void testUpdateBill() {
		Assertions.assertFalse(ud.updateBill(new User()));
	}

	@Test
	void testUpdateStatus() {
		Assertions.assertFalse(ud.updateStatus(new User()));
	}

	@Test
	void testGetAllClients() {
		Assertions.assertNotNull(ud.getAllClients());
	}

}
