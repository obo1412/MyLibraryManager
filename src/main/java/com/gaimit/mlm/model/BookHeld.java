package com.gaimit.mlm.model;

//도서관이 보유하고 있는 책 정보

public class BookHeld extends Book{
	private int id;
	private int libraryIdLib;
	private int bookIdBook;
	private String bookShelf;
	private String regDate;
	private String editDate;
	private String localIdBarcode;
	private int purchasedOrDonated;
	private int available;
	private String additionalCode;
	private int copyCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLibraryIdLib() {
		return libraryIdLib;
	}
	public void setLibraryIdLib(int libraryIdLib) {
		this.libraryIdLib = libraryIdLib;
	}
	public int getBookIdBook() {
		return bookIdBook;
	}
	public void setBookIdBook(int bookIdBook) {
		this.bookIdBook = bookIdBook;
	}
	public String getBookShelf() {
		return bookShelf;
	}
	public void setBookShelf(String bookShelf) {
		this.bookShelf = bookShelf;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public String getLocalIdBarcode() {
		return localIdBarcode;
	}
	public void setLocalIdBarcode(String localIdBarcode) {
		this.localIdBarcode = localIdBarcode;
	}
	public int getPurchasedOrDonated() {
		return purchasedOrDonated;
	}
	public void setPurchasedOrDonated(int purchasedOrDonated) {
		this.purchasedOrDonated = purchasedOrDonated;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public String getAdditionalCode() {
		return additionalCode;
	}
	public void setAdditionalCode(String additionalCode) {
		this.additionalCode = additionalCode;
	}
	public int getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(int copyCode) {
		this.copyCode = copyCode;
	}
	@Override
	public String toString() {
		return "BookHeld [id=" + id + ", libraryIdLib=" + libraryIdLib + ", bookIdBook=" + bookIdBook + ", bookShelf="
				+ bookShelf + ", regDate=" + regDate + ", editDate=" + editDate + ", localIdBarcode=" + localIdBarcode
				+ ", purchasedOrDonated=" + purchasedOrDonated + ", available=" + available + ", additionalCode="
				+ additionalCode + ", copyCode=" + copyCode + "]";
	}

}
