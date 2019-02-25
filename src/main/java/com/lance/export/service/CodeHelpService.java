//package com.lance.export.service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.Namespace;
//import org.dom4j.QName;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.lance.export.common.Tools;
//import com.lance.export.domain.bean.other.CodeHelpBean;
//import com.lance.export.domain.repository.CodeHelpDao;
//
//@SuppressWarnings("all")
//@Service
//public class CodeHelpService {
//	
//	@Autowired
//	CodeHelpDao codeHelpDao;
//	
//	
//	//生成代码
//	public List<File> createCode(Map map) throws Exception {
//		CodeHelpBean bean = new CodeHelpBean();
//		bean.setName(map.get("name")+"");
//		bean.setTableName(map.get("tableName")+"");
//		bean.setPackagePath(map.get("path")+"");
//		
//		List<String> tableNameList = codeHelpDao.findColumNameByTableName(bean.getTableName());
//		bean.setTableCloum(tableNameList);
//		
//		File xmlFile = createMybatisXML(bean);
//		File mapperFile =createMybatisMapperProxy(bean);
//		File beanFile =createBean(bean);
//		File serviceFile = createService(bean);
//		File controllerFile =createController(bean);
//		
//		return Arrays.asList(xmlFile,mapperFile,beanFile,serviceFile,controllerFile);
//	}
//	
//	//
//	private File createMybatisXML(CodeHelpBean bean) throws Exception {
//		File file = new File(bean.getName()+"Dao.xml"); 
//		Document document = DocumentHelper.createDocument();
//		document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd",null);
//		Element mapper= document.addElement("mapper");
//		document.setRootElement(mapper);
//		document.getRootElement().addAttribute(QName.get("space", Namespace.XML_NAMESPACE),  "preserve");
//		mapper.addAttribute("namespace", bean.getPackagePath()+"."+bean.getName()+"Dao");
//		List<String> methodList = bean.getList();
//		createSelectMapper(mapper, bean);
//		createDeleteMapper(mapper, bean);
//		createUpdateMapper(mapper, bean);
//		createInsertMapper(mapper, bean);
//
//		OutputFormat outputFormat = OutputFormat.createPrettyPrint();  
//		outputFormat.setEncoding("UTF-8");
//		outputFormat.setIndent(true); 
//		outputFormat.setIndent("    ");
//		outputFormat.setNewlines(true);
//
//		StringWriter stringWriter = new StringWriter();  
//		XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);  
//		xmlWriter.setEscapeText(false);
//		xmlWriter.write(document);
//		System.out.println(stringWriter.toString());
//		
//		StringToFile(file,stringWriter.toString());
//		return file;
//	}
//	
//	//
//	private void createSelectMapper(Element element,CodeHelpBean bean) {
//		Element selectAll = element.addElement("select");
//		selectAll.addAttribute("id", "find"+bean.getName());
//		selectAll.addAttribute("parameterType", "java.util.Map");
//		selectAll.addAttribute("resultType", bean.getPackagePath()+"."+bean.getName());
//		createSelectSql(selectAll,bean);
//	}
//	
//	//
//	private void createSelectSql(Element select,CodeHelpBean bean) {
//		StringBuffer sb = new StringBuffer();
//		List<String> tableColum = bean.getTableCloum();
//		
//		List<String> rowList = new ArrayList();
//		for(String str:tableColum) {
//			rowList.add(str+" as "+Tools.camelCaseForMate(str));
//		}
//		String selectStr = "\n	select\n	"+ rowList.stream().collect(Collectors.joining(",\n	"))+"\n	from "+bean.getTableName();
//		sb.append(selectStr);
//		select.addText(selectStr);
//		select.addText(" where 1=1 ");
//		for(String s:tableColum) {
//			Element ifElement = select.addElement("if");
//			ifElement.addAttribute("test", Tools.camelCaseForMate(s)+" != null");
//			ifElement.addText("\n	and "+s+ " =#{"+Tools.camelCaseForMate(s)+"}\n    ");
//		}
//	}
//	
//	//
//	private void createDeleteMapper(Element element,CodeHelpBean bean) {
//		Element delete = element.addElement("delete");
//		delete.addAttribute("id","del"+bean.getName());
//		delete.addAttribute("parameterType", "java.lang.String");
//		delete.setText("\n	delete from " +bean.getTableName()+" where id =#{id}\n");
//	}
//	
//	//
//	private void createUpdateMapper(Element mapper,CodeHelpBean bean) {
//		Element update = mapper.addElement("update");
//		update.addAttribute("id","upd"+bean.getName());
//		update.addAttribute("parameterType", bean.getPackagePath()+"."+bean.getName());
//		createUpdateSql( update, bean);
//	}
//	
//	//
//	private void createUpdateSql(Element update,CodeHelpBean bean) {
//		StringBuffer sb = new StringBuffer();
//		update.addText("update " +bean.getTableName());
//		Element set = update.addElement("set");
//		List<String> tableCloum = bean.getTableCloum();
//		for(String str:tableCloum) {
//			Element ifElement = set.addElement("if");
//			ifElement.addAttribute("test", Tools.camelCaseForMate(str) +" != null");
//			ifElement.addText("\n		 "+str+" = #{" +Tools.camelCaseForMate(str)+"},\n	 ");
//		}
//		update.setText("\n	where id = #{id}");
//	}
//	
//	//
//	private void createInsertMapper(Element mapper,CodeHelpBean bean) {
//		Element insert = mapper.addElement("insert");
//		insert.addAttribute("id", "add"+ bean.getName());
//		insert.addAttribute("parameterType", bean.getPackagePath()+"."+bean.getName());
//		Element selectKey = insert.addElement("selectKey");
//		selectKey.addAttribute("keyProperty", "id");
//		selectKey.addAttribute("order", "AFTER");
//		selectKey.addAttribute("resultType", "java.lang.Integer");
//		selectKey.addText("\n		SELECT LAST_INSERT_ID()\n	");
//		insert.addText(createInserSql(bean));
//	}
//	
//	//
//	private String createInserSql(CodeHelpBean bean) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n	"+"insert into "+ bean.getTableName()+ "(");
//		sb.append("\n	"+bean.getTableCloum().stream().collect(Collectors.joining(",\n	")));
//		sb.append(")\n	value(");
//		sb.append("\n	"+bean.getTableCloum().stream().map(str->"	#{"+Tools.camelCaseForMate(str)+"}").collect(Collectors.joining(",\n")));
//		sb.append(")");
//		return sb.toString();
//	}
//	
//	private File createMybatisMapperProxy(CodeHelpBean bean) throws Exception {
//		File file = new File(bean.getName()+"Dao.java");
//		StringBuffer sb = new StringBuffer();
//		sb.append("package "+bean.getPackagePath()+".domain.repository;\r\n" + 
//				"\r\n" + 
//				"import java.util.List;\r\n" + 
//				"import java.util.Map;\r\n" + 
//				"\r\n" + 
//				"@SuppressWarnings(\"all\")\r\n" + 
//				"public interface "+bean.getName()+"Dao {\r\n" + 
//				"	\r\n" + 
//				"	//新增"+bean.getName()+"记录\r\n" + 
//				"	int add"+bean.getName()+"(M2 m2);\r\n" + 
//				"	//删除"+bean.getName()+"\r\n" + 
//				"	int del"+bean.getName()+"(String id);\r\n" + 
//				"	//修改"+bean.getName()+"\r\n" + 
//				"	int upd"+bean.getName()+"(M2 m2);\r\n" + 
//				"	//查询"+bean.getName()+"\r\n" + 
//				"	List<M2> find"+bean.getName()+"(Map map);\r\n" + 
//				"	\r\n" + 
//				"	\r\n" + 
//				"}");
//		StringToFile(file,sb.toString());
//		return file;
//	}
//	
//	//
//	private void StringToFile(File file,String str) throws Exception {
//		String MapperProxy = str.toString();
//		byte[] bytes = MapperProxy.getBytes();
//		OutputStream out = null;
//		try {
//			out =new FileOutputStream(file);
//			out.write(bytes);
//		} finally {
//			out.close();
//		}
//	}
//	
//	private File createBean(CodeHelpBean bean ) throws Exception {
//		File file = new File(bean.getName()+".java");
//		StringBuffer sb = new StringBuffer();
//		List<String> tableCloum = bean.getTableCloum();
//		sb.append("package "+bean.getPackagePath()+".domain.bean;\r\n" + 
//				"\r\n" + 
//				"public class CodeHelpBean {\r\n"); 
//		for(String str:tableCloum) {
//			sb.append("	private String "+Tools.camelCaseForMate(str)+";\n\n");
//		}		
//		sb.append("	\r\n" + 
//				"}");
//		StringToFile(file,sb.toString());
//		return file;
//	}
//	
////	//
////	private String getMethodName(String fieldName) {
////		String methodName = "";
////		if(fieldName.length() >= 2) {
////			Character secondCharacter = new Character(fieldName.charAt(1)); 
////			if(Character.isUpperCase(secondCharacter)) {
////				methodName = "set"+fieldName.substring(0, 1)+fieldName.substring(1, fieldName.length());
////			}else {
////				methodName = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
////			}
////		}
////		return methodName;
////	}
////	
////	//
////	private String setMethodName(String fieldName) {
////		String methodName = "";
////		if(fieldName.length() >= 2) {
////			Character secondCharacter = new Character(fieldName.charAt(1)); 
////			if(Character.isUpperCase(secondCharacter)) {
////				methodName = "get"+fieldName.substring(0, 1)+fieldName.substring(1, fieldName.length());
////			}else {
////				methodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
////			}
////		}
////		return methodName;
////	}
//	
//	//
//	private File createService(CodeHelpBean bean) throws Exception {
//		File file = new File(bean.getName()+"Service.java");
//		StringBuffer sb = new StringBuffer();
//		sb.append("package "+bean.getPackagePath()+".service;\r\n" + 
//				"\r\n" + 
//				"import java.util.ArrayList;\r\n" + 
//				"import java.util.HashMap;\r\n" + 
//				"import java.util.List;\r\n" + 
//				"import java.util.Map;\r\n" + 
//				"\r\n" + 
//				"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
//				"import org.springframework.stereotype.Service;\r\n" + 
//				"\r\n" + 
//				"\r\n" + 
//				"@SuppressWarnings(\"all\")\r\n" + 
//				"@Service\r\n" + 
//				"public class "+bean.getName()+"Service"+"Service {\r\n" + 
//				"	@Autowired\r\n" + 
//				"	"+bean.getName()+"Dao "+bean.getName()+"Dao;\r\n" + 
//				"\r\n" + 
//				"	@Autowired\r\n" + 
//				"	TokenService tokenService;\r\n" + 
//				"\r\n" + 
//				"	//查询"+bean.getName()+"\r\n" + 
//				"	public Result find"+bean.getName()+"(Map map){\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		List<"+bean.getName()+"> list = "+bean.getName()+"Dao.find"+bean.getName()+"(map);\r\n" + 
//				"		result.setData(list);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//新增"+bean.getName()+"\r\n" + 
//				"	public Result add"+bean.getName()+"("+bean.getName()+" entity,Map parameter) {\r\n" + 
//				"		Result result= new Result();\r\n" + 
//				"		String token = parameter.get(\"token\")+\"\";\r\n" + 
//				"		User user = tokenService.getUser(token);\r\n" + 
//				"		entity.setCreator(user.getUsername());\r\n" + 
//				"		entity.setCreaterTime(Tools.currentDatetime());\r\n" + 
//				"\r\n" + 
//				"		"+bean.getName()+"Dao.add"+bean.getName()+"(entity);\r\n" + 
//				"		result.setData(entity);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//修改"+bean.getName()+"\r\n" + 
//				"	public Result upd"+bean.getName()+"("+bean.getName()+" entity,Map parameter) {\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		String token = parameter.get(\"token\")+\"\";\r\n" + 
//				"		User user = tokenService.getUser(token);\r\n" + 
//				"		\r\n" + 
//				"		if(Tools.isNotBlank(entity.getId()+\"\")) {\r\n" + 
//				"			entity.setModifier(user.getUsername());\r\n" + 
//				"			entity.setModifyTime(Tools.currentDatetime());\r\n" + 
//				"			"+bean.getName()+"Dao.upd"+bean.getName()+"(entity);\r\n" + 
//				"		}else {\r\n" + 
//				"			result.setStatus(\"500\");\r\n" + 
//				"			result.setNote(\"请检查数据Id是否完整\");;\r\n" + 
//				"		}\r\n" + 
//				"		result.setData(entity);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//删除"+bean.getName()+"\r\n" + 
//				"	public Result del"+bean.getName()+"(List<String> ids) {\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		Map queryCondition = new HashMap();\r\n" + 
//				"		List total = new ArrayList();\r\n" + 
//				"		for(String id:ids) {\r\n" + 
//				"			queryCondition.put(\"id\", id);\r\n" + 
//				"			List<"+bean.getName()+"> list = "+bean.getName()+"Dao.find"+bean.getName()+"(queryCondition);\r\n" + 
//				"			if(list.size() > 0) {\r\n" + 
//				"				"+bean.getName()+"Dao.del"+bean.getName()+"(id);\r\n" + 
//				"				total.add(list.get(0));\r\n" + 
//				"			}	\r\n" + 
//				"		}\r\n" + 
//				"		result.setData(total);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"}\r\n" + 
//				"");
//		StringToFile(file,sb.toString());
//		return file;
//	}
//	
//	//
//	private File createController(CodeHelpBean bean) throws Exception {
//		File file = new File(bean.getName()+"Controller.java");
//		StringBuffer sb = new StringBuffer();
//		sb.append("package "+bean.getPackagePath()+".controller;\r\n" + 
//				"\r\n" + 
//				"import java.util.HashMap;\r\n" + 
//				"import java.util.List;\r\n" + 
//				"import java.util.Map;\r\n" + 
//				"\r\n" + 
//				"import javax.servlet.http.HttpServletRequest;\r\n" + 
//				"\r\n" + 
//				"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
//				"import org.springframework.stereotype.Controller;\r\n" + 
//				"import org.springframework.web.bind.annotation.RequestMapping;\r\n" + 
//				"\r\n" + 
//				"import com.alibaba.fastjson.JSON;\r\n" + 
//				"import com.infiai.webcommon.Result;\r\n" + 
//				"import com.infiai.webcommon.Tools;\r\n" + 
//				"import com.infiai.webcommon.domain.User;\r\n" + 
//				"\r\n" + 
//				"@SuppressWarnings(\"all\")\r\n" + 
//				"@Controller\r\n" + 
//				"@RequestMapping(value=\""+bean.getName()+"\")\r\n" + 
//				"public class "+bean.getName()+"Controller {\r\n" + 
//				"	@Autowired\r\n" + 
//				"	"+bean.getName()+"Service "+bean.getName()+"Service;\r\n" + 
//				"\r\n" + 
//				"	//查询"+bean.getName()+"\r\n" + 
//				"	@RequestMapping(value=\"find"+bean.getName()+"\")\r\n" + 
//				"	public Result find"+bean.getName()+"(HttpServletRequest request){\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		Map map = Tools.parameterMapToMap(request.getParameterMap());\r\n" + 
//				"		result = "+bean.getName()+"Service.find"+bean.getName()+"(map);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//新增"+bean.getName()+"\r\n" + 
//				"	@RequestMapping(value=\"add"+bean.getName()+"\")\r\n" + 
//				"	public Result add"+bean.getName()+"(HttpServletRequest request) {\r\n" + 
//				"		Result result= new Result();\r\n" + 
//				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r\n" + 
//				"		"+bean.getName()+" entity = JSON.parseObject(parameter.get(\"data\")+\"\", "+bean.getName()+".class);\r\n" + 
//				"		result = "+bean.getName()+"Service.add"+bean.getName()+"(entity,parameter);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//修改"+bean.getName()+"\r\n" + 
//				"	@RequestMapping(value=\"upd"+bean.getName()+"\")\r\n" + 
//				"	public Result upd"+bean.getName()+"(HttpServletRequest request) {\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r\n" + 
//				"		"+bean.getName()+" entity = JSON.parseObject(parameter.get(\"data\")+\"\", "+bean.getName()+".class);\r\n" + 
//				"		result = "+bean.getName()+"Service.upd"+bean.getName()+"(entity,parameter);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"	//删除"+bean.getName()+"\r\n" + 
//				"	@RequestMapping(value=\"del"+bean.getName()+"\")\r\n" + 
//				"	public Result del"+bean.getName()+"(HttpServletRequest request) {\r\n" + 
//				"		Result result = new Result();\r\n" + 
//				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r\n" + 
//				"		List<String> ids = JSON.parseArray(parameter.get(\"ids\")+\"\", String.class);\r\n" + 
//				"		result = "+bean.getName()+"Service.del"+bean.getName()+"(ids);\r\n" + 
//				"		return result;\r\n" + 
//				"	};\r\n" + 
//				"\r\n" + 
//				"}\r\n" + 
//				"");
//				StringToFile(file,sb.toString());
//		return file;
//	}
//}
