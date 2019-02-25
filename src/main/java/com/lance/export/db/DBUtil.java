package com.lance.export.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.lance.export.common.Tools;



@SuppressWarnings("all")
public class DBUtil {

	
	//获取jdbc连接
	public static Connection getJdbcConnection(Map basis) throws ClassNotFoundException, SQLException {
		String db = "information_schema";
		if(Tools.isNotBlank(basis.get("db")+"")) {
			db = basis.get("db")+"";
		}
		String url = "jdbc:mysql://"+basis.get("ip")+":"+basis.get("port")+"/"+db;
		String user = basis.get("user")+"";
		String password = basis.get("password")+"";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
	
	//获取jdbc连接
	public static Connection getJdbcConnection(DBBasis basis) throws ClassNotFoundException, SQLException {
		String db = "information_schema";
		if(Tools.isNotBlank(basis.getDefaultDb())) {
			db = basis.getDefaultDb();
		}
		
		String url = "jdbc:mysql://"+basis.getIp()+":"+basis.getPort()+"/"+db;
		String user = basis.getUser();
		String password = basis.getPassword();
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
