package com.lance.export.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lance.export.db.DBBasis;
import com.lance.export.db.DBUtil;
@SuppressWarnings("all")
public class test {

	public static void main(String[] args) throws Exception {
//		dom4jTest();
		findData();
//		delete();
	}
	
	public static void dom4jTest() throws IOException {
		Document document = DocumentHelper.createDocument();
		document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd",null);
		Element mapper= document.addElement("mapper");
		Element selectAll = mapper.addElement("select");
		selectAll.addAttribute("id", "find");
		selectAll.addAttribute("parameterType", "java.util.Map");
		selectAll.addAttribute("resultType", "");
		selectAll.addText("sada");
		System.out.println(document.asXML());
	}
	
	
	public static void test() {
		System.out.println("asdadsad".substring(1));;
	}
	
	
	public static void findData() throws ClassNotFoundException, SQLException {
		DBBasis basis = new DBBasis();
		basis.setDefaultDb("web_ad");
		basis.setIp("119.23.236.73");
		basis.setPassword("shell_#infi123");
		basis.setUser("warehouse_shell");
		basis.setPort("3306");
		
		Connection conn = null;
		try {
			conn = DBUtil.getJdbcConnection(basis);
			
			String sql = "SELECT" + 
					"	*" + 
					"	FROM" + 
					"		web_ad.amazon_ad_report_account";
			PreparedStatement preState = conn.prepareStatement(sql);
			preState.executeQuery();
			ResultSet rs = preState.getResultSet();
			int i = 1;
			Map map = new HashMap();
			while(rs.next()) {
				i++;
//				System.out.println(i);
//				System.out.println("seller_id:"+rs.getString("seller_id")+"|marketplace_id:"+rs.getString("marketplace_id")+"|name:"+rs.getString("name")+"|refresh_token:"+rs.getString("refresh_token")+"|profile_id:"+rs.getString("profile_id"));;
				String uuid = rs.getString("seller_id")+rs.getString("marketplace_id");
				String id = rs.getString("id");
				if(map.containsKey(uuid)) {
					System.out.println(id);
				}
				map.put(uuid, id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}
	
	public static void delete() throws SQLException {
		DBBasis basis = new DBBasis();
		basis.setDefaultDb("web_ad");
		basis.setIp("119.23.236.73");
		basis.setPassword("shell_#infi123");
		basis.setUser("warehouse_shell");
		basis.setPort("3306");
		
		Connection conn = null;
		try {
			conn = DBUtil.getJdbcConnection(basis);
			String delsql = "delete" + 
					"	FROM" + 
					"		web_ad.amazon_ad_report_account"+
					"	where id >=24" ;
			PreparedStatement preState = conn.prepareStatement(delsql);
			preState.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}
}
