package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{	
	public Argument(String title)
	{
		this.title = title;
		type =	CommandLineArgument.Type.Unknown;
		description = "";
	}
	
	public Argument(String title, CommandLineArgument.Type type, String description)
	{
		this.title = title;
		this.type = type;
		this.description = description;
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
		if( type != CommandLineArgument.Type.Unknown)
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
		return 0;
	}
	
	public String getShort()
	{
		return "";
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