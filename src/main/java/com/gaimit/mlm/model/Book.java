package com.gaimit.mlm.model;

public class Book {
	private int idBook;
	private String nameBook;
	private String writerBook;
	private String publisherBook;
	private int priceBook;
	private String regDateBook;
	
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
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
	public int getPriceBook() {
		return priceBook;
	}
	public void setPriceBook(int priceBook) {
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
		return "Book [idBook=" + idBook + ", nameBook=" + nameBook + ", writerBook=" + writerBook + ", publisherBook="
				+ publisherBook + ", priceBook=" + priceBook + ", regDateBook=" + regDateBook + "]";
	}
	
	
	
	
}
