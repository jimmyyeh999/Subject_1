package subject.util;

import static subject.util.Property.MS_JAR_NAME;
import static subject.util.Property.MS_URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {	
	
	private static Connection conn;

	public static Connection createMSConn() throws SQLException, ClassNotFoundException {
		Class.forName(MS_JAR_NAME);
		conn = DriverManager.getConnection(MS_URL);
		
		if (null == conn) {
			throw new SQLException("Connect database fail");
		}
		conn.setAutoCommit(true);
		
		return conn;
	}
	
	public static void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}