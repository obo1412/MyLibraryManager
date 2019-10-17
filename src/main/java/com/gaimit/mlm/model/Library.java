package com.gaimit.mlm.model;

public class Library {
	private int idLib;
	private String nameLib;
	private String locLib;
	
	public int getIdLib() {
		return idLib;
	}
	public void setIdLib(int idLib) {
		this.idLib = idLib;
	}
	public String getNameLib() {
		return nameLib;
	}
	public void setNameLib(String nameLib) {
		this.nameLib = nameLib;
	}
	public String getLocLib() {
		return locLib;
	}
	public void setLocLib(String locLib) {
		this.locLib = locLib;
	}
	
	@Override
	public String toString() {
		return "Library [idLib=" + idLib + ", nameLib=" + nameLib + ", locLib=" + locLib + "]";
	}

}
