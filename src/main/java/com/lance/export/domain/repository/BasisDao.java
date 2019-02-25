package com.lance.export.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lance.export.domain.bean.basis.BasisBean;

@Repository
@SuppressWarnings("all") 
public interface BasisDao {

	//查询Basis 
	List findBasis(Map queryCondition); 

	//修改Basis 
	int updBasis(Map entity); 

	//删除Basis 
	int delBasis(String id); 

	//新增Basis 
	int addBasis(BasisBean entity); 
}
