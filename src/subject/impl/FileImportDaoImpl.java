package subject.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import subject.dao.FileImportDao;
import subject.util.DbUtil;

public class FileImportDaoImpl implements FileImportDao {

	private static final String SQL_CHECK = "SELECT * FROM FILE_IMPORT WHERE FILE_TYPE = ? AND ISIMPORT = 1";
	
	private static final String SQL_UPDATE = "UPDATE FILE_IMPORT SET ISIMPORT = 1 WHERE FILE_TYPE = ?";

	@Override
	public boolean checkExist(String fileType, Connection conn) throws SQLException, ClassNotFoundException {
		boolean exist = false;
		try (PreparedStatement ps = conn.prepareStatement(SQL_CHECK);) {
			ps.setString(1, fileType);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					System.out.println(fileType + "已匯入");
					exist = true;
				}
			}
		}
		return exist;
	}

	@Override
	public void updateStatus(String fileType) throws SQLException, ClassNotFoundException {
		try (Connection conn = DbUtil.createMSConn(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE);) {
			ps.setString(1, fileType);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}