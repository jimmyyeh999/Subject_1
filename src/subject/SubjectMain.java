package subject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import subject.bean.AdultHealthBean;
import subject.dao.DaoFactory;
import subject.dao.AdultHealthDao;
import subject.util.CsvFileUtil;
import subject.util.DownloadUtil;
import subject.util.JsonFileUtil;
import subject.util.XmlFileUtil;

public class SubjectMain {

	private final String Extension_CSV = "csv";
	private final String Extension_XML = "xml";
	private final String Extension_JSON = "json";

	public void readFileByFormat(List<String> inputData, String filePath) throws Exception {
		String format = inputData.get(1);
		/* jdk 1.8 */
		switch (format) {
		case Extension_CSV:
			CsvFileUtil csv = new CsvFileUtil();
			// 讀取CSV內容並逐筆寫入資料庫(ADULT_HEALTH)
			csv.readAndInsert(filePath, Extension_CSV);

			// 從資料庫讀取寫入的資料
			List<AdultHealthBean> dataCsv = queryAdultHealth(Extension_CSV);

			// 輸出新的csv
			csv.createCsv(dataCsv);
			break;
		case Extension_XML:
			XmlFileUtil xml = new XmlFileUtil();
			// 讀取XML Node並逐筆寫入資料庫(ADULT_HEALTH)
			xml.readAndInsert(filePath, Extension_XML);

			// 從資料庫讀取寫入的資料
			List<AdultHealthBean> dataXml = queryAdultHealth(Extension_XML);

			// 輸出新的xml
			xml.createXml(dataXml);
			break;
		case Extension_JSON:
			JsonFileUtil json = new JsonFileUtil();
			// 讀取JSON內容並逐筆寫入資料庫
			json.readAndInsert(filePath, Extension_JSON);

			// 從資料庫讀取寫入的資料
			List<AdultHealthBean> dataJson = queryAdultHealth(Extension_JSON);

			// 輸出新的json
			json.creatJson(dataJson);
			break;
		default:
			throw new Exception("讀取URL失敗!! 請輸入正確的URL");
		}
	}

	public List<AdultHealthBean> queryAdultHealth(String fileType) throws ClassNotFoundException, SQLException {
		AdultHealthDao csvDao = DaoFactory.creatAdultHealthDao();
		List<AdultHealthBean> rsList = csvDao.queryData(fileType);
		System.out.println("資料是讀取 " + rsList.get(0).getFileType() + "類型檔案匯入資料庫，匯入時間為 " + rsList.get(0).getTime());
		for (AdultHealthBean obj : rsList) {
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("身高 " + obj.getPersonTall() + " 的人體重範圍在 " + obj.getLowerBound() + " 到 "
					+ obj.getUpperBound() + " 之間" + "，超過 " + obj.getFatBodyWeight() + " 公斤的人屬於肥胖" + "。");
		}
		return rsList;
	}

	// 解析輸入字串
	public List<String> pasringUrl()
			throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
		List<String> extention = new ArrayList<String>();
		String url = "";
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("請輸入資料url：");
			url = scan.nextLine();
			extention.add(url);
		}

		if (url.toLowerCase().contains("csv.php")) {
			extention.add(Extension_CSV);
		} else if (url.toLowerCase().contains("xml.php")) {
			extention.add(Extension_XML);
		} else if (url.toLowerCase().contains("json.php")) {
			extention.add(Extension_JSON);
		}

		return extention;
	}

	public static void main(String[] args) throws Exception {
		// 使用者輸入url
		SubjectMain subject = new SubjectMain();
		List<String> inputData = subject.pasringUrl();
		// List<String> inputData = new ArrayList<String>();
		/**
		 * csv:
		 * https://quality.data.gov.tw/dq_download_csv.php?nid=8492&md5_url=f08b32bf5dd2f3c34ac1d6ce522d0adb
		 * 
		 * json:
		 * https://quality.data.gov.tw/dq_download_json.php?nid=8492&md5_url=f08b32bf5dd2f3c34ac1d6ce522d0adb
		 * 
		 * xml:
		 * https://quality.data.gov.tw/dq_download_xml.php?nid=8492&md5_url=f08b32bf5dd2f3c34ac1d6ce522d0adb
		 */
		// inputData.add(
		// "https://quality.data.gov.tw/dq_download_json.php?nid=8492&md5_url=f08b32bf5dd2f3c34ac1d6ce522d0adb");
		// inputData.add("json");

		// 下載檔案
		DownloadUtil dwn = new DownloadUtil();
		String filePath = dwn.download(inputData);

		// 讀取檔案,寫入資料庫,新增檔案
		subject.readFileByFormat(inputData, filePath);
	}
}
