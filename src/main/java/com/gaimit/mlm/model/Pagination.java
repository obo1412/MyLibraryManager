package com.gaimit.mlm.model;

public class Pagination {
	// 페이지 구현을 위해서 추가된 값
	private int limitStart;
	private int listCount;
	
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
	@Override
	public String toString() {
		return "Pagination [limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}

}
