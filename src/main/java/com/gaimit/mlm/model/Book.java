package com.gaimit.mlm.model;

public class Book {
	private int idBook;
	private int idLibBook;
	private String idCodeBook;
	private String nameBook;
	private String writerBook;
	private String publisherBook;
	private String priceBook;
	private String regDateBook;
	
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	public int getIdLibBook() {
		return idLibBook;
	}
	public void setIdLibBook(int idLibBook) {
		this.idLibBook = idLibBook;
	}
	public String getIdCodeBook() {
		return idCodeBook;
	}
	public void setIdCodeBook(String idCodeBook) {
		this.idCodeBook = idCodeBook;
	}
	public String getNameBook() {
		return nameBook;
	}
	public void setNameBook(String nameBook) {
		this.nameBook = nameBook;
	}
	public String getWriterBook() {
		return writerBook;
	}
	public void setWriterBook(String writerBook) {
		this.writerBook = writerBook;
	}
	public String getPublisherBook() {
		return publisherBook;
	}
	public void setPublisherBook(String publisherBook) {
		this.publisherBook = publisherBook;
	}
	public String getPriceBook() {
		return priceBook;
	}
	public void setPriceBook(String priceBook) {
		this.priceBook = priceBook;
	}
	public String getRegDateBook() {
		return regDateBook;
	}
	public void setRegDateBook(String regDateBook) {
		this.regDateBook = regDateBook;
	}
	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", idLibBook=" + idLibBook + ", idCodeBook=" + idCodeBook + ", nameBook="
				+ nameBook + ", writerBook=" + writerBook + ", publisherBook=" + publisherBook + ", priceBook="
				+ priceBook + ", regDateBook=" + regDateBook + "]";
	}
}
