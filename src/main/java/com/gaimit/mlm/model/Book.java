package com.gaimit.mlm.model;

public class Book extends Member{
	private int idBook;
	private String idCodeBook;
	private String nameBook;
	private String writerBook;
	private String publisherBook;
	private String pubDateBook;
	private String priceBook;
	private String isbn13Book;
	private String callNoBook;
	private String classCodeBook;
	private String classificationBook;
	private String descriptionBook;
	
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
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
	public String getPubDateBook() {
		return pubDateBook;
	}
	public void setPubDateBook(String pubDateBook) {
		this.pubDateBook = pubDateBook;
	}
	public String getPriceBook() {
		return priceBook;
	}
	public void setPriceBook(String priceBook) {
		this.priceBook = priceBook;
	}
	public String getIsbn13Book() {
		return isbn13Book;
	}
	public void setIsbn13Book(String isbn13Book) {
		this.isbn13Book = isbn13Book;
	}
	public String getCallNoBook() {
		return callNoBook;
	}
	public void setCallNoBook(String callNoBook) {
		this.callNoBook = callNoBook;
	}
	public String getClassCodeBook() {
		return classCodeBook;
	}
	public void setClassCodeBook(String classCodeBook) {
		this.classCodeBook = classCodeBook;
	}
	public String getClassificationBook() {
		return classificationBook;
	}
	public void setClassificationBook(String classificationBook) {
		this.classificationBook = classificationBook;
	}
	public String getDescriptionBook() {
		return descriptionBook;
	}
	public void setDescriptionBook(String descriptionBook) {
		this.descriptionBook = descriptionBook;
	}
	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", idCodeBook=" + idCodeBook + ", nameBook=" + nameBook + ", writerBook="
				+ writerBook + ", publisherBook=" + publisherBook + ", pubDateBook=" + pubDateBook + ", priceBook="
				+ priceBook + ", isbn13Book=" + isbn13Book + ", callNoBook=" + callNoBook + ", classCodeBook="
				+ classCodeBook + ", classificationBook=" + classificationBook + ", descriptionBook=" + descriptionBook
				+ "]";
	}
	
	
}
