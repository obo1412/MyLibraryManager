package com.gaimit.mlm.model;

public class Borrow {
	private int idBrw;
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
