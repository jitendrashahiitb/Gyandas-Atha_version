package com.atha.treemapindia;

public class Unique_Area_Detail {

	String ward_no,poly_no,form_key;
	public Unique_Area_Detail(String ward_no,String poly_no,String form_key) {
		// TODO Auto-generated constructor stub
		this.ward_no=ward_no;
		this.poly_no=poly_no;
		this.form_key=form_key;
	}
	public String get_ward(){
		return ward_no;
	}
	
	public String get_poly(){
		return poly_no;
	}
	
	public String get_form_key(){
		return form_key;
	}
	
	public void set_ward(String ward_no){
		this.ward_no=ward_no;
	}
	
	public void set_poly(String poly_no){
		this.poly_no= poly_no;
	}
	
	public void set_form_key(String form_key){
		this.form_key=form_key;
	}

}
