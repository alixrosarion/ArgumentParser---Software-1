package edu.jsu.mcis;

/**
 *As an extension of the CommandLineArgument class, Argument
 *focuses on everything dealing with positional arguments. Here
 *the set value method is housed which attaches the value(s) to
 *the argument after checking to make sure the argument and the
 *value have the same data type. This class allows
 *arguments to either have a declared data type or,default to string. The toString()
 * method of this class returns the argument information in XML format.
 *
 *
 *
 */


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
 *Class constructor. Constructs an argument with a specified data type
 *<p>
 *Accepted Data Types: Boolean, Float, String, Integer
 *
 *@param title the title of the argument
 *@param type the Data type of the argument
 */
	public Argument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
		numberValues = 0;
	}
	
/**
 *Sets the value of an argument after checking if the data
 *type of both the argument and the value match. If the data
 *types do not match an error is thrown.
 *<p>
 *Additionally, if the
 *argument has a set of restricted values this method checks if 
 *the value being added is one of those restricted values.
 *
 *@param o the value to be set
 *@throws NumberFormatException improper data type
 *@throws IncorrectValueException the value is not within the restricted settings
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
 *Returns an empty string since positional arguments
 *do not have shorts.
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
 *@param o the argument that is being compared
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
 *Generates a hash code based on the name of the argument which
 *is used to check if two or more arguments are equal.
 *
 *@return a unique hashCode for each argument, based on the title which is unique.
 */
	public int hashCode() {
		int result = 31 * title.hashCode();
		return result;	
    }

/**
 *Returns true since all added positional arguments are required for the.
 *program to run.
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