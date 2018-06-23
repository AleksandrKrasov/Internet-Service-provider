package ua.khpi.krasov.db.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.khpi.krasov.db.entity.Service;

public interface ServiceDaoInterface {
	
	List<Service> getAllServices();
	
	boolean insertService(Service service);
	
	Service extractService(ResultSet rs) throws SQLException;
	
	Service getServiceByName(String login);
	
	Service getServiceById(int id);
	
	boolean updateService(Service service);
	
	boolean updateNameRu(Service service);
	
	boolean deleteService(Service service);
}
