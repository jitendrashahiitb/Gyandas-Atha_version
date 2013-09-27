package com.atha.treemapindia;

public class Area_Details
{
	String	prop_type, prop_desc, prop_owner, house_no, survey_no, form_key;
	double	prop_area;

	public Area_Details(String prop_type, String prop_desc, String prop_owner, String house_no, String survey_no, double prop_area, String form_key)
	{
		this.prop_type = prop_type;
		this.prop_desc = prop_desc;
		this.prop_owner = prop_owner;
		this.house_no = house_no;
		this.survey_no = survey_no;
		this.prop_area = prop_area;
		this.form_key = form_key;
	}

	public Area_Details(String place_type, String form_key)
	{
		this.prop_type = place_type;
		this.prop_desc = "nil";
		this.prop_owner = "nil";
		this.house_no = "nil";
		this.survey_no = "nil";
		this.prop_area = 0.0;
		this.form_key = form_key;
	}

	public String get_prop_type()
	{
		return prop_type;
	}

	public String get_prop_desc()
	{
		return prop_desc;
	}

	public String get_prop_owner()
	{
		return prop_owner;
	}

	public String get_house_no()
	{
		return house_no;
	}

	public String get_survey()
	{
		return survey_no;
	}

	public double get_prop_area()
	{
		return prop_area;
	}

	public String get_form_key()
	{
		return form_key;
	}

	public void set_prop_type(String prop_type)
	{
		this.prop_type = prop_type;
	}

	public void set_prop_desc(String prop_desc)
	{
		this.prop_desc = prop_desc;
	}

	public void set_prop_owner(String prop_owner)
	{
		this.prop_owner = prop_owner;
	}

	public void set_house_no(String house_no)
	{
		this.house_no = house_no;
	}

	public void set_survey(String survey_no)
	{
		this.survey_no = survey_no;
	}

	public void set_prop_area(double prop_area)
	{
		this.prop_area = prop_area;
	}

	public void set_form_key(String form_key)
	{
		this.form_key = form_key;
	}
}
