package com.gaimit.mlm.model;

//도서관이 보유하고 있는 책 정보

public class BookHeld extends Book{
	private int id;
	private String regDate;
	private String barcode;
	private String localIdCode;
	private int bookIdBook;
	private int libraryIdLib;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getLocalIdCode() {
		return localIdCode;
	}
	public void setLocalIdCode(String localIdCode) {
		this.localIdCode = localIdCode;
	}
	public int getBookIdBook() {
		return bookIdBook;
	}
	public void setBookIdBook(int bookIdBook) {
		this.bookIdBook = bookIdBook;
	}
	public int getLibraryIdLib() {
		return libraryIdLib;
	}
	public void setLibraryIdLib(int libraryIdLib) {
		this.libraryIdLib = libraryIdLib;
	}
	@Override
	public String toString() {
		return "BookHeld [id=" + id + ", regDate=" + regDate + ", barcode=" + barcode + ", localIdCode=" + localIdCode
				+ ", bookIdBook=" + bookIdBook + ", libraryIdLib=" + libraryIdLib + "]";
	}
	
	
}
