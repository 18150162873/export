package com.lance.export.domain.bean.other;

import java.util.Arrays;
import java.util.List;

public class CodeHelpBean {
	
	private String packagePath;
	
	private String tableName;
	
	private String name;
	
	private List<String> List = Arrays.asList("add","del","upd","find");
	
	private List<String> tableCloum;

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return List;
	}

	public void setList(List<String> list) {
		List = list;
	}

	public List<String> getTableCloum() {
		return tableCloum;
	}

	public void setTableCloum(List<String> tableCloum) {
		this.tableCloum = tableCloum;
	}
	
	
}
