package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.interfaces.ServiceTariffsDaoInterface;
import ua.khpi.krasov.db.entity.Service;
import ua.khpi.krasov.db.entity.Tariff;

public class ServiceTariffsDao implements ServiceTariffsDaoInterface {

	private String SQL_INSERT_TARIFF_FOR_SERVICE = "INSERT INTO service_tariffs VALUES (?, ?)";

	private String SQL_DELETE_TARIFF_FROM_SERVICE = "DELETE FROM service_tariffs WHERE service_id=? AND tariff_id=?";

	private String SQL_SELECT_ALL_ID_BY_SERVICE_ID = "SELECT * FROM service_tariffs WHERE service_id=?";

	@Override
	public boolean insertTariffForService(Tariff tariff, Service service) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean deleteTariffFromService(Tariff tarrif, Service service) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_TARIFF_FROM_SERVICE);
			int k = 1;
			pstmt.setInt(k++, service.getId());
			pstmt.setInt(k++, tarrif.getId());
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
	public List<Integer> getAllIdByServiceId(Service service) {
		List<Integer> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_ALL_ID_BY_SERVICE_ID);
			int k = 1;
			pstmt.setInt(k++, service.getId());
			rs = pstmt.executeQuery();
			while(rs.next()) {
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
