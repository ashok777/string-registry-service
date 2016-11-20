package com.stringregistry.model;


public class UnicodeString {

    private int id;
    private String  text;
    
    public UnicodeString() {
    }
    public UnicodeString(int id, String text) {
    	this.id = id;
    	this.text = text;
    }
    public UnicodeString(String text) {
    	this.text = text;
    }
    public int getId(){
    	return id;
    }
    public void setId(int id ){
    	this.id= id;
    }
    public String getText(){
    	return text;
    }
    public void setText(String text){
    	this.text = text;
    }
}
