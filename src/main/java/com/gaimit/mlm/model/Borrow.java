package com.gaimit.mlm.model;

public class Borrow extends Book {
	private int idBrw;
	private int idLibBrw;
	private int idBookBrw;
	private int idMemberBrw;
	private String startDateBrw;
	private String endDateBrw;
	
	public int getIdBrw() {
		return idBrw;
	}
	public void setIdBrw(int idBrw) {
		this.idBrw = idBrw;
	}
	public int getIdLibBrw() {
		return idLibBrw;
	}
	public void setIdLibBrw(int idLibBrw) {
		this.idLibBrw = idLibBrw;
	}
	public int getIdBookBrw() {
		return idBookBrw;
	}
	public void setIdBookBrw(int idBookBrw) {
		this.idBookBrw = idBookBrw;
	}
	public int getIdMemberBrw() {
		return idMemberBrw;
	}
	public void setIdMemberBrw(int idMemberBrw) {
		this.idMemberBrw = idMemberBrw;
	}
	public String getStartDateBrw() {
		return startDateBrw;
	}
	public void setStartDateBrw(String startDateBrw) {
		this.startDateBrw = startDateBrw;
	}
	public String getEndDateBrw() {
		return endDateBrw;
	}
	public void setEndDateBrw(String endDateBrw) {
		this.endDateBrw = endDateBrw;
	}
	
	@Override
	public String toString() {
		return "Borrow [idBrw=" + idBrw + ", idBookBrw=" + idBookBrw + ", idMemberBrw=" + idMemberBrw
				+ ", startDateBrw=" + startDateBrw + ", endDateBrw=" + endDateBrw + "]";
	}
	
		
	
}
