package subject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import subject.bean.AdultHealthBean;
import subject.dao.AdultHealthDao;
import subject.dao.DaoFactory;
import subject.dao.FileImportDao;

public class CsvFileUtil {

	public void readAndInsert(String filePath, String fileType)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		File csv = new File(filePath);
		try (CSVReader reader = new CSVReader(new FileReader(csv))) {
			String[] nextLine;
			int i = 0;
			while ((nextLine = reader.readNext()) != null) {
				if (i == 0) {
					i++;
					continue;
				}

				AdultHealthBean bean = new AdultHealthBean();
				bean.setPersonTall(Integer.parseInt(nextLine[0]));
				bean.setLowerBound(Float.parseFloat(nextLine[1]));
				bean.setUpperBound(Float.parseFloat(nextLine[2]));
				bean.setFatBodyWeight(nextLine[3]);
				bean.setFileType(fileType);

				AdultHealthDao ahDao = DaoFactory.creatAdultHealthDao();
				ahDao.insert(bean);
			}
			FileImportDao fileImp = DaoFactory.creatFileImportDao();
			fileImp.updateStatus(fileType);
		}
	}

	public void createCsv(List<AdultHealthBean> data) throws IOException {
		File csv = new File("C:\\Users\\Student\\Download\\subject_New.csv");
		String[] header = { "身高別", "正常體重範圍下限", "正常體重範圍上限", "肥胖", "匯入時間" };
		try (CSVWriter writer = new CSVWriter(new FileWriter(csv))) {
			int i = 0;
			for (AdultHealthBean obj : data) {
				if (i == 0) {
					writer.writeNext(header);
				}
				String[] dataList = new String[5];
				dataList[0] = String.valueOf(obj.getPersonTall());
				dataList[1] = Float.toString(obj.getLowerBound());
				dataList[2] = Float.toString(obj.getUpperBound());
				dataList[3] = obj.getFatBodyWeight();
				dataList[4] = obj.getTime().toString();
				writer.writeNext(dataList);
			}
		}
	}
}
