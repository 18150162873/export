package com.lance.export.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lance.export.common.Result;
import com.lance.export.common.Tools;
import com.lance.export.domain.bean.basis.BasisBean;

@SuppressWarnings("all")
@Service
public class BasisService {
	
	private static Map basisMap = new ConcurrentHashMap();
	
	private synchronized BasisBean addBasis(BasisBean entity) {
		String id = Tools.getUUID();
		entity.setId(id);
		basisMap.put(id, entity);
		return entity;
	}
	
	private synchronized int delBasis(String id) {
		if(basisMap.containsKey(id)) {
			basisMap.remove(id);
		};
		return 1;
	}
	
	private List<Map> findALL() throws Exception {
		List<Map> resList = new LinkedList();
		Set<Entry<String, Object>> entrys = basisMap.entrySet();
		for(Entry ent:entrys) {
			resList.add(BeanUtils.describe(ent.getValue()));
		}
		return resList;
	}
	
	private int updBasis(BasisBean BasisBean) {
		if(basisMap.containsKey(BasisBean.getId())) {
			addBasis(BasisBean);
		}
		return 1;
	}
	
	//新增Basis 
	public Result addBasis(Map parameter) { 
		Result result= new Result(); 
		BasisBean entity = JSON.parseObject(parameter.get("data")+"", BasisBean.class); 
		if(!Tools.isNotBlank(entity.getDefaultDb())) {
			entity.setDefaultDb("information_schema");
		}
		entity.setCreaterTime(Tools.currentDatetime());
		entity = addBasis(entity); 
		result.setData(entity); 
		return result; 
	}; 


	//修改Basis 
	public Result updBasis(Map parameter) throws Exception{ 
		Result result = new Result(); 
		BasisBean entity = JSON.parseObject(parameter.get("data")+"", BasisBean.class); 

		if(Tools.isNotBlank(entity.getId()+"")) { 
			updBasis(entity);
		}else { 
			result.setStatus("500"); 
			result.setNote("id不能为空");; 
		} 
		result.setData(basisMap.get(entity.getId())); 
		return result; 
	}; 


	//查询Basis 
	public Result findBasis(Map map) throws Exception{ 
		Result result = new Result(); 
		List list = findALL();; 
		result.setData(list); 
		return result; 
	}; 


	//删除Basis 
	public Result delBasis(Map parameter) { 
		Result result = new Result(); 
		List <String> ids = JSON.parseArray(parameter.get("data")+"",String.class);
		List total = new ArrayList(); 
		for(String id:ids) { 
			delBasis(id);	
		} 
		return result; 
	}; 
}
