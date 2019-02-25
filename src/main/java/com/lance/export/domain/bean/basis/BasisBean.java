package com.lance.export.domain.bean.basis;
@SuppressWarnings("all")
public class BasisBean {

	private String id;
	private String ip;
	private String user;
	private String password;
	private String createrTime;
	private String remark;
	private String port;
	private String defaultDb;
	
	public String getId() { 
		return id; 
	}	
	public void setId(String id) { 
		this.id = id; 
	}
	public String getIp() { 
		return ip; 
	}
	public void setIp(String ip) { 
		this.ip = ip; 
	}
	public String getUser() { 
		return user; 
	}
	public void setUser(String user) { 
		this.user = user; 
	}
	public String getPassword() { 
		return password; 
	}
	public void setPassword(String password) { 
		this.password = password; 
	}
	public String getCreaterTime() { 
		return createrTime; 
	}
	public void setCreaterTime(String createrTime) { 
		this.createrTime = createrTime; 
	}
	public String getRemark() { 
		return remark; 
	}
	public void setRemark(String remark) { 
		this.remark = remark; 
	}
	public String getPort() { 
		return port; 
	}
	public void setPort(String port) { 
		this.port = port; 
	}
	public String getDefaultDb() {
		return defaultDb;
	}
	public void setDefaultDb(String defaultDb) {
		this.defaultDb = defaultDb;
	}
	
	
}
