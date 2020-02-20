package subject.dao;

import subject.impl.AdultHealthDaoImpl;
import subject.impl.FileImportDaoImpl;

public class DaoFactory {
	public static AdultHealthDao creatAdultHealthDao() {
		AdultHealthDao ahDao = new AdultHealthDaoImpl();
		return ahDao;
	}
	
	public static FileImportDao creatFileImportDao() {
		FileImportDao fileImp = new FileImportDaoImpl();
		return fileImp;
	}
}