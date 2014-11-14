package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{	
	public Argument(String title)
	{
		this.title = title;
		type =	CommandLineArgument.DataType.String;
		description = "";
	}
	
	public Argument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
	}
	
	public void setValue(Object o) throws NumberFormatException
	{
			if(type == CommandLineArgument.DataType.Integer)
					value =Integer.parseInt(o.toString());
			else if(type == CommandLineArgument.DataType.Boolean)
			{
				if((o.toString().equals("true")) || o.toString().equals("false"))
					value =Boolean.parseBoolean(o.toString());
			    else 
					throw new NumberFormatException();
			}
			else if( type == CommandLineArgument.DataType.Float)
				value =Float.parseFloat(o.toString());
			else
				value = o.toString();
	}
	
	public <T> T getValue()
	{
		return (T) value;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String toString()
	{	
		String output = "";
		output += "\r\n\t<argument>\r\n\t\t<name>" + title + "</name>";
		output += "\r\n\t\t<type>" + type + "</type>";
		output += "\r\n\t\t<description>" + description + "</description>\r\n\t</argument>";
		
		return output;
	}
	
	public int getNumberValues()
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
		int result = 31 * title.hashCode();
		return result;	
    }

	public boolean getRequired()
	{
		return true;
	}
	
	public void setShort(String s){}
	public void setNumberValues(int num){}
	public void setRequired(){}
}