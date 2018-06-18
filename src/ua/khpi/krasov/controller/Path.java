package ua.khpi.krasov.controller;

public class Path {
	
	public static final String LOGIN_PAGE = "/login.jsp";
	
	public static final String REGISTRATION_PAGE = "/registration.jsp";
	
	public static final String SETTING_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=settings&saved=true";
	
	public static final String ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	
	public static final String SETTING_PAGE = "/WEB-INF/jsp/settings_page.jsp";
	
	public static final String CLIENT_PAGE = "/WEB-INF/jsp/client/client.jsp";
	
	public static final String CLIENT_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=client";
	
	public static final String BILL_REFILL_PAGE = "/WEB-INF/jsp/client/bill_refill_page.jsp";
	
	public static final String BILL_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=billRefill&completed=true";
	
	public static final String SERVICES_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=clientServices";
	
	public static final String CLIENT_SERVICES_PAGE = "/WEB-INF/jsp/client/client_services_page.jsp";
	
	public static final String CLIENT_TARIFFS_PAGE = "/WEB-INF/jsp/client/client_tariffs_page.jsp";
	
	public static final String CLIENT_ORDERS_PAGE = "/WEB-INF/jsp/client/client_orders_page.jsp";
	
	public static final String CLIENT_ORDERS_REDIRECT = "http://localhost:8080/FinalTask/Controller?command=clientOrders";
	
	public static final String ADMIN_PAGE = "/WEB-INF/jsp/admin/admin.jsp";
	
	public static final String ADMIN_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=admin";
	
	public static final String CLIENT_LIST_PAGE = "/WEB-INF/jsp/admin/client_list_page.jsp";
	
	public static final String CLIENT_LIST_REDIRECT_PAGE = "http://localhost:8080/FinalTask/Controller?command=clientList";
	
}
