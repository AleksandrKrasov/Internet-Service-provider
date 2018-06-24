package ua.khpi.krasov.db.dao.interfaces;

import java.util.List;

import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

/**
 * Interface sets a contract for a DAO ServiceTariffs and tells how to
 * connect Tariff entity with Service entity.
 * DAO which works with ServiceTariffs entity must implement this interface.
 * 
 *  
 * @author A.Krasov
 * @see Tariff
 * @version 1.0
 *
 */
public interface ServiceTariffsDaoInterface {
	
	/**
	 * Method allows to insert a tariff entity to service entity to
	 * create a connection between them.
	 * 
	 * @param tarrif which will be inserted to a service entity
	 * @param service for inserting
	 * @return true if tariff successfully added to service entity
	 */
	boolean insertTariffForService(Tariff tarrif, Service service);
	
	/**
	 * Method allows to delete a tariff entity from service entity to
	 * cancel a connection between them.
	 * 
	 * @param tariff which will be deleted from a service entity
	 * @param service for point
	 * @return true if tariff successfully deleted from a service entity
	 */
	boolean deleteTariffFromService(Tariff tariff, Service service);
	
	/**
	 * Method allows to get all tariff's IDs from a service entity.
	 * @param service for a concrete tariffs
	 * @return list of tariff's IDs
	 */
	List<Integer> getAllIdByServiceId(Service service);
}
