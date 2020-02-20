package subject.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import subject.bean.AdultHealthBean;
import subject.dao.AdultHealthDao;
import subject.dao.DaoFactory;
import subject.dao.FileImportDao;

public class JsonFileUtil {

	public void readAndInsert(String filePath, String fileType)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		File jsonFile = new File(filePath);
		try (BufferedReader br = new BufferedReader(new FileReader(jsonFile));) {
			String data = null;
			
			while ((data = br.readLine()) != null) {
				JSONArray jsonArr = JSONArray.fromObject(data);
				
				for (int i = 0; i < jsonArr.size(); i++) {
					JSONObject obj = (JSONObject) jsonArr.get(i);
					int tall = obj.getInt("身高別(cm)");
					float lowerBound = Float.parseFloat(obj.getString("正常體重範圍下限(kg)"));
					float upperBound = Float.parseFloat(obj.getString("正常體重範圍上限(kg)"));
					String fatBody = obj.getString("肥胖(kg)");

					AdultHealthBean bean = new AdultHealthBean();
					bean.setPersonTall(tall);
					bean.setLowerBound(lowerBound);
					bean.setUpperBound(upperBound);
					bean.setFatBodyWeight(fatBody);
					bean.setFileType(fileType);

					AdultHealthDao ahDao = DaoFactory.creatAdultHealthDao();
					ahDao.insert(bean);
				}
			}

			FileImportDao fileImp = DaoFactory.creatFileImportDao();
			fileImp.updateStatus(fileType);
		}
	}

	public void creatJson(List<AdultHealthBean> dataJson)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		List<Object> jsonList = new ArrayList<Object>();
		for (AdultHealthBean obj : dataJson) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("身高", String.valueOf(obj.getPersonTall()));
			dataMap.put("正常體重最小值", Float.toString(obj.getLowerBound()));
			dataMap.put("正常體重最大值", Float.toString(obj.getUpperBound()));
			dataMap.put("肥胖體重", obj.getFatBodyWeight());
			dataMap.put("匯入資料庫時間", obj.getTime().toString());
			jsonList.add(dataMap);
		}

		JSONArray jsonObject = JSONArray.fromObject(jsonList);
		String jsonString = jsonObject.toString();

		File json = new File("C:\\Users\\Student\\Download\\subject_New.json");

		try (Writer write = new OutputStreamWriter(new FileOutputStream(json), "UTF-8");) {
			write.write(jsonString);
			write.flush();
		}
	}

	// public static void main(String[] args) throws FileNotFoundException,
	// IOException {
	// JsonFileUtil json = new JsonFileUtil();
	// json.readAndInsert("F:\\Download\\subject.json");
	// }
}
