package test.ua.khpi.krasov.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.entity.Tariff;

class TariffDaoTest {
	
	TariffDao td = new TariffDao();
	
	@Test
	void testGetAllTariffs() {
		Assertions.assertNotNull(td.getAllTariffs());
	}

	@Test
	void testInsertTariff() {
		Assertions.assertTrue(td.insertTariff(new Tariff()));
	}

	@Test
	void testUpdateName() {
		Assertions.assertFalse(td.updateName(new Tariff()));
	}

	@Test
	void testUpdateDescription() {
		Assertions.assertFalse(td.updateDescription(new Tariff()));
	}

	@Test
	void testGetTarrifByName() {
		Assertions.assertNull(td.getTarrifByName("asdasd"));
	}

	@Test
	void testUpdatePrice() {
		Assertions.assertFalse(td.updatePrice(new Tariff()));
	}

	@Test
	void testDeleteTarrif() {
		Assertions.assertFalse(td.deleteTarrif(new Tariff()));
	}

	@Test
	void testGetTariffById() {
		Assertions.assertNull(td.getTariffById(2321));
	}

	@Test
	void testUpdateNameRu() {
		Assertions.assertFalse(td.updateNameRu(new Tariff()));
	}

	@Test
	void testUpdateDescriptionRu() {
		Assertions.assertFalse(td.updateDescriptionRu(new Tariff()));
	}

}
