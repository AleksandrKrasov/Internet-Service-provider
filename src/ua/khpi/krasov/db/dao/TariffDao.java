package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DbManager;
import ua.khpi.krasov.db.dao.interfaces.OrderDaoInterface;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Tariff;

/**
 * DAO Tariff class. This class allows to work with DB.
 * This class allows to obtain data connecting with 'isp' DB
 * and table 'tariffs'. Class implements TariffDaoInterface
 * 
 * @author A.Krasov
 * @see OrderDaoInterface
 * @see Tariff
 * @version 1.0
 * 
 */
public class TariffDao implements TariffDaoInterface {
	
	private static String SQL_SELECT_ALL_TARIFFS = "SELECT * FROM tariffs";
	
	private static String SQL_INSERT_TARIFF = "INSERT INTO tariffs VALUES (DEFAULT, ?, ?, ?, ?, ?)";
	
	private static String SQL_UPDATE_NAME = "UPDATE tariffs SET name=? WHERE id=?";
	
	private static String SQL_UPDATE_NAME_RU = "UPDATE tariffs SET name_ru=? WHERE id=?";
	
	private static String SQL_UPDATE_DESCRIPTION = "UPDATE tariffs SET description=? WHERE id=?";
	
	private static String SQL_UPDATE_DESCRIPTION_RU = "UPDATE tariffs SET description_ru=? WHERE id=?";
	
	private static String SQL_UPDATE_PRICE = "UPDATE tariffs SET price=? WHERE id=?";
	
	private static String SQL_FIND_TARIF_BY_NAME = "SELECT * FROM tariffs WHERE name=?";
	
	private static String SQL_DELETE_TARIFF = "DELETE FROM tariffs WHERE name=?";
	
	private static String SQL_FIND_TARIFF_BY_ID = "SELECT * FROM tariffs WHERE id=?";

	/**
	 * Method allows to obtained all data from DB.
	 * @return List of Tariff objects which obtains from DB
	 * @see Tariff
	 */
	@Override
	public List<Tariff> getAllTariffs() {
		List<Tariff> tariffs = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_TARIFFS);
			while (rs.next()) {
				Tariff tariff = extractTariff(rs);
				tariffs.add(tariff);
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
		return tariffs;
	}

	/**
	 * Method allows to insert data to DB.
	 * 
	 * @param tariff which is instance of Tariff
	 * @return true if data successfully saved
	 * @see Tariff
	 */
	@Override
	public boolean insertTariff(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_TARIFF, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, tariff.getName());
			pstmt.setString(k++, tariff.getDescription());
			pstmt.setInt(k++, tariff.getPrice());
			pstmt.setString(k++, tariff.getNameRu());
			pstmt.setString(k++, tariff.getDescriptionRu());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					tariff.setId(rs.getInt(1));
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
	 * Method allows to extract tariff entity from DB.
	 * Method should be used only in DAO class as handler.
	 * 
	 * @param rs result of query
	 * @return Tariff entity
	 * @throws SQLException if SQL exceptions happen
	 * @see ResultSet
	 * @see Tariff
	 */
	@Override
	public Tariff extractTariff(ResultSet rs) throws SQLException {
		Tariff tariff = new Tariff();
		tariff.setId(rs.getInt("id"));
		tariff.setName(rs.getString("name"));
		tariff.setDescription(rs.getString("description"));
		tariff.setPrice(rs.getInt("price"));
		tariff.setNameRu(rs.getString("name_ru"));
		tariff.setDescriptionRu(rs.getString("description_ru"));
		return tariff;
	}
	
	/**
	 * Method allows to update tariff entity name in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	@Override
	public boolean updateName(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_NAME);
			int k = 1;
			pstmt.setString(k++, tariff.getName());
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
	 * Method allows to update tariff entity description in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	@Override
	public boolean updateDescription(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_DESCRIPTION);
			int k = 1;
			pstmt.setString(k++, tariff.getDescription());
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
	 * Method allows to get a tariff entity by its name from DB.
	 * 
	 * @param name which will be searched for
	 * @return Tariff entity
	 * @see ResultSet
	 * @see Tariff
	 */
	@Override
	public Tariff getTarrifByName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_TARIF_BY_NAME);
			int k = 1;
			pstmt.setString(k++, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractTariff(rs);
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
	 * Method allows to update tariff entity price in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	@Override
	public boolean updatePrice(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_PRICE);
			int k = 1;
			pstmt.setInt(k++, tariff.getPrice());
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
	 * Method allows to delete tariff entity from DB.
	 * @param tariff entity
	 * @return true if tariff successfully deleted
	 * @see Tariff
	 */
	@Override
	public boolean deleteTarrif(Tariff tariff) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_TARIFF);
			int k = 1;
			pstmt.setString(k++, tariff.getName());
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
	 * Method allows to get a tariff entity from DB by its id.
	 * @param id which will be searched for
	 * @return Tariff entity
	 */
	@Override
	public Tariff getTariffById(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_TARIFF_BY_ID);
			int k = 1;
			pstmt.setInt(k++, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractTariff(rs);
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
	 * Method allows to update tariff entity Russian name in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	@Override
	public boolean updateNameRu(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_NAME_RU);
			int k = 1;
			pstmt.setString(k++, tariff.getNameRu());
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
	 * Method allows to update tariff entity Russian description in DB.
	 * @param tariff entity
	 * @return true if tariff entity successfully updated
	 * @see Tariff
	 */
	@Override
	public boolean updateDescriptionRu(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DbManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_DESCRIPTION_RU);
			int k = 1;
			pstmt.setString(k++, tariff.getDescriptionRu());
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
	
}
