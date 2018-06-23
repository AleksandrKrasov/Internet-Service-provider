package ua.khpi.krasov.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.khpi.krasov.db.DBManager;
import ua.khpi.krasov.db.dao.interfaces.TariffDaoInterface;
import ua.khpi.krasov.db.entity.Tariff;

public class TariffDao implements TariffDaoInterface {
	
	private String SQL_SELECT_ALL_TARIFFS = "SELECT * FROM tariffs";
	
	private String SQL_INSERT_TARIFF = "INSERT INTO tariffs VALUES (DEFAULT, ?, ?, ?, ?, ?)";
	
	private String SQL_UPDATE_NAME = "UPDATE tariffs SET name=? WHERE id=?";
	
	private String SQL_UPDATE_NAME_RU = "UPDATE tariffs SET name_ru=? WHERE id=?";
	
	private String SQL_UPDATE_DESCRIPTION = "UPDATE tariffs SET description=? WHERE id=?";
	
	private String SQL_UPDATE_DESCRIPTION_RU = "UPDATE tariffs SET description_ru=? WHERE id=?";
	
	private String SQL_UPDATE_PRICE = "UPDATE tariffs SET price=? WHERE id=?";
	
	private String SQL_FIND_TARIF_BY_NAME = "SELECT * FROM tariffs WHERE name=?";
	
	private String SQL_DELETE_TARIFF = "DELETE FROM tariffs WHERE name=?";
	
	private String SQL_FIND_TARIFF_BY_ID = "SELECT * FROM tariffs WHERE id=?";


	@Override
	public List<Tariff> getAllTariffs() {
		List<Tariff> tariffs = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean insertTariff(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateName(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateDescription(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public Tariff getTarrifByName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updatePrice(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean deleteTarrif(Tariff tariff) {
		boolean res = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public Tariff getTariffByID(int id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateNameRu(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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

	@Override
	public boolean updateDescriptionRu(Tariff tariff) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getInstance().getConnection();
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
