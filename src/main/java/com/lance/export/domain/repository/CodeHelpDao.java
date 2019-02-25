package com.lance.export.domain.repository;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface CodeHelpDao {
	
	//
	List<String> findColumNameByTableName(String tableName);
	
	//获取表的信息
	List<Map> findTableInfoByTableName(Map map);
	
	//获取所有指定库的表名
	List<Map> findAllWarehouseTables();
}
