package com.lance.export.domain.repository;

import java.util.List;
import java.util.Map;

public interface ExportDao {
	List<Map> findAllWarehouseTables();
	
	List<Map> findTableInfo(Map parameter);
}
