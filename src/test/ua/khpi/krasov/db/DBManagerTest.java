package test.ua.khpi.krasov.db;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ua.khpi.krasov.db.DBManager;

class DBManagerTest {
	
	@Test
	void testGetConnectionWithDriverManager() throws SQLException {
		DBManager db = DBManager.getInstance();
		Assertions.assertNotNull(db.getConnectionWithDriverManager());
	}
	
	@Test
	void testGetInstance() {
		Assertions.assertNotNull(DBManager.getInstance());
	}

}
