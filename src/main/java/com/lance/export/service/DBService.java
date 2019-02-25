package com.lance.export.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lance.export.db.DBBasis;
import com.lance.export.db.DBUtil;
@SuppressWarnings("all")
@Service
public class DBService {
	
	//获取指定表的信息
	public List findDBTableColumInfo(DBBasis basis,Map parameter) throws SQLException {
		List<Map> resultList = new ArrayList();
		
		Connection conn = null;
		try {
			conn = DBUtil.getJdbcConnection(basis);
			
			String sql = "SELECT" + 
					"	column_name as column_name," + 
					"	column_type as column_type," + 
					"	column_comment as column_comment" + 
					"	FROM" + 
					"		information_schema.COLUMNS" + 
					"	WHERE" + 
					"		table_schema = ?" + 
					"	AND table_name = ?";
			PreparedStatement preState = conn.prepareStatement(sql);
			preState.setString(1, basis.getDefaultDb());
			preState.setString(2, parameter.get("tableName")+"");
			preState.executeQuery();
			ResultSet rs = preState.getResultSet();
			while(rs.next()) {
				Map resultMap = new HashMap();
				resultMap.put("column_name", rs.getString("column_name"));
				resultMap.put("column_type", rs.getString("column_type"));
				resultMap.put("column_comment", rs.getString("column_comment"));
				resultList.add(resultMap);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
				
		return resultList;
	};
	
	//获取指定DB的表名
	public List findDBTable(DBBasis basis) throws SQLException {
		List<Map> resList = new ArrayList();
		Connection conn = null;
		try {
			conn = DBUtil.getJdbcConnection(basis);
			String sql = "select table_name as tableName" + 
						 " from information_schema.tables" + 
						 " where table_schema=?";
			PreparedStatement prestate = conn.prepareStatement(sql);
			prestate.setString(1, basis.getDefaultDb());
			prestate.executeQuery();
			ResultSet rs = prestate.getResultSet();
			while(rs.next()) {
				Map map = new HashMap();
				map.put("tableName", rs.getString("tableName"));
				resList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return resList;
	}
	
	
	//获取所有的DB
	public List<String> jdbcFindDBTableColumInfo(Map map) throws ClassNotFoundException, SQLException{
		List<String> list = new ArrayList();
		Connection conn = null;
		try {
			DBBasis entity = JSON.parseObject(map.get("data")+"", DBBasis.class);
			conn = DBUtil.getJdbcConnection(entity);
			Statement stmt = conn.createStatement();
			String sql = "show DATABASES";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString("Database"));
			}
		} finally {
			conn.close();
		}
		return list;
	}
}
