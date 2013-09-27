package com.atha.treemapindia;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Surveydetail
{
	int	           nest, burrows, flowers, fruits, nails, poster, wires,
	        tree_guard, men_other, s_other, ref_to_dept;
	String	       fno, prop_id, tree_no, tree_name, botanical_name,
	        men_other_desc, health_tree, found_ground, ground_condition,
	        risk_on_tree, risk_desc, pest_affected, _date, _time, s_other_desc;
	private long	sessionId;
	private long	surveyorId;
	private String	prabhagId;
	private String	clusterId;
	double	       height_ft, height_m, girth_cm, girth_m, lat, lon;
	private String	images[]	= new String[5];

	// Date created;
	public Surveydetail(String fno, String prop_id, String tree_no, String tree_name, String botanical_name, int burrows, int nest, int fruits, int flowers, int nails, int poster, int wires, int tree_guard, int men_other, String men_other_desc, String health_tree, String found_ground, String ground_condition, String risk_on_tree, String risk_desc, String pest_affected, int ref_to_dept, int s_other, String s_other_desc, double height_ft, double height_m, double girth_cm, double girth_m, double lat, double lon, String _created, String _time)
	{
		this.fno = fno;
		this.prop_id = prop_id;
		this.tree_no = tree_no;
		this.tree_name = tree_name;
		this.botanical_name = botanical_name;
		this.burrows = burrows;
		this.nest = nest;
		this.fruits = fruits;
		this.flowers = flowers;
		this.nails = nails;
		this.poster = poster;
		this.wires = wires;
		this.tree_guard = tree_guard;
		this.men_other = men_other;
		this.men_other_desc = men_other_desc;
		this.health_tree = health_tree;
		this.found_ground = found_ground;
		this.ground_condition = ground_condition;
		this.risk_on_tree = risk_on_tree;
		this.risk_desc = risk_desc;
		this.pest_affected = pest_affected;
		this.ref_to_dept = ref_to_dept;
		this.s_other = s_other;
		this.s_other_desc = s_other_desc;
		this.height_ft = height_ft;
		this.height_m = height_m;
		this.girth_cm = girth_cm;
		this.girth_m = girth_m;
		this._date = _created;
		this._time = _time;
		this.lat = lat;
		this.lon = lon;
		// this.created=_created;
	}

	public Surveydetail(String fno, String prop_id, String tree_no, String tree_name, String botanical_name, int burrows, int nest, int fruits, int flowers, int nails, int poster, int wires, int tree_guard, int men_other, String men_other_desc, String health_tree, String found_ground, String ground_condition, String risk_on_tree, String risk_desc, String pest_affected, int ref_to_dept, int s_other, String s_other_desc, double height_ft, double height_m, double girth_cm, double girth_m, double lat, double lon)
	{
		this(fno, prop_id, tree_no, tree_name, botanical_name, burrows, nest, fruits, flowers, nails, poster, wires, tree_guard, men_other, men_other_desc, health_tree, found_ground, ground_condition, risk_on_tree, risk_desc, pest_affected, ref_to_dept, s_other, s_other_desc, height_ft, height_m, girth_cm, girth_m, lat, lon, get_date(), get_time());// new
		                                                                                                                                                                                                                                                                                                                                                       // Date(java.lang.System.currentTimeMillis()));

		// this(fno ,prop_id, tree_no, tree_name, botanical_name, burrows, nest, fruits, flowers,
		// nails, poster, wires,tree_guards,men_other,men_other_desc, health_tree, found_ground,
		// ground_condition, risk_on_tree, risk_desc,rare,endangered,vulnerable,pest_affected,
		// height_ft, height_m, girth_cm, girth_m, new Date(java.lang.System.currentTimeMillis()));
	}

	/** get_ ToDo List item creation date */
	/*
	 * public Date get_Created() { return created; }
	 * 
	 * 
	 * @Override public String toString() { SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	 * return sdf.format(created); }
	 */

	public void setSessionId(long sessionId)
	{
		this.sessionId = sessionId;
	}

	public long getSessionId()
	{
		return sessionId;
	}

	public void setImages(String images[])
	{
		this.images = images;
	}

	public String[] getImages()
	{
		return images;
	}

	public void setSurveyorId(long surveyorId)
	{
		this.surveyorId = surveyorId;
	}

	public long getSurveyorId()
	{
		return surveyorId;
	}

	public String getPrabhagId()
	{
		return prabhagId;
	}

	public void setPrabhagId(String prabhagId)
	{
		this.prabhagId = prabhagId;
	}

	public String getClusterId()
	{
		return clusterId;
	}

	public void setClusterId(String clusterId)
	{
		this.clusterId = clusterId;
	}

	public static String get_date()
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current Date => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	public static String get_time()
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String formattedTime = df.format(c.getTime());
		return formattedTime;
	}

	public String get_dt()
	{
		return _date;
	}

	public String get_tm()
	{
		return _time;
	}

	public String get_fno()
	{
		return fno;

	}

	public String get_prop_id()
	{
		return prop_id;

	}

	public String get_tree_no()
	{
		return tree_no;
	}

	public String get_tree_name()
	{
		return tree_name;
	}

	public String get_botanical_name()
	{
		return botanical_name;
	}

	public int get_burrows()
	{
		return burrows;
	}

	public int get_nest()
	{
		return nest;
	}

	public int get_fruits()
	{
		return fruits;
	}

	public int get_flowers()
	{
		return flowers;
	}

	public int get_nails()
	{
		return nails;
	}

	public int get_poster()
	{
		return poster;
	}

	public int get_wires()
	{
		return wires;
	}

	public int get_tree_guards()
	{
		return tree_guard;
	}

	public int get_other_nuissance()
	{
		return men_other;
	}

	public String get_other_nuissance_desc()
	{
		return men_other_desc;
	}

	public String get_health_tree()
	{
		return health_tree;
	}

	public String get_ground_type()
	{
		return found_ground;
	}

	public String get_ground_desc()
	{
		return ground_condition;
	}

	public String get_risk_on_tree()
	{
		return risk_on_tree;
	}

	public String get_risk_desc()
	{
		return risk_desc;
	}

	public String get_pest_affected()
	{
		return pest_affected;
	}

	public double get_height_ft()
	{
		return height_ft;
	}

	public double get_height_m()
	{
		return height_m;
	}

	public double get_girth_cm()
	{
		return girth_cm;
	}

	public double get_girth_m()
	{
		return girth_m;
	}

	public int get_ref_to_dept()
	{
		return ref_to_dept;
	}

	public int get_s_other()
	{
		return s_other;
	}

	public String get_s_other_desc()
	{
		return s_other_desc;
	}

	public double get_lat()
	{
		return lat;
	}

	public double get_lon()
	{
		return lon;
	}

	public void set_fno(String fno)
	{
		this.fno = fno;

	}

	public void set_prop_id(String prop_id)
	{
		this.prop_id = prop_id;

	}

	public void set_tree_name(String tree_name)
	{
		this.tree_name = tree_name;
	}

	public void set_tree_no(String tree_no)
	{
		this.tree_no = tree_no;
	}

	public void set_botanical_name(String botanical_name)
	{
		this.botanical_name = botanical_name;
	}

	public void set_burrows(int burrows)
	{
		this.burrows = burrows;
	}

	public void get_nest(int nest)
	{
		this.nest = nest;
	}

	public void set_fruits(int fruits)
	{
		this.fruits = fruits;
	}

	public void set_flowers(int flowers)
	{
		this.flowers = flowers;
	}

	public void set_nails(int nails)
	{
		this.nails = nails;
	}

	public void set_poster(int poster)
	{
		this.poster = poster;
	}

	public void set_wires(int wires)
	{
		this.wires = wires;
	}

	public void set_tree_guards(int tree_guard)
	{
		this.tree_guard = tree_guard;
	}

	public void set_men_other(int men_other)
	{
		this.men_other = men_other;
	}

	public void set_men_other_desc(String men_other_desc)
	{
		this.men_other_desc = men_other_desc;
	}

	public void set_health_tree(String health_tree)
	{
		this.health_tree = health_tree;
	}

	public void set_found_ground(String found_ground)
	{
		this.found_ground = found_ground;
	}

	public void set_ground_condition(String ground_condition)
	{
		this.found_ground = ground_condition;
	}

	public void set_risk_on_tree(String risk_on_tree)
	{
		this.risk_on_tree = risk_on_tree;
	}

	public void set_risk_desc(String risk_desc)
	{
		this.risk_desc = risk_desc;
	}

	public void set_pest_affected(String pest_affected)
	{
		this.pest_affected = pest_affected;
	}

	public void set_height_ft(double height_ft)
	{
		this.height_ft = height_ft;
	}

	public void set_height_m(double height_m)
	{
		this.height_m = height_m;
	}

	public void set_girth_cm(double girth_cm)
	{
		this.girth_cm = girth_cm;
	}

	public void set_girth_m(double girth_m)
	{
		this.girth_m = girth_m;
	}

	public void set_ref_to_dept(int ref_to_dept)
	{
		this.ref_to_dept = ref_to_dept;
	}

	public void set_s_other(int s_other)
	{
		this.s_other = s_other;
	}

	public void set_s_other_desc(String s_other_desc)
	{
		this.s_other_desc = s_other_desc;
	}

	public void set_lat(double lat)
	{
		this.lat = lat;
	}

	public void set_lon(double lon)
	{
		this.lon = lon;
	}
}
