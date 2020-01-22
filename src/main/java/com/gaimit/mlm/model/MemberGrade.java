package com.gaimit.mlm.model;

public class MemberGrade {
	private int gradeId;
	private String gradeName;
	private int brwLimit;
	private int dateLimit;
	private int idLib;

	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getBrwLimit() {
		return brwLimit;
	}
	public void setBrwLimit(int brwLimit) {
		this.brwLimit = brwLimit;
	}
	public int getDateLimit() {
		return dateLimit;
	}
	public void setDateLimit(int dateLimit) {
		this.dateLimit = dateLimit;
	}
	public int getIdLib() {
		return idLib;
	}
	public void setIdLib(int idLib) {
		this.idLib = idLib;
	}
	@Override
	public String toString() {
		return "MemberGrade [gradeId=" + gradeId + ", gradeName=" + gradeName + ", brwLimit=" + brwLimit
				+ ", dateLimit=" + dateLimit + ", idLib=" + idLib + "]";
	}
}
