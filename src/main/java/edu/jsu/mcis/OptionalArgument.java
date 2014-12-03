package edu.jsu.mcis;

public class OptionalArgument extends CommandLineArgument{
	
	private String shortName;
	private boolean required;
	
/**
 *Class constructor.
 *Creates an optional argument with the default value set to string, 
required set to false, and number of values set to 0.
 *
 *@param title - the title of the optional argument
 */
	public OptionalArgument(String title)
	{
		this.title = title;
		shortName = "";
		description = "";
		type =	CommandLineArgument.DataType.String;
		required = false;
		numberValues = 0;
	}
	
/**
 *Class constructor.
 *Creates an argument with the title and data type passed in.
 *
 *@param title - the title of the optional argument
 *@param type - the type of the optional argument
 */
	public OptionalArgument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
		shortName = "";
		required = false;
		numberValues = 0;
	}
	
/**
 *Sets the shorthand name of the optional argument.
 *
 *
 *@param string - the short name of the optinal argument
 */
	public void setShort(String string)
	{
		shortName = string;
	}
	
/**
 *Returns the number of multiple values an optional argument has.
 *
 *
 *@return the size of the array with multiple values
 */	
	public int getMultiplesCount()
	{
		return multipleValues.size();
	}
	
/**
 *Returns the shorthand name of an optional argument.
 *
 *
 *@return  the short name of the optional argument
 */
	public String getShort()
	{
		return shortName;
	}
	
/**
 *Sets the value of the optional argument.
 *
 *
 *@param object - the value of the optional argument
 *@throws NumberFormatException - improper data type
 *@throws IncorrectValueException - the value is not within the restricted settings
 */
	public void setValue(Object object) throws NumberFormatException, IncorrectValueException
	{
			value = object;
		if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
			throw new IncorrectValueException();
	}
	
/**
 *Returns the value of the optional argument.
 *
 *
 *@return the value of the argument
 */
	public <T> T getValue()
	{
		return (T) value;
	}
	
/**
 *Sets the optional argument as required.
 *
 *
 *
 */
	public void setRequired() 
	{
		required = true;
	}
	
/**
 *Returns whether or not the optional argument is required.
 *
 *
 *@return <code>true</code>If the positional argument is required or not.
 */
	public boolean getRequired()
	{
		return required;
	}

/**
 *Returns the string representation of an optional argument for use in XML formatting.
 *
 *
 *@return a string representing the argument which includes: argument name, type,description,value and short name
 */
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

/**
 *Returns whether or not two optional arguments are the same.
 *
 *@param o - the argument that is being compared
 *@return true if the title of the optional argument is equal to the
 */
	public boolean equals(Object o)
	{
		boolean result = false;
		if(o instanceof OptionalArgument)
		{
			OptionalArgument arg = (OptionalArgument) o;
			
			if(this.title.equals(arg.getTitle()) || this.getShort().equals(arg.getTitle()))
				result = true;
			if(this.title.equals(arg.getShort()))
				result = true;
		}
		return result;
	}
	
/**
 *
 *
 *
 *@return a hashCode for each optional argument
 */
	public int hashCode() {
		return 17;	
    }
}