package com.atha.treemapindia;

public class Treelist {
	
	public String tree_name;
	public String botonical_name;
	
	public Treelist(String tree_name,String botonical_name){
		this.tree_name=tree_name;
		this.botonical_name=botonical_name;
	}
	
	public String get_tree_name(){
		return tree_name;
	}

	public String get_botonical_name() {
		return botonical_name;
	}
	
	public void set_tree_name(String tree_name){
		this.tree_name= tree_name;
	}

	public void set_botonical_name(String botonical_name) {
		this.botonical_name= botonical_name;
	}
}
