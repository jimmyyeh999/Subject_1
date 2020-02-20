package subject.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import subject.bean.AdultHealthBean;
import subject.dao.AdultHealthDao;
import subject.dao.DaoFactory;
import subject.dao.FileImportDao;

public class XmlFileUtil {

	public void readAndInsert(String filePath, String fileType)
			throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, SQLException {
		// 解析xml
		File xmlFile = new File(filePath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		NodeList nl = doc.getElementsByTagName("row");
		for (int i = 0; i < nl.getLength(); i++) {
			int tall = Integer.parseInt(doc.getElementsByTagName("Col1").item(i).getFirstChild().getNodeValue());
			float lowerB = Float.parseFloat(doc.getElementsByTagName("Col2").item(i).getFirstChild().getNodeValue());
			float upperB = Float.parseFloat(doc.getElementsByTagName("Col3").item(i).getFirstChild().getNodeValue());
			String fatWeight = doc.getElementsByTagName("Col4").item(i).getFirstChild().getNodeValue();

			AdultHealthBean bean = new AdultHealthBean();
			bean.setPersonTall(tall);
			bean.setLowerBound(lowerB);
			bean.setUpperBound(upperB);
			bean.setFatBodyWeight(fatWeight);
			bean.setFileType(fileType);

			AdultHealthDao ahDao = DaoFactory.creatAdultHealthDao();
			ahDao.insert(bean);
		}

		FileImportDao fileImp = DaoFactory.creatFileImportDao();
		fileImp.updateStatus(fileType);
	}

	public void createXml(List<AdultHealthBean> data) throws IOException {
		// 依據Dom4j套件建立xml物件
		org.dom4j.Document document = DocumentHelper.createDocument();
		Element table = document.addElement("table");
		Element row = table.addElement("row");
		for (AdultHealthBean obj : data) {
			Element tall = row.addElement("Col1");
			tall.addAttribute("name", "身高別(cm)");
			tall.addText(String.valueOf(obj.getPersonTall()));
			Element lowerBound = row.addElement("Col2");
			lowerBound.addAttribute("name", "正常體重範圍下限(kg)");
			lowerBound.addText(Float.toString(obj.getLowerBound()));
			Element upperBound = row.addElement("Col3");
			upperBound.addAttribute("name", "正常體重範圍上限(kg)");
			upperBound.addText(Float.toString(obj.getUpperBound()));
			Element fatBody = row.addElement("Col4");
			fatBody.addAttribute("name", "肥胖值(kg)");
			fatBody.addText(obj.getFatBodyWeight());
			Element createTime = row.addElement("Col5");
			createTime.addAttribute("name", "匯入資料庫時間(yyyy-mm-dd hh:mm:ss)");
			createTime.addText(obj.getTime().toString());
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		File xml = new File("C:\\Users\\Student\\Download\\subject_New.xml");
		XMLWriter output = new XMLWriter(new FileWriter(xml));
		output.write(document);
		output.close();
	}
}
