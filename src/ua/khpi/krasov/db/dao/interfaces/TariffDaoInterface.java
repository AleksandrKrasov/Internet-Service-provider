package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.Tariff;


public interface TariffDaoInterface {
	
	List<Tariff> getAllTariffs();
	
	boolean insertTariff(Tariff tariff);
	
	Tariff extractTariff(ResultSet rs) throws SQLException;
	
	Tariff getTarrifByName(String name);
	
	boolean updateName(Tariff tariff);
	
	boolean updateNameRu(Tariff tariff);
	
	boolean updateDescription(Tariff tariff);
	
	boolean updateDescriptionRu(Tariff tariff);
	
	boolean updatePrice(Tariff tariff);
	
	boolean deleteTarrif(Tariff tariff);
	
	Tariff getTariffByID(int id);
	
}
