package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{	
/**
 *Class constructor.
 *Constructs an argument with the default data type set as string.
 *
 *@param title The title of the positional argument
 */
	public Argument(String title)
	{
		this.title = title;
		type =	CommandLineArgument.DataType.String;
		description = "";
		numberValues = 0;
	}
	
/**
 *Class constructor.
 *
 *@param title - the title of the argument
 *@param type - the Data type of the argument
 */
	public Argument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
		numberValues = 0;
	}
	
/**
 *Sets the value of an argument.
 *
 *@param o - the value to be set
 *@throws NumberFormatException - improper data type
 *@throws IncorrectValueException - the value is not within the restricted settings
 */
	public void setValue(Object o) throws NumberFormatException, IncorrectValueException
	{
		Object tmpValue = null;
		if(type == CommandLineArgument.DataType.Integer)
		{
				tmpValue =Integer.parseInt(o.toString());
				if(restrictedValues.size() != 0 && !restrictedValues.contains(tmpValue))
					throw new IncorrectValueException();
		}
		else if(type == CommandLineArgument.DataType.Boolean)
		{
			if((o.toString().equals("true")) || o.toString().equals("false"))
				tmpValue =Boolean.parseBoolean(o.toString());
			else 
				throw new NumberFormatException();
		}
		else if( type == CommandLineArgument.DataType.Float)
		{
			tmpValue =Float.parseFloat(o.toString());
			if(restrictedValues.size() != 0 && !restrictedValues.contains(tmpValue))
					throw new IncorrectValueException();
		}
		else
		{
			tmpValue = o.toString();
			if(restrictedValues.size() != 0 && !restrictedValues.contains(tmpValue))
					throw new IncorrectValueException();
		}
		if (numberValues ==0)	value = tmpValue;
		else multipleValues.add(tmpValue);
	}
	
/**
 *Returns the value of an argument.
 *
 *@return the value of the argument
 */
	public <T> T getValue()
	{
		if(numberValues == 0)	return (T) value;
		else return (T) multipleValues.toString();
	}
	
	public int getMultiplesCount()
	{
		return multipleValues.size();
	}
	
/**
 *Returns the title of the argument.
 *
 *@return the title of the argument
 */
	public String getTitle()
	{
		return title;
	}
	
/**
 *Returns the string representation of an argument for use in XML formatting.
 *
 *
 *@return a string representing the argument which includes: argument name, type, and description.
 */
	public String toString()
	{	
		String output = "";
		output += "\r\n\t<argument>\r\n\t\t<name>" + title + "</name>";
		output += "\r\n\t\t<type>" + type + "</type>";
		output += "\r\n\t\t<description>" + description + "</description>\r\n\t</argument>";
		
		return output;
	}
	
/**
 *Returns an empty string.
 *
 *
 *@return nothing: positional arguments do not have a shorthand name
 */
	public String getShort()
	{
		return "";
	}

/**
 *Returns whether or not two arguments are the same.
 *
 *@param o - the argument that is being compared
 *@return <code>true</code>if two arguments are equivalent
 */
	public boolean equals(Object o)
	{
		if(o instanceof Argument)
		{
			Argument arg = (Argument) o;
			return this.title.equals(arg.getTitle());
		}
		return false;	
	}
	
/**
 *
 *
 *
 *@return a unique hashCode for each argument, based on the title which is unique.
 */
	public int hashCode() {
		int result = 31 * title.hashCode();
		return result;	
    }

/**
 *Returns true since all positional arguments are required.
 *
 *
 *@return <code>true</code> if the positional argument is required or not.
 */
	public boolean getRequired()
	{
		return true;
	}
	
	public void setShort(String s){}
	public void setRequired(){}
}