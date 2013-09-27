package com.atha.treemapindia;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TreeDetails implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private long	          gid;

	private String	          formNumber	     = null;
	private String	          propertyId	     = null;
	private int	              number;
	private int	              prabhagId;
	private int	              clusterId;
	private String	          propertyType;
	private String	          name;
	private String	          botanicalName;
	private double	          girth;
	private double	          height;
	private boolean	          nest;
	private boolean	          burrows;
	private boolean	          flowers;
	private boolean	          fruits;
	private boolean	          nails;
	private boolean	          poster;
	private boolean	          wires;
	private boolean	          treeGuard;
	private boolean	          otherNuissance;
	private String	          otherNuissanceDesc;
	private String	          health;
	private String	          groundType;
	private String	          groundDesc;
	private String	          riskDueToTree;
	private String	          riskDesc;
	private boolean	          referToDept;
	private boolean	          specialOther;
	private String	          specialOtherDesc;
	private double	          lattitude;
	private double	          longitude;
	private String	          point;
	private String	          creationDate;
	private String	          creationTime;
	private String	          deviceId;
	private String	          surveyorName;
	private int	              surveyorId;
	private String	          sessionId;
	private String	          imageF1;
	private String	          imageF2;
	private String	          imageP1;
	private String	          imageP2;
	private String	          imageOther;
	private long	          editTrace;
	private boolean	          deleted;

	public TreeDetails()
	{

	}

	public long getGid()
	{
		return gid;
	}

	public void setGid(long gid)
	{
		this.gid = gid;
	}

	public String getFormNumber()
	{
		return formNumber;
	}

	public void setFormNumber(String formNumber)
	{
		this.formNumber = formNumber;
	}

	public String getPropertyId()
	{
		return propertyId;
	}

	public void setPropertyId(String propertyId)
	{
		this.propertyId = propertyId;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getPrabhagId()
	{
		return prabhagId;
	}

	public void setPrabhagId(int prabhagId)
	{
		this.prabhagId = prabhagId;
	}

	public int getClusterId()
	{
		return clusterId;
	}

	public void setClusterId(int clusterId)
	{
		this.clusterId = clusterId;
	}

	public String getPropertyType()
	{
		return propertyType;
	}

	public void setPropertyType(String propertyType)
	{
		this.propertyType = propertyType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBotanicalName()
	{
		return botanicalName;
	}

	public void setBotanicalName(String botanicalName)
	{
		this.botanicalName = botanicalName;
	}

	public double getGirth()
	{
		return girth;
	}

	public void setGirth(double girth)
	{
		this.girth = girth;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public boolean isNest()
	{
		return nest;
	}

	public void setNest(boolean nest)
	{
		this.nest = nest;
	}

	public boolean isBurrows()
	{
		return burrows;
	}

	public void setBurrows(boolean burrows)
	{
		this.burrows = burrows;
	}

	public boolean isFlowers()
	{
		return flowers;
	}

	public void setFlowers(boolean flowers)
	{
		this.flowers = flowers;
	}

	public boolean isFruits()
	{
		return fruits;
	}

	public void setFruits(boolean fruits)
	{
		this.fruits = fruits;
	}

	public boolean isNails()
	{
		return nails;
	}

	public void setNails(boolean nails)
	{
		this.nails = nails;
	}

	public boolean isPoster()
	{
		return poster;
	}

	public void setPoster(boolean poster)
	{
		this.poster = poster;
	}

	public boolean isWires()
	{
		return wires;
	}

	public void setWires(boolean wires)
	{
		this.wires = wires;
	}

	public boolean isTreeGuard()
	{
		return treeGuard;
	}

	public void setTreeGuard(boolean treeGuard)
	{
		this.treeGuard = treeGuard;
	}

	public boolean isOtherNuissance()
	{
		return otherNuissance;
	}

	public void setOtherNuissance(boolean otherNuissance)
	{
		this.otherNuissance = otherNuissance;
	}

	public String getOtherNuissanceDesc()
	{
		return otherNuissanceDesc;
	}

	public void setOtherNuissanceDesc(String otherNuissanceDesc)
	{
		this.otherNuissanceDesc = otherNuissanceDesc;
	}

	public String getHealth()
	{
		return health;
	}

	public void setHealth(String health)
	{
		this.health = health;
	}

	public String getGroundType()
	{
		return groundType;
	}

	public void setGroundType(String groundType)
	{
		this.groundType = groundType;
	}

	public String getGroundDesc()
	{
		return groundDesc;
	}

	public void setGroundDesc(String groundDesc)
	{
		this.groundDesc = groundDesc;
	}

	public String getRiskDueToTree()
	{
		return riskDueToTree;
	}

	public void setRiskDueToTree(String riskDueToTree)
	{
		this.riskDueToTree = riskDueToTree;
	}

	public String getRiskDesc()
	{
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc)
	{
		this.riskDesc = riskDesc;
	}

	public boolean isReferToDept()
	{
		return referToDept;
	}

	public void setReferToDept(boolean referToDept)
	{
		this.referToDept = referToDept;
	}

	public boolean isSpecialOther()
	{
		return specialOther;
	}

	public void setSpecialOther(boolean specialOther)
	{
		this.specialOther = specialOther;
	}

	public String getSpecialOtherDesc()
	{
		return specialOtherDesc;
	}

	public void setSpecialOtherDesc(String specialOtherDesc)
	{
		this.specialOtherDesc = specialOtherDesc;
	}

	public double getLattitude()
	{
		return lattitude;
	}

	public void setLattitude(double lattitude)
	{
		this.lattitude = lattitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public String getPoint()
	{
		return point;
	}

	public void setPoint(String point)
	{
		this.point = point;
	}

	public static String getDate()
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current Date => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	public static String getTime()
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String formattedTime = df.format(c.getTime());
		return formattedTime;
	}

	public String getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(String creationTime)
	{
		this.creationTime = creationTime;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getSurveyorName()
	{
		return surveyorName;
	}

	public void setSurveyorName(String surveyorName)
	{
		this.surveyorName = surveyorName;
	}

	public int getSurveyorId()
	{
		return surveyorId;
	}

	public void setSurveyorId(int surveyorId)
	{
		this.surveyorId = surveyorId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getImageF1()
	{
		return imageF1;
	}

	public void setImageF1(String imageF1)
	{
		this.imageF1 = imageF1;
	}

	public String getImageF2()
	{
		return imageF2;
	}

	public void setImageF2(String imageF2)
	{
		this.imageF2 = imageF2;
	}

	public String getImageP1()
	{
		return imageP1;
	}

	public void setImageP1(String imageP1)
	{
		this.imageP1 = imageP1;
	}

	public String getImageP2()
	{
		return imageP2;
	}

	public void setImageP2(String imageP2)
	{
		this.imageP2 = imageP2;
	}

	public String getImageOther()
	{
		return imageOther;
	}

	public void setImageOther(String imageOther)
	{
		this.imageOther = imageOther;
	}

	public long getEditTrace()
	{
		return editTrace;
	}

	public void setEditTrace(long editTrace)
	{
		this.editTrace = editTrace;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	@Override
	public String toString()
	{
		return "TreeDetails [number=" + number + ", prabhagId=" + prabhagId + ", clusterId=" + clusterId + ", name=" + name + ", lattitude=" + lattitude + ", longitude=" + longitude + ", sessionId=" + sessionId + "]";
	}

}
