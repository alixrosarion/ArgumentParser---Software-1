package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{
	private int numValues;
	private String shortTitle;
	
	public Argument(String title)
	{
		this.title = title;
		type = "";
		description = "";
		numValues = 0;
		shortTitle = "";
	}
	
	public Argument(String title, String type, String description)
	{
		this.title = title;
		this.type = type;
		this.description = description;
		numValues = 0;
		shortTitle ="";
	}
	
	public void addValue(Object v)
	{
		value = v;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public String getTitle()
	{
		return title;
	}
	public String toString()
	{
		String result;
		
		result = title;
		if( type != "")
			result += " " +type;
		else
			result = result;
		if(description != "")
			result += " " +description;
		else
			result = result;
			
		return result;
	}
	public int getNumValues()
	{
		return numValues;
	}
	public String getShort()
	{
		return shortTitle;
	}

	public boolean equals(Object o)
	{
		if(o instanceof Argument)
		{
			Argument arg = (Argument) o;
			return this.title.equals(arg.getTitle());
		}
		return false;	
	}
	
	public int hashCode() {
        int result = 17;
		result = 31 * title.hashCode();
		return result;	
    }
	
	public void setShort(String s){}
	public void setNumValues(int num){}
}