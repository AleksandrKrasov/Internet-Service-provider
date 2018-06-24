package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.Tariff;

/**
 * Interface sets a contract for a DAO Tariff.
 * DAO which works with Tariff entity must implement this interface.
 *  
 * @author A.Krasov
 * @version 1.0
 *
 */
public interface TariffDaoInterface {
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Tariff objects which obtains from DB
	 * @see Tariff
	 */
	List<Tariff> getAllTariffs();
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param tariff which is instance of Tariff
	 * @return true if data successfully saved
	 * @see Tariff
	 */
	boolean insertTariff(Tariff tariff);
	
	/**
	 * Method allows to extract tariff entity from DB.
	 * Method should be used only in DAO class as handler.
	 * 
	 * @param rs result of query
	 * @return Tariff entity
	 * @throws SQLException if SQL exceptions happen
	 * @see ResultSet
	 * @see Tariff
	 */
	Tariff extractTariff(ResultSet rs) throws SQLException;
	
	/**
	 * Method allows to get a tariff entity by its name from DB.
	 * 
	 * @param name which will be searched for
	 * @return Tariff entity
	 * @see ResultSet
	 * @see Tariff
	 */
	Tariff getTarrifByName(String name);
	
	/**
	 * Method allows to update tariff entity name in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	boolean updateName(Tariff tariff);
	
	/**
	 * Method allows to update tariff entity Russian name in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	boolean updateNameRu(Tariff tariff);
	
	/**
	 * Method allows to update tariff entity description in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	boolean updateDescription(Tariff tariff);
	
	/**
	 * Method allows to update tariff entity Russian description in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	boolean updateDescriptionRu(Tariff tariff);
	
	/**
	 * Method allows to update tariff entity price in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	boolean updatePrice(Tariff tariff);
	
	/**
	 * Method allows to delete tariff entity from DB.
	 * @param tariff entity
	 * @return true if tariff successfully deleted
	 * @see Tariff
	 */
	boolean deleteTarrif(Tariff tariff);
	
	/**
	 * Method allows to get a tariff entity from DB by its id.
	 * @param id which will be searched for
	 * @return Tariff entity
	 */
	Tariff getTariffById(int id);
	
}
