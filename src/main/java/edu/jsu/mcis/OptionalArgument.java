package edu.jsu.mcis;

public class OptionalArgument extends CommandLineArgument{
	
	private int numberValues;
	private String shortName;
	
	public OptionalArgument(String title)
	{
		this.title = title;
		shortName = "";
		description = "";
		type =	CommandLineArgument.Type.Unknown;
	}
	
	public OptionalArgument(String title, int numberValues)
	{
		this.title = title;
		this.numberValues = numberValues;
		shortName = "";
		description = "";
		type =	CommandLineArgument.Type.Unknown;
	}
	
	public OptionalArgument(String title, int numberValues, CommandLineArgument.Type type, String description, Object value)
	{
		this.title = title;
		this.type = type;
		this.description = description;
		this.numberValues = numberValues;
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
	
	public void setNumberValues(int number)
	{
		numberValues = number;
	}
	
	public int getNumberValues()
	{
		return numberValues;
	}
	
	public void addValue(Object object)
	{
		value = object;
	}
	public Object getValue()
	{
		return value;
	}
	
	public String toString()
	{
		String output = "";
		output += "\r\n\t<optionalArgument>\r\n\t\t<name>" + title + "</name>";
		output += "\r\n\t\t<numValues>" + numberValues + "</numValues>";
		output += "\r\n\t\t<type>" + type + "</type>";
		output += "\r\n\t\t<description>" + description + "</description>";	
		output += "\r\n\t\t<value>" + value + "</value>";
		output += "\r\n\t\t<shortName>" + shortName + "</shortName>\r\n\t</optionalArgument>";
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
	public int hashCode() {
		int result = 17 * title.hashCode();
		return result;	
    }
}