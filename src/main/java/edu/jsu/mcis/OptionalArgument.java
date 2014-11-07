package edu.jsu.mcis;

public class OptionalArgument extends CommandLineArgument{
	
	private int numValues;
	private String shortName;
	
	public OptionalArgument(String title)
	{
		this.title = title;
		shortName = "";
	}
	
	public OptionalArgument(String title, int numValues)
	{
		this.title = title;
		this.numValues = numValues;
		shortName = "";
	}
	
	public OptionalArgument(String title, int numValues, Type type, String description, Object value)
	{
		this.title = title;
		this.type = type;
		this.description = description;
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
	
	public void addValue(Object obj)
	{
		value = obj;
	}
	public Object getValue()
	{
		return value;
	}
	
	public String toString()
	{
		String output = "";
		output += "\n\t<optionalArgument>\n\t\t<name>" + title + "</name>";
		output += "\n\t\t<numValues>" + numValues + "</numValues>";
		output += "\n\t\t<type>" + type + "</type>";
		output += "\n\t\t<description>" + description + "</description>";	
		output += "<value>" + value + "</value>";
		output += "<shortName>" + shortName + "</shortName>\n\t</optionalArgument>";
		return output;
	}
	
	public boolean equals(Object o)
	{
		boolean result = false;
		if(o instanceof OptionalArgument)
		{
			OptionalArgument arg = (OptionalArgument) o;
			
			if(this.title.equals(arg.getTitle()))
				result = true;
			if(!arg.getShort().equals("") && this.title.equals(arg.getShort()))
			{
				result = true;
			}
		}
		return result;
	}
}