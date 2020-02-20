package subject.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import subject.bean.AdultHealthBean;
import subject.dao.AdultHealthDao;
import subject.dao.DaoFactory;
import subject.dao.FileImportDao;
import subject.util.DbUtil;

public class AdultHealthDaoImpl implements AdultHealthDao {

	private static final String SQL_INSERT = "INSERT INTO ADULT_HEALTH(PERSON_TALL, LOWER_BOUND, UPPDER_BOUND, FAT_VALUE, CREATE_TIME, FILE_TYPE) VALUES(?,?,?,?,?,?)";

	private static final String SQL_QUERY = "SELECT * FROM ADULT_HEALTH WHERE FILE_TYPE = ?";

	@Override
	public void insert(AdultHealthBean bean) throws SQLException, ClassNotFoundException {
		try (Connection conn = DbUtil.createMSConn();) {
			FileImportDao fileImp = DaoFactory.creatFileImportDao();
			if (!fileImp.checkExist(bean.getFileType(), conn)) {
				try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT);) {
					ps.setInt(1, bean.getPersonTall());
					ps.setFloat(2, bean.getLowerBound());
					ps.setFloat(3, bean.getUpperBound());
					ps.setString(4, bean.getFatBodyWeight());
					ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
					ps.setString(6, bean.getFileType());
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<AdultHealthBean> queryData(String fileType) throws SQLException, ClassNotFoundException {
		List<AdultHealthBean> rtn = new ArrayList<AdultHealthBean>();
		try (Connection conn = DbUtil.createMSConn(); PreparedStatement ps = conn.prepareStatement(SQL_QUERY);) {
			ps.setString(1, fileType);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					rtn.add(this.getResults(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public AdultHealthBean getResults(ResultSet rs) throws SQLException {
		AdultHealthBean bean = new AdultHealthBean();
		bean.setPersonTall(rs.getInt("PERSON_TALL"));
		bean.setLowerBound(rs.getFloat("LOWER_BOUND"));
		bean.setUpperBound(rs.getFloat("UPPDER_BOUND"));
		bean.setFatBodyWeight(rs.getString("FAT_VALUE"));
		bean.setTime(rs.getTimestamp("CREATE_TIME"));
		bean.setFileType(rs.getString("FILE_TYPE"));
		return bean;
	}

}