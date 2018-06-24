package test.ua.khpi.krasov.db;

import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ua.khpi.krasov.db.DbManager;

class DbManagerTest {
	
	@Test
	void testGetConnectionWithDriverManager() throws SQLException {
		DbManager db = DbManager.getInstance();
		Assertions.assertNotNull(db.getConnectionWithDriverManager());
	}
	
	@Test
	void testGetInstance() {
		Assertions.assertNotNull(DbManager.getInstance());
	}

}
