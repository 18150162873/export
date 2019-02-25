package com.lance.export.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.lance.export.common.Tools;

@SuppressWarnings("all")
@Controller
@RequestMapping(value="page")
public class PageController {
	
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request,HttpSession session) {
		Map map = Tools.parameterMapToMap(request.getParameterMap());
		JSONObject job = new JSONObject();
		job.put("ip", request.getParameter("ip"));
		job.put("port", request.getParameter("port"));
		job.put("defaultDb", request.getParameter("db"));
		job.put("user", request.getParameter("user"));
		job.put("password", request.getParameter("password"));
		session.setAttribute("DBInfo", job.toJSONString());
		return "index";
	}
	
	@RequestMapping(value="basis")
	public String basis() {
		return "basis/basis";
	}
	
//	@RequestMapping(value="findAllTable")
//	@ResponseBody
//	public List findData(HttpServletResponse response) {
//		List list = exportDao.findAllWarehouseTables();
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		return list;
//	}
//	
//	@RequestMapping(value="findTableInfo")
//	@ResponseBody
//	public List findTableInfo() {
//		Map map = new HashMap();
//		map.put("tableName", "mws_account");
//		return  exportDao.findTableInfo(map);
//	}
//	
//	
//	@RequestMapping(value="tableData")
//	@ResponseBody
//	public Map tableData() {
//		Map map = new HashMap();
//		map.put("id","1" );
//		map.put("name", "lance");
//		map.put("price", "1000");
//		return map;
//	}
}
