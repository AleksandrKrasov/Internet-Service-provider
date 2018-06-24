package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.Service;

/**
 * Interface sets a contract for a DAO Service.
 * DAO which works with Service entity must implement this interface.
 *  
 * @author A.Krasov
 * @see Service
 * @version 1.0
 *
 */
public interface ServiceDaoInterface {
	
	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Service objects which obtains from DB
	 * @see Serivce
	 */
	List<Service> getAllServices();
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param service which is instance of Service
	 * @return true if data successfully saved
	 * @see Service
	 */
	boolean insertService(Service service);
	
	/**
	 * Method allows to extract service from DB.
	 * Method should be used only in DAO class as handler.
	 * 
	 * @param rs result of query
	 * @return Service entity
	 * @throws SQLException if SQL exceptions happen
	 * @see ResultSet
	 * @see Service
	 */
	Service extractService(ResultSet rs) throws SQLException;
	
	/**
	 * Method allows to get service by its name.
	 * @param name which will be searched for
	 * @return service entity
	 * @see ResultSet
	 */
	Service getServiceByName(String name);
	
	/**
	 * Method allows to get service by its id.
	 * @param id which will be searched for
	 * @return service entity
	 */
	Service getServiceById(int id);
	
	/**
	 * Method allows to update service entity in DB.
	 * @param service entity
	 * @return true if order successfully updated
	 * @see Service
	 */
	boolean updateService(Service service);
	
	/**
	 * Method allows to update service Russian name in DB.
	 * @param service entity
	 * @return true if order successfully updated
	 * @see Service
	 */
	boolean updateNameRu(Service service);
	
	/**
	 * Method allows to delete service entity from DB.
	 * @param service entity
	 * @return true if order successfully deleted
	 * @see Service
	 */
	boolean deleteService(Service service);
}
