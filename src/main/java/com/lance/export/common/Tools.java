package com.lance.export.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class Tools {
	
	public static String camelCaseForMate(String str) {
		List<String> list1 = Arrays.asList(str.split("_"));
		StringBuffer sb = new StringBuffer();
		for(String s:list1) {
			if(list1.indexOf(s)!=0) {
				sb.append(s.substring(0, 1).toUpperCase()+s.substring(1));
			}else{
				sb.append(s);
			}
		}
		return sb.toString();
	}
	
	public static Map<String, String> parameterMapToMap(Map<String, String[]> map) {

		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<?> entries = map.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	public static boolean isNotBlank(String str) {
		boolean isCheck = false;
		if (str != null && !"".equals(str) && !" ".equals(str) && !"NAN".equals(str) && !"NULL".equals(str)
				&& !"undefined".equals(str) && !"null".equals(str)) {
			isCheck = true;
		}
		return isCheck;
	}
	
	public static String currentDate() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(new Date());
	}

	public static String currentDatetime() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(new Date());
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
