package com.lance.export.domain.bean.constructComponent;

import com.lance.export.common.Tools;

@SuppressWarnings("all")
public class CodeConstruct {

	private String tableName;

	public CodeConstruct(String tableName) {
		super();
		this.tableName = tableName;
	}

	public String getBeanName() {
		return Tools.camelCaseForMate(tableName).substring(0, 1).toUpperCase()+Tools.camelCaseForMate(tableName).substring(1);
	}
	
	public String getServiceName() {
		return Tools.camelCaseForMate(tableName)+"Service";
	}
	
	public String getServiceClassName() {
		return getBeanName()+"Service";
	}
	
	public String getServiceMethodName(String Type) {
		return Type+getBeanName();
	}
	
	public String getControllerMethodName(String Type) {
		return Type+getBeanName();
	}
	
	public String getControllerName() {
		return  getBeanName()+"Controller";
	}
	
	public String getControllerMappingValue() {
		return Tools.camelCaseForMate(tableName);
	}
	
	public String getControllerMappingMethodValue(String type) {
		return type+getBeanName();
	}
	
	public String getMybatisDaoInterfaceName() {
		return getBeanName()+"Dao";
	}
	
	public String getMybatisDaoFieldName() {
		return Tools.camelCaseForMate(tableName)+"Dao";
	}
	
	public String getMybatisMethodName(String type) {
		return type+getBeanName();
	}
	
	public String getMethodName(String fieldName) {
		String methodName = "";
		if(fieldName.length() >= 2) {
			Character secondCharacter = new Character(fieldName.charAt(1)); 
			if(Character.isUpperCase(secondCharacter)) {
				methodName = "get"+fieldName.substring(0, 1)+fieldName.substring(1, fieldName.length());
			}else {
				methodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
			}
		}
		return methodName;
	}
	
	public String setMethodName(String fieldName) {
		String methodName = "";
		if(fieldName.length() >= 2) {
			Character secondCharacter = new Character(fieldName.charAt(1)); 
			if(Character.isUpperCase(secondCharacter)) {
				methodName = "set"+fieldName.substring(0, 1)+fieldName.substring(1, fieldName.length());
			}else {
				methodName = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
			}
		}
		return methodName;
	}
}
