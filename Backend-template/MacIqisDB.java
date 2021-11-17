package db.macIqisDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MacIqis數據庫的連接工具類
 *@author C3005579
 *@date 2018年8月28日 上午9:03:30 
 */
public class MacIqisDB {
	private static String url = "jdbc:oracle:thin:@10.244.231.91:1521:xtwl";
	private static String user = "macIqis";
	private static String password = "macIqis.2018";
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  *  返回macIqis數據庫連接
	 * @return MacIqisDB connection
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}


