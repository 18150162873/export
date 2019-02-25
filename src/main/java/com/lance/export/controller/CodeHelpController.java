package com.lance.export.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lance.export.common.Tools;
import com.lance.export.db.DBBasis;
import com.lance.export.service.CodeFactory;
import com.lance.export.service.DBService;
@RequestMapping(value="codeHelp")
@SuppressWarnings("all")
@Controller
public class CodeHelpController {

//	@Autowired
//	CodeHelpService codeHelpService;
	
	@Autowired
	DBService dbService;
	
	@Autowired
	CodeFactory codeFactory;
	
//	@RequestMapping(value="download")
//	public void codeHelp(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		Map parameter = Tools.parameterMapToMap(request.getParameterMap());
//		List<File> list = codeHelpService.createCode(parameter);
//		
//		response.setContentType("APPLICATION/OCTET-STREAM");  
//    	response.setHeader("Content-Disposition","attachment; filename="+"package.zip");
//    	ZipOutputStream out = null;
//    	try {
//    		out = new ZipOutputStream(response.getOutputStream());
//    		for(File file:list) {
//    			ZipUtils.doCompress(file, out);
//    			response.flushBuffer();
//    		}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//    	finally {
//			out.close();
//		}
//	}
	
	@RequestMapping(value="createSelect")
	@ResponseBody
	public String createSelect(HttpServletRequest request,HttpSession session) throws SQLException {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		List<Map> tableInfo = dbService.findDBTableColumInfo(dbBasis,map);
		String string = codeFactory.createSelectCode(tableInfo, map.get("tableName")+"");
		return string;
	}
	
	@RequestMapping(value="createInsert")
	@ResponseBody
	public String createInsert(HttpServletRequest request,HttpSession session) throws SQLException {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		List<Map> tableInfo = dbService.findDBTableColumInfo(dbBasis,map);
		String string = codeFactory.createInsert(tableInfo, map.get("tableName")+"");
		return string;
	}
	
	@RequestMapping(value="updateSql")
	@ResponseBody
	public String createUpdateSql(HttpServletRequest request,HttpSession session) throws SQLException {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		List<Map> tableInfo = dbService.findDBTableColumInfo(dbBasis,map);
		String string = codeFactory.createUpdateSql(tableInfo, map.get("tableName")+"");
		return string;
	}
	
	@RequestMapping(value="delSql")
	@ResponseBody
	public String createDelSql(HttpServletRequest request,HttpSession session) throws SQLException {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		List<Map> tableInfo = dbService.findDBTableColumInfo(dbBasis,map);
		String string = codeFactory.createDelSql(tableInfo, map.get("tableName")+"");
		return string;
	}
	
	//controller新增
	@RequestMapping(value="createControllerCodeAdd")
	@ResponseBody
	public String createControllerCode(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.createControllerAdd(map.get("tableName")+"");
		return string;
	}
	
	//controller删除
	@RequestMapping(value="createControllerdel")
	@ResponseBody
	public String createControllerdel(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.createControllerdel(map.get("tableName")+"");
		return string;
	}
	
	//controller修改
	@RequestMapping(value="createControllerUpd")
	@ResponseBody
	public String createControllerUpd(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.createControllerUpd(map.get("tableName")+"");
		return string;
	}
	
	//controller查询
	@RequestMapping(value="createControllerFind")
	@ResponseBody
	public String createControllerFind(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.createControllerFind(map.get("tableName")+"");
		return string;
	}
	
	//controller查询
	@RequestMapping(value="controller")
	@ResponseBody
	public String controller(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.createController(map.get("tableName")+"");
		return string;
	}
	
	//service新增
	@RequestMapping(value="serviceAdd")
	@ResponseBody
	public String serviceAdd(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.serviceAdd(map.get("tableName")+"");
		return string;
	}
	
	//service删除
	@RequestMapping(value="serviceDel")
	@ResponseBody
	public String serviceDel(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.servicedel(map.get("tableName")+"");
		return string;
	}
	
	//service修改
	@RequestMapping(value="serviceUpd")
	@ResponseBody
	public String serviceUpd(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.serviceupd(map.get("tableName")+"");
		return string;
	}
	
	//service查询
	@RequestMapping(value="serviceFind")
	@ResponseBody
	public String serviceFind(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.servicefind(map);
		return string;
	}
	
	//service
	@RequestMapping(value="service")
	@ResponseBody
	public String service(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.service(map);
		return string;
	}
	
	//mybatisInterface
	@RequestMapping(value="mybatisInterface")
	@ResponseBody
	public String mybatisInterface(HttpServletRequest request) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		String string = codeFactory.mybatisInterface(map);
		return string;
	}
	
	//createBean
	@RequestMapping(value="createBean")
	@ResponseBody
	public String createBean(HttpServletRequest request,HttpSession session) throws SQLException {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		DBBasis dbBasis = JSON.parseObject(session.getAttribute("DBInfo")+"", DBBasis.class);
		List<Map> tableInfo = dbService.findDBTableColumInfo(dbBasis,map);
		String string = codeFactory.createBean(tableInfo, map.get("tableName")+"");
		return string;
	}
}
