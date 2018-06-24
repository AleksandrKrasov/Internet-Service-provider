package test.ua.khpi.krasov.db.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.entity.Service;

class ServiceDaoTest {
	
	ServiceDao sd = new ServiceDao();

	@Test
	void testGetAllServices() {
		Assertions.assertNotNull(sd.getAllServices());
	}

	@Test
	void testInsertService() {
		Assertions.assertTrue(sd.insertService(new Service()));
	}

	@Test
	void testGetServiceByName() {
		Assertions.assertNull(sd.getServiceByName("asdd"));
	}

	@Test
	void testUpdateNameRu() {
		Assertions.assertFalse(sd.updateNameRu(new Service()));
	}

	@Test
	void testUpdateService() {
		Assertions.assertFalse(sd.updateService(new Service()));
	}

	@Test
	void testDeleteService() {
		Assertions.assertFalse(sd.deleteService(new Service()));
	}

	@Test
	void testGetServiceById() {
		Assertions.assertNotNull(sd.getServiceById(23123));
	}

}
