package subject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DownloadUtil {

	public String download(List<String> inputData) throws IOException {
		int byteread = 0;
		URL url = new URL(inputData.get(0));
		String fileName = "subject." + inputData.get(1);
		URLConnection conn = null;
		InputStream inStream = null;
		FileOutputStream fs = null;
		
		String dwnPath = Property.DOWNLOAD_FOLDER_PATH;
		
		// 確認資料夾是否存在
		if (!new File(dwnPath).exists()) {
			new File(dwnPath).mkdirs();
		}

		String filePath = dwnPath + fileName;
		try {
			conn = url.openConnection();
			inStream = conn.getInputStream();
			fs = new FileOutputStream(filePath);
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fs) {
				fs.close();
			}

			if (null != inStream) {
				inStream.close();
			}
		}
		return filePath;
	}

}
