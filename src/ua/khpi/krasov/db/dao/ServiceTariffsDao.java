package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.dao.interfaces.ServiceTariffsDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

/**
 * DAO ServiceTariffs class. This class allows to work with DB.
 * This class allows to connect a Tariff entity with Service entity.
 * and table 'services'. Class implements ServiceTariffsDaoInterface
 * 
 * @author A.Krasov
 * @see ServiceTariffsDaoInterface
 * @See Tariff
 * @see Service
 * @version 1.0
 * 
 */
public class ServiceTariffsDao implements ServiceTariffsDaoInterface {

	private static String SQL_INSERT_TARIFF_FOR_SERVICE = "INSERT INTO service_tariffs VALUES (?, ?)";

	private static String SQL_DELETE_TARIFF_FROM_SERVICE = "DELETE FROM service_tariffs WHERE service_id=? AND tariff_id=?";

	private static String SQL_SELECT_ALL_ID_BY_SERVICE_ID = "SELECT * FROM service_tariffs WHERE service_id=?";
	
	/**
	 * Method allows to insert a tariff entity to service entity to
	 * create a connection between them.
	 * 
	 * @param tariff which will be inserted to a service entity
	 * @param service for inserting
	 * @return true if tariff successfully added to service entity
	 */
	@Override
	public boolean insertTariffForService(Tariff tariff, Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_TARIFF_FOR_SERVICE);
			int k = 1;
			pstmt.setInt(k++, service.getId());
			pstmt.setInt(k++, tariff.getId());
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
	 * Method allows to delete a tariff entity from service entity to
	 * cancel a connection between them.
	 * 
	 * @param tariff which will be deleted from a service entity
	 * @param service for point
	 * @return true if tariff successfully deleted from a service entity
	 */
	@Override
	public boolean deleteTariffFromService(Tariff tariff, Service service) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_TARIFF_FROM_SERVICE);
			int k = 1;
			pstmt.setInt(k++, service.getId());
			pstmt.setInt(k++, tariff.getId());
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
	 * Method allows to get all tariff's IDs from a service entity.
	 * @param service for a concrete tariffs
	 * @return list of tariff's IDs
	 */
	@Override
	public List<Integer> getAllIdByServiceId(Service service) {
		List<Integer> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_ALL_ID_BY_SERVICE_ID);
			int k = 1;
			pstmt.setInt(k++, service.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int tariffId = rs.getInt("tariff_id");
				list.add(tariffId);
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
		return list;
	}

}
