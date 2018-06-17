package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.interfaces.ServiceDaoInterface;
import ua.khpi.krasov.db.entity.Service;

public class ServiceDao implements ServiceDaoInterface {

	private String SQL_SELECT_ALL_SERVICES = "SELECT * FROM services";

	private String SQL_INSERT_SERVICE = "INSERT INTO services VALUES (DEFAULT, ?)";

	private String SQL_FIND_SERVICE_BY_NAME = "SELECT * FROM services WHERE name=?";

	private String SQL_FIND_SERVICE_BY_ID = "SELECT * FROM services WHERE id=?";

	private String SQL_UPDATE_SERVICE = "UPDATE services SET name=? WHERE id=?";

	private String SQL_DELETE_SERVICE = "DELETE FROM services WHERE name=?";

	@Override
	public List<Service> getAllServices() {
		List<Service> services = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean insertService(Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, service.getName());
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

	@Override
	public Service extractService(ResultSet rs) throws SQLException {
		Service service = new Service();
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
		return service;
	}

	@Override
	public Service getServiceByName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateService(Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SERVICE);
			int k = 1;
			pstmt.setString(k++, service.getName());
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

	@Override
	public boolean deleteService(Service service) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public Service getServiceById(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getInstance().getConnection();
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
