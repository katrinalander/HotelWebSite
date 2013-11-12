package dbPackage;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionDB {
	private static Connection conn = null;
	private static ConnectionDB mySelf=null;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String dbName = "hotel";
	private static String username = "root", password = "1234";
	
	//method for connect to DB
	private ConnectionDB() throws ClassNotFoundException{
		Class.forName(driver);
		try {
			conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,username,password);
			System.out.println("CONNECT WITH DB");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't to connect to DB");
		}
	}

	public static ConnectionDB getInstance() throws Exception{
		if (mySelf==null){
			mySelf=new ConnectionDB();
		}//if
		return mySelf;
	}

	public Connection getConnection(){
		return conn;
	}
	
	//method for disconnect
	public static void disconnect() throws SQLException{
		conn.close();
	}
}
