package edu.jsu.mcis;

public class OptionalArgument extends Argument{
	
	private int numValues;
	private String shortName;
	
	public OptionalArgument(String title)
	{
		super(title);
		shortName = "";
	}
	
	public OptionalArgument(String title, int numValues)
	{
		super();
		this.title = title;
		this.numValues = numValues;
		shortName = "";
	}
	
	public OptionalArgument(String title, int numValues, String type, String description, Object value)
	{
		super(title, type, description);
		this.numValues = numValues;
		this.value = value;
		shortName = "";
	}
	
	public void setShort(String str)
	{
		shortName = str;
	}
	
	public String getShort()
	{
		return shortName;
	}
	
	public void setNumValues(int num)
	{
		numValues = num;
	}
	
	public int getNumValues()
	{
		return numValues;
	}
	
	public void setValue(Object obj)
	{
		value = obj;
	}
	
	
	public boolean equals(Object o)
	{	
		OptionalArgument arg = (OptionalArgument) o;
		boolean result = false;
		if(this.title.equals(arg.getTitle()))
			result = true;
		if(!arg.getShort().equals("") && this.title.equals(arg.getShort()))
		{
			result = true;
		}
		return result;
	}
}