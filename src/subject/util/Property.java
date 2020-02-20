package subject.util;

public class Property {	
	public static final String MS_JAR_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String MyS_JAR_NAME = "com.mysql.cj.jdbc.Driver";

	public static final String MS_DATABASE_NAME = "SubjectOneTest";
	public static final String MS_ACCOUNT_NAME = "sa";
	public static final String MyS_DATABASE_NAME = "MySQL";
	public static final String MyS_ACCOUNT_NAME = "root";
	public static final String PASSWORD = "A12345@10902";
	
	public static final String MS_URL = "jdbc:sqlserver://localhost:1433;databaseName=" + MS_DATABASE_NAME + ";user=" + MS_ACCOUNT_NAME
			+ ";password=" + PASSWORD;
	
	public static final String PERSON_TALL = "身高別";
	public static final String LOWER_BOUND = "正常體重範圍下限";
	public static final String UPPDER_BOUND = "正常體重範圍上限";
	public static final String FAT_VALUE = "肥胖體重";
	public static final String CREATE_TIME = "資料匯入時間";
	
	public static final String DOWNLOAD_FOLDER_PATH = "C:\\Users\\Student\\Download\\";
	
}