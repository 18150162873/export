package com.lance.export.common;


public class Result {

	private Object data;
	private String status="200";//200成功，403：权限不够。401：未登录或会话过期。444：账号或者密码错误。500：系统某些错误，如果是500，则note字段会填写相关错误信息
	private int size;
	private String token;//返回成功 一定要加上token   
	private String note;//只有当status为500时才有相关提示信息。比如：创建用户的时候，用户已经存在。
	private Object dim;
	private Object config;
	
	public Result(){
		
	}
	
	public Result(String token){
		this.token=token;
	}
	public Result(int size){
		this.size=size;
	}
	public Result(String status,String token,String note){
		this.status=status;
		this.token=token;
		this.note=note;
	}
	public Result(String status,String token){
		this.status=status;
		this.token=token;
	}
	public Result(String status,String token,int size){
		this.status=status;
		this.token=token;
		this.size=size;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Object getDim() {
		return dim;
	}
	public void setDim(Object dim) {
		this.dim = dim;
	}
	public Object getConfig() {
		return config;
	}
	public void setConfig(Object config) {
		this.config = config;
	}
	
	
}
