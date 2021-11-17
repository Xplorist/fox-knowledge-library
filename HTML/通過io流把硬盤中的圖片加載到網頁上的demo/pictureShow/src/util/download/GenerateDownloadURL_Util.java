package util.download;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

/**
 *	生成下載url工具類
 * @author C3005579
 * @date 2018年12月26日 上午11:17:08 
 */
public class GenerateDownloadURL_Util {
public static Map<String, String> FILE_URL_FIELDS = new HashMap<String, String>();
	
	//url屬性對應的 文件名稱屬性
	public static Map<String,String> URL_NAME_MAP = new HashMap<String,String>();
	
	public static String SERVLET_PATH = "servlet/download/?key=";
	
	static {
		FILE_URL_FIELDS.put("io.xicp.xplorist.model.NewMouldMainBean", "pic_file_path");//MacI新模治具圖檔圖片上傳  C3005579
		
		//URL屬性對應的名稱屬性
		URL_NAME_MAP.put("url", "fileObjectName");//FileObjectDTO
	}
	
	public static void generateURL(Object dto, HttpSession session) {
		String url = null;
		String fieldNameStr = FILE_URL_FIELDS.get(dto.getClass().getCanonicalName());
		if (fieldNameStr != null) {
			String[] fieldNames = fieldNameStr.split(",");
			for (String fieldName : fieldNames)
			{
				try {
					String realUrl = BeanUtils.getProperty(dto, fieldName);
					String key = UUID.randomUUID().toString().replaceAll("-", "");
					url = SERVLET_PATH + key;
					session.setAttribute(key, realUrl);
					BeanUtils.setProperty(dto, fieldName, url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else 
		{
			throw new RuntimeException("DTO的文檔屬性未設置");
		}
	}
	
	public static Object setDtoUrlField(Object obj, HttpSession session) {
		if (obj != null) {
			if (obj instanceof Collection) {
				for (Object item : (Collection<?>)obj)
					generateURL(item, session);
			} else {
				generateURL(obj, session);
			}
		}

		return obj;
	}
}


