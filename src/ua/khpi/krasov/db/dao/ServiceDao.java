package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.entity.Service;

/**
 * DAO Service class. This class allows to work with DB.
 * This class allows to obtain data connecting with 'isp' DB
 * and table 'services'. Class implements ServiceDaoInterface
 * 
 * @author A.Krasov
 * @see ServiceDaoInterface
 * @version 1.0
 * 
 */
public class ServiceDao implements ServiceDaoInterface {

	private static String SQL_SELECT_ALL_SERVICES = "SELECT * FROM services";

	private static String SQL_INSERT_SERVICE = "INSERT INTO services VALUES (DEFAULT, ?, ?)";

	private static String SQL_FIND_SERVICE_BY_NAME = "SELECT * FROM services WHERE name=?";

	private static String SQL_FIND_SERVICE_BY_ID = "SELECT * FROM services WHERE id=?";

	private static String SQL_UPDATE_SERVICE = "UPDATE services SET name=? WHERE id=?";
	
	private static String SQL_UPDATE_SERVICE_RU = "UPDATE services SET name_ru=? WHERE id=?";

	private static String SQL_DELETE_SERVICE = "DELETE FROM services WHERE name=?";

	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Service objects which obtains from DB
	 * @see Serivce
	 */
	@Override
	public List<Service> getAllServices() {
		List<Service> services = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DbManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_SERVICES);
			while (rs.next()) {
				Service service = extractService(rs);
				services.add(service);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return services;
	}
	
	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param service which is instance of Service
	 * @return true if data successfully saved
	 * @see Service
	 */
	@Override
	public boolean insertService(Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, service.getName());
			pstmt.setString(k++, service.getNameRu());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					service.setId(rs.getInt(1));
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
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
	@Override
	public Service extractService(ResultSet rs) throws SQLException {
		Service service = new Service();
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
		service.setNameRu(rs.getString("name_ru"));
		return service;
	}
	
	/**
	 * Method allows to get service by its name.
	 * @param name which will be searched for
	 * @return service entity
	 * @see ResultSet
	 */
	@Override
	public Service getServiceByName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_SERVICE_BY_NAME);
			int k = 1;
			pstmt.setString(k++, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractService(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Method allows to update service Russian name in DB.
	 * @param service entity
	 * @return true if order successfully updated
	 * @see Service
	 */
	@Override
	public boolean updateNameRu(Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SERVICE_RU);
			int k = 1;
			pstmt.setString(k++, service.getNameRu());
			pstmt.setInt(k++, service.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Method allows to update service entity in DB.
	 * @param service entity
	 * @return true if order successfully updated
	 * @see Service
	 */
	@Override
	public boolean updateService(Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(SQL_UPDATE_SERVICE);
			int k = 1;
			pstmt.setString(k++, service.getName());
			pstmt.setInt(k++, service.getId());
			if (pstmt.executeUpdate() > 0) {
				con.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.rollback();
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Method allows to delete service entity from DB.
	 * @param service entity
	 * @return true if order successfully deleted
	 * @see Service
	 */
	@Override
	public boolean deleteService(Service service) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_SERVICE);
			int k = 1;
			pstmt.setString(k++, service.getName());
			res = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	/**
	 * Method allows to get service by its id.
	 * @param id which will be searched for
	 * @return service entity
	 */
	@Override
	public Service getServiceById(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_SERVICE_BY_ID);
			int k = 1;
			pstmt.setInt(k++, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractService(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
