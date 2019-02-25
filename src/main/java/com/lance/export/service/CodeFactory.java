package com.lance.export.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lance.export.common.Tools;
import com.lance.export.domain.bean.constructComponent.CodeConstruct;

@SuppressWarnings("all")
@Service
public class CodeFactory {

	//查询sql
	public String createSelectCode(List<Map> tableInfo,String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		StringBuffer selectSb = new StringBuffer();
		String str = tableInfo.stream().map(entity->entity.get("column_name")+"").collect(Collectors.joining(","));
		List<String> list = Arrays.asList(str.split(","));
		List<String> select= new ArrayList();
		List<String> iflist= new ArrayList();
		for(String s:list) {
			StringBuffer sb =new StringBuffer();
			sb.append(s+" as "+Tools.camelCaseForMate(s));
			select.add(sb.toString());
		}
		String selectStr =  select.stream().collect(Collectors.joining(",<br/>"));
		selectSb.append("select<br/>");
		selectSb.append(selectStr);
		selectSb.append("<br/>from "+Tools.camelCaseForMate(tableName)+"<br/>");
		System.out.println("select<br/>"+selectStr+"<br/>from");
		
		StringBuffer ifsb =new StringBuffer();
		for(String s:list) {
			ifsb.append(" &lt;if test=\""+Tools.camelCaseForMate(s)+" != null\" &gt;\r<br/>" + 
					"       and "+s+" =#{"+Tools.camelCaseForMate(s)+"}\r<br/>" + 
					"&lt;/if&gt;<br/>");
		}
		selectSb.append("where <br/> 1=1 <br/>"+ifsb.toString());
		
		StringBuffer sb = new StringBuffer();
		sb.append("&lt;!--查询"+Tools.camelCaseForMate(tableName)+"--&gt;<br/>");
		sb.append(" &lt;select id=\""+construct.getMybatisMethodName("find")+"\" parameterType=\"Map\" resultType =\"Map\"&gt;<br/>");
		sb.append(selectSb.toString());
		sb.append("&lt;/select&gt;");
		return sb.toString();
	}
	
	//新增sql
	public String createInsert(List<Map> tableInfo,String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		StringBuffer insertSb = new StringBuffer();
		List<String> columNameList = tableInfo.stream().map(entity->entity.get("column_name")+"").collect(Collectors.toList());
		insertSb.append("&lt;!--新增"+Tools.camelCaseForMate(tableName)+"--&gt;<br/>");
		insertSb.append(" &lt;insert id=\""+construct.getMybatisMethodName("add")+"\" "+"parameterType =\"\" &gt; <br/>");
		insertSb.append("insert into <br/>"+tableName+"(");
		for(int i = 0;i<columNameList.size();i++) {
			insertSb.append(columNameList.get(i));
			if(i!=columNameList.size()-1) {
				insertSb.append(",");
			}
			if(i%3==0) {
				insertSb.append("<br/>");
			}
		}
		insertSb.append(")<br/>values(");
		for(int i = 0;i<columNameList.size();i++) {
			insertSb.append("#{"+Tools.camelCaseForMate(columNameList.get(i))+"}");
			if(i!=columNameList.size()-1) {
				insertSb.append(",");
			}
			if(i%3==0) {
				insertSb.append("<br/>");
			}
		}
		insertSb.append(")<br/>");
		insertSb.append(" &lt;/insert &gt;");
		return insertSb.toString();
	}
	
	//修改sql
	public String createUpdateSql(List<Map> tableInfo,String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		StringBuffer updateSb = new StringBuffer();
		List<String> columNameList = tableInfo.stream().map(entity->entity.get("column_name")+"").collect(Collectors.toList());
		updateSb.append("&lt;!--修改"+Tools.camelCaseForMate(tableName)+"--&gt;<br/>");
		updateSb.append(" &lt;update id=\""+construct.getMybatisMethodName("upd")+"\"  parameterType=\"Map\" &gt;<br/>" );
		updateSb.append("update "+tableName+" <br/>");
		updateSb.append(" &lt;set &gt; <br/>");
		for(int i = 0 ;i<columNameList.size();i++) {
			if("id".equals(columNameList.get(i))) {
				continue;
			}
			updateSb.append(" &lt;if test=\""+Tools.camelCaseForMate(columNameList.get(i))+"  != null\"  &gt;<br/>");
			updateSb.append(columNameList.get(i)+"=#{"+Tools.camelCaseForMate(columNameList.get(i))+"},<br/>");
			updateSb.append("&lt;/if&gt;<br/>");
		}
		updateSb.append(" &lt;/set&gt;<br/>");
		updateSb.append("where id = #{id}<br/>");
		updateSb.append(" &lt;/update&gt;<br/>");
		return updateSb.toString();
	}
	
	//删除sql
	public String createDelSql(List<Map> tableInfo,String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		StringBuffer delSb = new StringBuffer();
		List<String> columNameList = tableInfo.stream().map(entity->entity.get("column_name")+"").collect(Collectors.toList());
		delSb.append("&lt;!--删除"+Tools.camelCaseForMate(tableName)+"--&gt;<br/>");
		delSb.append(" &lt;delete id=\""+construct.getMybatisMethodName("del")+"\"  parameterType=\"String\" &gt;<br/>" );
		delSb.append("delete from "+tableName+" <br/>");
		delSb.append("where id = #{id}<br/>");
		delSb.append(" &lt;/delete&gt;<br/>");
		return delSb.toString();
	}
	
	//controlle新增
	public String createControllerAdd(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		String str ="	//新增"+construct.getBeanName()+"\r<br/>" + 
				"	@RequestMapping(value=\""+construct.getControllerMappingMethodValue("add")+"\")\r<br/>" + 
				"	public Result "+construct.getControllerMethodName("add")+"(HttpServletRequest request) {\r<br/>" + 
				"		Result result= new Result();\r<br/>" + 
				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r<br/>" + 
				"		result = "+construct.getServiceName()+"."+construct.getServiceMethodName("add")+"(parameter);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>";
		return str;
	}
	
	//controlle删除
	public String createControllerdel(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);

		String str ="	//删除"+construct.getBeanName()+"\r<br/>" + 
				"	@RequestMapping(value=\""+construct.getControllerMappingMethodValue("del")+"\")\r<br/>" + 
				"	public Result "+construct.getControllerMethodName("del")+"(HttpServletRequest request) {\r<br/>" + 
				"		Result result= new Result();\r<br/>" + 
				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r<br/>" + 
				"		result = "+construct.getServiceName()+"."+construct.getServiceMethodName("del")+"(parameter);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>";
		return str;
	}
	
	//controlle修改
	public String createControllerUpd(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		String str ="	//修改"+construct.getBeanName()+"\r<br/>" + 
				"	@RequestMapping(value=\""+construct.getControllerMappingMethodValue("upd")+"\")\r<br/>" + 
				"	public Result "+construct.getControllerMethodName("upd")+"(HttpServletRequest request) throws Exception{\r<br/>" + 
				"		Result result= new Result();\r<br/>" + 
				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r<br/>" + 
				"		result = "+construct.getServiceName()+"."+construct.getServiceMethodName("upd")+"(parameter);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>";
		return str;
	}
	
	//controlle查询
	public String createControllerFind(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);

		String str ="	//查询"+construct.getBeanName()+"\r<br/>" + 
				"	@RequestMapping(value=\""+construct.getControllerMappingMethodValue("find")+"\")\r<br/>" + 
				"	public Result "+construct.getControllerMethodName("find")+"(HttpServletRequest request) {\r<br/>" + 
				"		Result result= new Result();\r<br/>" + 
				"		Map parameter = Tools.parameterMapToMap(request.getParameterMap());\r<br/>" + 
				"		result = "+construct.getServiceName()+"."+construct.getServiceMethodName("find")+"(parameter);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>";
		return str;
	}
	
	//controller
	public String createController(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		String str ="\r<br/>" + 
				"import java.util.HashMap;\r<br/>" + 
				"import java.util.List;\r<br/>" + 
				"import java.util.Map;\r<br/>" + 
				"\r<br/>" + 
				"import javax.servlet.http.HttpServletRequest;\r<br/>" + 
				"\r<br/>" + 
				"import org.springframework.beans.factory.annotation.Autowired;\r<br/>" + 
				"import org.springframework.stereotype.Controller;\r<br/>" + 
				"import org.springframework.web.bind.annotation.RequestMapping;\r<br/>" + 
				"\r<br/>" + 
				"import com.alibaba.fastjson.JSON;\r<br/>" + 
				"import com.infiai.webcommon.Result;\r<br/>" + 
				"import com.infiai.webcommon.Tools;\r<br/>" + 
				"import com.infiai.webcommon.domain.User;\r<br/>" + 
				"\r<br/>" + 
				"@SuppressWarnings(\"all\")\r<br/>" + 
				"@Controller\r<br/>" + 
				"@RequestMapping(value=\""+construct.getControllerMappingValue()+"\")\r<br/>" + 
				"public class "+construct.getControllerName()+" {\r<br/>" + 
				"	@Autowired\r<br/>" + 
				"	"+construct.getServiceClassName()+"  "+construct.getServiceName()+";\r<br/>" + 
				"\r<br/>" + 
				createControllerAdd(tableName)+ 
				"\r<br/>" + 
				createControllerdel(tableName)+
				"\r<br/>" + 
				createControllerUpd(tableName)+
				"\r<br/>" + 
				createControllerFind(tableName)+
				"\r<br/>" + 
				"\r<br/>}"  ;
		return str;
	}
	
	//serviceAdd
	public String serviceAdd(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		String str ="//新增"+construct.getBeanName()+"\r<br/>" + 
				"	public Result "+construct.getServiceMethodName("add")+"(Map parameter) {\r<br/>" + 
				"		Result result= new Result();\r<br/>" + 
				"		"+construct.getBeanName()+" entity = JSON.parseObject(parameter.get(\"data\")+\"\", "+construct.getBeanName()+".class);\r<br/>" + 
				"		"+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("add")+"(entity);\r<br/>" + 
				"		result.setData(entity);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>" + 
				"\r<br/>";
		return str;
	}
	
	//serviceDel
	public String servicedel(String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		
		String str ="//删除"+construct.getBeanName()+"\r<br/>" + 
				"	public Result "+construct.getServiceMethodName("del")+"(Map parameter) {\r<br/>" + 
				"		Result result = new Result();\r<br/>" + 
				"		List &lt;String&gt; ids =  JSON.parseArray(parameter.get(\"data\")+\"\",String.class);<br/>"+
				"		Map queryCondition = new HashMap();\r<br/>" + 
				"		List total = new ArrayList();\r<br/>" + 
				"		for(String id:ids) {\r<br/>" + 
				"			queryCondition.put(\"id\", id);\r<br/>" + 
				"			List<"+construct.getBeanName()+"> list = "+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("find")+"(queryCondition);\r<br/>" + 
				"			if(list.size() > 0) {\r<br/>" + 
				"				"+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("del")+"(id);\r<br/>" + 
				"				total.add(list.get(0));\r<br/>" + 
				"			}	\r<br/>" + 
				"		}\r<br/>" + 
				"		result.setData(total);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>" + 
				"\r<br/>";
		return str;
	}
	
	//serviceUpd
	public String serviceupd(String tableName){
		CodeConstruct construct = new CodeConstruct(tableName);
		
		String str = "	//修改"+construct.getBeanName()+"\r<br/>" + 
				"	public Result "+construct.getServiceMethodName("upd")+"(Map parameter) throws Exception{\r<br/>" + 
				"		Result result = new Result();\r<br/>" + 
				"		"+construct.getBeanName()+" entity = JSON.parseObject(parameter.get(\"data\")+\"\", "+construct.getBeanName()+".class);\r<br/>" + 
				"		\r<br/>" + 
				"		if(Tools.isNotBlank(entity.getId()+\"\")) {\r<br/>" + 
				"			"+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("upd")+"(BeanUtils.describe(entity));\r<br/>" + 
				"		}else {\r<br/>" + 
				"			result.setStatus(\"500\");\r<br/>" + 
				"			result.setNote(\"id不能为空\");;\r<br/>" + 
				"		}\r<br/>" + 
				"		Map queryCondition = new HashMap();\r<br/>" + 
				"		queryCondition.put(\"id\", entity.getId());\r<br/>" + 
				"		List<"+construct.getBeanName()+"> list = "+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("find")+"(queryCondition);\r<br/>" + 
				"		result.setData(list.get(0));\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>" + 
				"\r<br/>";
		return str;
	}
	
	//servicefind
	public String servicefind(Map map) {
		String tableName = map.get("tableName")+"";
		String queryStr = map.get("data")+"";
		List<String> columNameList = new ArrayList();
		if(Tools.isNotBlank(queryStr)) {
			columNameList = JSON.parseArray(queryStr, String.class);
		}
		
		CodeConstruct construct = new CodeConstruct(tableName);
		StringBuffer sb = new StringBuffer();
		sb.append("	//查询"+construct.getBeanName()+"\r<br/>" + 
				"public Result "+construct.getServiceMethodName("find")+"(Map map){\r<br/>" + 
				"Result result = new Result();\r<br/>"+
				"Map queryCondition = new HashMap();\r<br/>");
				for(String columName:columNameList) {
					sb.append("queryCondition.put(\""+Tools.camelCaseForMate(columName)+"\",map.get(\""+Tools.camelCaseForMate(columName)+"\"))");
				}
				sb.append("List<"+construct.getBeanName()+"> list = "+construct.getMybatisDaoFieldName()+"."+construct.getMybatisMethodName("find")+"(map);\r<br/>" + 
				"		result.setData(list);\r<br/>" + 
				"		return result;\r<br/>" + 
				"	};\r<br/>" + 
				"\r<br/>");
		return sb.toString();
	}
	
	//service
	public String service(Map parameter) {
		String tableName = parameter.get("tableName")+"";
		CodeConstruct construct = new CodeConstruct(tableName);

		String str ="import java.util.ArrayList;\r<br/>" + 
				"import java.util.HashMap;\r<br/>" + 
				"import java.util.List;\r<br/>" + 
				"import java.util.Map;\r<br/>" + 
				"\r<br/>" + 
				"import org.springframework.beans.factory.annotation.Autowired;\r<br/>" + 
				"import org.springframework.stereotype.Service;\r<br/>" + 
				"\r<br/>" + 
				"\r<br/>" + 
				"@SuppressWarnings(\"all\")\r<br/>" + 
				"@Service\r<br/>" + 
				"public class "+construct.getServiceClassName()+"Service"+"Service {\r<br/>" + 
				"	@Autowired\r<br/>" + 
				"	"+construct.getMybatisDaoInterfaceName()+" "+construct.getMybatisDaoFieldName()+";\r<br/>" + 
				"\r<br/>"+
				serviceAdd(tableName)+"<br/>"+
				serviceupd(tableName)+"<br/>"+
				servicefind(parameter)+"<br/>"+
				servicedel(tableName)+"<br/>"+
				"}";

		return str;
	}
	
	//mybatisInterface
	public String mybatisInterface(Map map) {
		String tableName = map.get("tableName")+"";
		CodeConstruct construct = new CodeConstruct(tableName);
		
		StringBuffer sb = new StringBuffer();
		sb.append("import java.util.List;\r<br/>" + 
				"import java.util.Map;\r<br/>" + 
				"\r<br/>" + 
				"@SuppressWarnings(\"all\")\r<br/>" + 
				"public interface "+construct.getMybatisDaoInterfaceName()+" {\r<br/>" + 
				"	\r<br/>" + 
				"	//查询"+construct.getBeanName()+"\r<br/>" + 
				"	List<"+construct.getBeanName()+"> "+construct.getMybatisMethodName("find")+"(Map queryCondition);\r<br/>" + 
				"	\r<br/>" + 
				"	//修改"+construct.getBeanName()+"\r<br/>" + 
				"	int "+construct.getMybatisMethodName("upd")+"(Map entity);\r<br/>" + 
				"	\r<br/>" + 
				"	//删除"+construct.getBeanName()+"\r<br/>" + 
				"	int "+construct.getMybatisMethodName("del")+"(String id);\r<br/>" + 
				"	\r<br/>" + 
				"	//新增"+construct.getBeanName()+"\r<br/>" + 
				"	int "+construct.getMybatisMethodName("add")+"("+construct.getBeanName()+" entity);\r<br/>" + 
				"}");
		return sb.toString();
	}
	
	//实体类
	public String createBean(List<Map> tableInfo,String tableName) {
		CodeConstruct construct = new CodeConstruct(tableName);
		List<String> columNamelist = tableInfo.stream().map(entity->entity.get("column_name")+"").collect(Collectors.toList());
		
		StringBuffer sb = new StringBuffer();
		sb.append("@SuppressWarnings(\"all\")\r<br/>" + 
				"public class "+construct.getBeanName()+" {\r<br/>");
		for(String field:columNamelist) {
			if("id".equals(field)) {
				sb.append("private Integer id;<br/>");
			}else {
				sb.append("private String "+Tools.camelCaseForMate(field)+";<br/>");
			}
		}
		for(String field:columNamelist) {
			if("id".equals(field)) {
				sb.append("	public Integer "+construct.getMethodName(Tools.camelCaseForMate(field))+"() {\r<br/>" + 
						"		return "+Tools.camelCaseForMate(field)+";\r<br/>" + 
						"	}");
				sb.append("	public void "+construct.setMethodName(Tools.camelCaseForMate(field))+"(Integer "+Tools.camelCaseForMate(field)+") {\r<br/>" + 
						"		this."+Tools.camelCaseForMate(field)+" = "+Tools.camelCaseForMate(field)+";\r<br/>" + 
						"	}<br/>");
			}else {
				sb.append("	public String "+construct.getMethodName(Tools.camelCaseForMate(field))+"() {\r<br/>" + 
						"		return "+Tools.camelCaseForMate(field)+";\r<br/>" + 
						"	}<br/>");
				sb.append("	public void "+construct.setMethodName(Tools.camelCaseForMate(field))+"(String "+Tools.camelCaseForMate(field)+") {\r<br/>" + 
						"		this."+Tools.camelCaseForMate(field)+" = "+Tools.camelCaseForMate(field)+";\r<br/>" + 
						"	}<br/>");
			}
		}
		sb.append("<br/>");
		sb.append("}");
		return sb.toString();
	}
}
