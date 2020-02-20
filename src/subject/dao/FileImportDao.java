package subject.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface FileImportDao {
    /**
     * check flag by file type if data already import
     * @param fileType
     * @param conn
     */
	public boolean checkExist(String fileType, Connection conn) throws SQLException, ClassNotFoundException;
	
    /**
     * update flag when data import to database
     * @param fileType
     */
	public void updateStatus(String fileType) throws SQLException, ClassNotFoundException;
}