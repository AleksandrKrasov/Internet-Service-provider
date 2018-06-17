package ua.khpi.krasov.db.dao.interfaces;

import java.util.List;

import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

public interface ServiceTariffsDaoInterface {
	
	boolean insertTariffForService(Tariff tarrif, Service service);
	
	boolean deleteTariffFromService(Tariff tariff, Service service);
	
	List<Integer> getAllIdByServiceId(Service service);
}
