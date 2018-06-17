package ua.khpi.krasov;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.Role;
import ua.khpi.krasov.db.dao.OrderDao;
import ua.khpi.krasov.db.dao.ServiceDao;
import ua.khpi.krasov.db.dao.ServiceTariffsDao;
import ua.khpi.krasov.db.dao.TariffDao;
import ua.khpi.krasov.db.dao.UserDao;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.ServiceTariffsDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.UserDaoInterface;
import ua.khpi.krasov.db.entity.Order;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServiceDaoInterface serDao = new ServiceDao();
		TariffDaoInterface tarDao = new TariffDao();
		OrderDaoInterface ordDao = new OrderDao();
		ServiceTariffsDaoInterface stDao = new ServiceTariffsDao();
		
		
//		Service service1 = serDao.getServiceByName("Internet");
//		Service service2 = serDao.getServiceByName("TV");
//		
//		Tariff tariff1 = tarDao.getTarrifByName("Standart");
//		Tariff tariff2 = tarDao.getTarrifByName("Premium");
//		
//		Tariff tariff3 = tarDao.getTarrifByName("Simple");
//		Tariff tariff4 = tarDao.getTarrifByName("Elite");
//		
//		stDao.insertTariffForService(tariff1, service1);
//		stDao.insertTariffForService(tariff2, service1);
//		stDao.insertTariffForService(tariff3, service2);
//		stDao.insertTariffForService(tariff4, service2);
		
		
		
		

		
		//serDao.insertService(service1);
		//serDao.insertService(service2);
		//tarDao.insertTariff(tariff1);
		
		
		
//		
//		Order order = new Order();
//		order.setServiceId(serDao.getServiceByName("Internet").getId());
//		order.setStatusId(0);
//		order.setTariffId(tarDao.getTarrifByName("premium").getId());
//		order.setUserId(userDao.getUserByLogin("kakaska").getId());
		
		//stDao.insertTariffForService(tarDao.getTarrifByName("premium"), serDao.getServiceByName("Internet"));
		//stDao.deleteTariffFromService(tarDao.getTarrifByName("premium"), serDao.getServiceByName("Internet"));
		
		//ordDao.insertOrder(order);
		//response.getWriter().append(ordDao.getOrdersByUser(userDao.getUserByLogin("kakaska")).toString());
		//ordDao.deleteOrder(order);
		
		
		
//		List<Order> list = ordDao.getAllOrders();
//		for(Order tar : list) {
//			response.getWriter().append(tar.toString());
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
