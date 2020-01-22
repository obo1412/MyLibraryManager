package com.gaimit.mlm.model;

public class Borrow extends BookHeld {
	private int idBrw;
	private int idLibBrw;
	private int bookHeldId;
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
	public int getBookHeldId() {
		return bookHeldId;
	}
	public void setBookHeldId(int bookHeldId) {
		this.bookHeldId = bookHeldId;
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
		return "Borrow [idBrw=" + idBrw + ", bookHeldId=" + bookHeldId + ", idMemberBrw=" + idMemberBrw
				+ ", startDateBrw=" + startDateBrw + ", endDateBrw=" + endDateBrw + "]";
	}
	
		
	
}
