package com.atha.treemapindia;

import java.io.Serializable;
import java.util.Arrays;

public class Session implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private long	          gid;
	private String	          id;
	private int	              numTrees;
	private long	          time;
	private int	              surveyorId;
	private TreeDetails[]	  trees;

	public Session()
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getNumTrees()
	{
		return numTrees;
	}

	public void setNumTrees(int numTrees)
	{
		this.numTrees = numTrees;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public int getSurveyorId()
	{
		return surveyorId;
	}

	public void setSurveyorId(int surveyorId)
	{
		this.surveyorId = surveyorId;
	}

	public TreeDetails[] getTrees()
	{
		return trees;
	}

	public void setTrees(TreeDetails[] trees)
	{
		this.trees = trees;
	}

	@Override
	public String toString()
	{
		return "Session [id=" + id + ", numTrees=" + numTrees + ", time=" + time + ", trees=" + Arrays.toString(trees) + "]";
	}

}
