package com.lance.export.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lance.export.common.Result;
import com.lance.export.common.Tools;
import com.lance.export.service.BasisService;
@SuppressWarnings("all")
@Controller
@RequestMapping(value="basis")
public class BasisController {

	@Autowired 
	BasisService basisService; 

	//新增Basis 
	@RequestMapping(value="addBasis") 
	@ResponseBody
	public Result addBasis(HttpServletRequest request) { 
		Result result= new Result(); 
		Map parameter = Tools.parameterMapToMap(request.getParameterMap()); 
		result = basisService.addBasis(parameter); 
		return result; 
	}; 

	//删除Basis 
	@RequestMapping(value="delBasis") 
	@ResponseBody
	public Result delBasis(HttpServletRequest request) { 
		Result result= new Result(); 
		Map parameter = Tools.parameterMapToMap(request.getParameterMap()); 
		result = basisService.delBasis(parameter); 
		return result; 
	}; 

	//修改Basis 
	@RequestMapping(value="updBasis")
	@ResponseBody
	public Result updBasis(HttpServletRequest request) throws Exception{ 
		Result result= new Result(); 
		Map parameter = Tools.parameterMapToMap(request.getParameterMap()); 
		result = basisService.updBasis(parameter); 
		return result; 
	}; 

	//查询Basis 
	@RequestMapping(value="findBasis") 
	@ResponseBody
	public Result findBasis(HttpServletRequest request) throws Exception { 
		Result result= new Result(); 
		Map parameter = Tools.parameterMapToMap(request.getParameterMap()); 
		result = basisService.findBasis(parameter); 
		return result; 
	}; 
}
