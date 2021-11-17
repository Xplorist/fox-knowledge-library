package util.config;

import java.util.ResourceBundle;

public class ConfigReader {
	public static String getPropertyByPath(String configPath, String property) {
		String result = ResourceBundle.getBundle(configPath).getString(property);

		return result;
	}

	public static ResourceBundle getProperties(String configPath) {
		ResourceBundle result = ResourceBundle.getBundle(configPath);

		return result;
	}
}
