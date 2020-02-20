package subject.dao;

import java.sql.SQLException;
import java.util.List;

import subject.bean.AdultHealthBean;

public interface AdultHealthDao {
    /**
     * Create a new record in Database.
     * @param bean   The Object to be inserted.
     */
	public void insert(AdultHealthBean bean) throws SQLException, ClassNotFoundException;
	
    /**
     * Select data from Database by file type.
     * @param fileType
     */
	public List<AdultHealthBean> queryData(String fileType) throws SQLException, ClassNotFoundException;
	
}