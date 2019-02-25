package com.lance.export.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lance.export.common.Tools;
import com.lance.export.db.DBBasis;
import com.lance.export.service.DBService;
@Controller
@SuppressWarnings("all")
@RequestMapping(value="DB")
public class DBController {
	
	@Autowired
	DBService dbService;

	//获取指定DB的表
	@RequestMapping(value="findDBTable")
	@ResponseBody
	public List<Map> findAllWarehouseTables(HttpServletRequest request,HttpSession session) throws SQLException{
		Map parameter = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		return dbService.findDBTable(dbBasis);
	}
	
	//通过表名获取表的信息
	@RequestMapping(value="tableInfo")
	@ResponseBody
	public List<Map> tableInfo(HttpServletRequest request,@RequestBody String tableName,HttpSession session) throws SQLException{
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		Map tableNameMap = JSON.parseObject(tableName, Map.class);
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		return dbService.findDBTableColumInfo(dbBasis,tableNameMap);
	}
	
	@RequestMapping(value="findAllDB")
	@ResponseBody
	public List<String> findDB(HttpServletRequest request) throws ClassNotFoundException, SQLException{
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		List<String> list = dbService.jdbcFindDBTableColumInfo(map);
		return list;
	}
}
