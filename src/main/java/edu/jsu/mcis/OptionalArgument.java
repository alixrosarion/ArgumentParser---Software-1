package edu.jsu.mcis;

/**
 *As an extension of the Command Line Argument class,
 *this class focuses entirely on the information and
 *attributes associated with optional (named) arguments and
 *assigning them to the proper element in the optional argument.
 *As with the Argument class, this class allows for 
 *optional arguments to either have a specified data type
 *or default to string. Optional arguments have the added ability to
 *use a short name to be referenced by their short name, if one is provided. For
 *example: "type" can be assigned the short 't'.
 *This allows the user to call on optional argument "type" from the
 *command line by simply typing "-t" instead of the full "--type."
 *<p>
 *The toString() method of this class will format return the optional
 *arguments in XML format.
 *
 *
 *
 *
 */

public class OptionalArgument extends CommandLineArgument{
	
	private String shortName;
	private boolean required;
	
/**
 *Class constructor.
 *Creates an optional argument with the following characteristics:
 *default value set to string, required set to false, and the number
 *of values set to '0'.
 *
 *@param title the name of the optional argument
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
 *Creates an optional argument with a specified data type.
 *<p>
 *Accepted Data Types: Boolean, Float, String, Integer 
 *
 *@param title the name of the optional argument
 *@param type the type of the optional argument
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
 *Sets a shorthand name for the optional argument to
 *be called with from the command line.
 *
 *
 *@param string the short name of the optional argument
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
 *Returns what the shorthand name of an optional argument is.
 *
 *
 *@return  the short name of the optional argument
 */
	public String getShort()
	{
		return shortName;
	}
	
/**
 *Sets the value of the optional argument to the object being
 *passed in.
 *
 *@param object the value of the optional argument
 *@throws NumberFormatException improper data type
 *@throws IncorrectValueException the value is not within the restricted settings
 */
	public void setValue(Object object) throws NumberFormatException, IncorrectValueException
	{
			value = object;
		if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
			throw new IncorrectValueException();
	}
	
/**
 *Returns the value of the optional argument as an object.
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
 */
	public void setRequired() 
	{
		required = true;
	}
	
/**
 *Returns if the optional argument is required or not required.
 *
 *@return <code>true</code>If the positional argument is required or not.
 */
	public boolean getRequired()
	{
		return required;
	}

/**
 *Returns the string representation of an optional argument for 
 *use in XML formatting.
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
 *Returns if two optional arguments are equal to each other.
 *
 *@param o the argument that is being compared
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
 *Generates a hash code based on the name of the argument which
 *is used to check if two or more arguments are equal.
 *
 *@return a hashCode for each optional argument
 */
	public int hashCode() {
		return 17;	
    }
}