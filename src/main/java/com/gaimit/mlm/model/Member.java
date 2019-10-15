package com.gaimit.mlm.model;

public class Member {
	private int id;
	private int idLib;
	private String idCode;
	private String name;
	private String phone;
	private int level;
	private int limitStart;
	private int listCount;
	
	public int getIdLib() {
		return idLib;
	}
	public void setIdLib(int idLib) {
		this.idLib = idLib;
	}

	
	public int getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", idLib=" + idLib + ", idCode=" + idCode + ", name=" + name + ", phone=" + phone
				+ ", level=" + level + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}
	
}
