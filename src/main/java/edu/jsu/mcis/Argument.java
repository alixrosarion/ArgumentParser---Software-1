package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{	
/**
 *@author GroupA
 *
 *@param title The title of the argument
 */
	public Argument(String title)
	{
		this.title = title;
		type =	CommandLineArgument.DataType.String;
		description = "";
		numberValues = 1;
	}
	
/**
 *@param title The title of the argument
 *@param type The datatype of the argument
 */
	public Argument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
		numberValues = 1;
	}
	
/**
 *@param o The value to be set
 *@throws NumberFormatException Improper Data type
 *@throws IncorrectValueException The value is not within the restricted settings
 */
	public void setValue(Object o) throws NumberFormatException, IncorrectValueException
	{
			if(type == CommandLineArgument.DataType.Integer)
			{
					value =Integer.parseInt(o.toString());
					if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
						throw new IncorrectValueException();
			}
			else if(type == CommandLineArgument.DataType.Boolean)
			{
				if((o.toString().equals("true")) || o.toString().equals("false"))
					value =Boolean.parseBoolean(o.toString());
			    else 
					throw new NumberFormatException();
			}
			else if( type == CommandLineArgument.DataType.Float)
			{
				value =Float.parseFloat(o.toString());
				if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
						throw new IncorrectValueException();
			}
			else
			{
				value = o.toString();
				if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
						throw new IncorrectValueException();
			}
	}
	
/**
 *
 *
 *
 */
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
	public void setRequired(){}
}