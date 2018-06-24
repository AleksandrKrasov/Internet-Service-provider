package test.ua.khpi.krasov.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

class ServiceTariffsDaoTest {
	
	ServiceTariffsDao std = new ServiceTariffsDao();

	@Test
	void testInsertTariffForService() {
		Assertions.assertFalse(std.insertTariffForService(new Tariff(), new Service()));
	}

	@Test
	void testDeleteTariffFromService() {
		Assertions.assertFalse(std.deleteTariffFromService(new Tariff(), new Service()));
	}

	@Test
	void testGetAllIdByServiceId() {
		Assertions.assertNull(std.getAllIdByServiceId(new Service()));
	}

}
