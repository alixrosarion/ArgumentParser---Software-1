package edu.jsu.mcis;

public class OptionalArgument extends CommandLineArgument{
	
	private String shortName;
	private boolean required;
	
/**
 *
 *
 *
 *@param title The title of the optional argument
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
 *
 *
 *@param title The title of the optional argument
 *@param type The type of the optional argument
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
 *
 *
 *
 *@param string The short name of the optinal argument
 */
	public void setShort(String string)
	{
		shortName = string;
	}
	
	public int getMultiplesCount()
	{
		return multipleValues.size();
	}
	
/**
 *
 *
 *
 *@return  The short name of the optional argument
 */
	public String getShort()
	{
		return shortName;
	}
	
/**
 *
 *
 *
 *@param object The value of the optional argument
 *@throws NumberFormatException Improper Data type
 *@throws IncorrectValueException The value is not within the restricted settings
 */
	public void setValue(Object object) throws NumberFormatException, IncorrectValueException
	{
			value = object;
		if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
			throw new IncorrectValueException();
	}
	
/**
 *
 *
 *
 *@return the value of the argument
 */
	public <T> T getValue()
	{
		return (T) value;
	}
	
/**
 *
 *
 *
 *
 */
	public void setRequired() 
	{
		required = true;
	}
	
/**
 *
 *
 *
 *@return <code>true</code>If the positional argument is required or not.
 */
	public boolean getRequired()
	{
		return required;
	}

/**
 *
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
 *
 *
 *
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