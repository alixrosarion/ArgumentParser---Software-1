package edu.jsu.mcis;

public class OptionalArgument extends CommandLineArgument{
	
	private String shortName;
	private boolean required;
	
/**
 *
 *
 *
 *
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
 *
 *
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
 *
 */
	public void setShort(String string)
	{
		shortName = string;
	}
	
/**
 *
 *
 *
 *
 */
	public String getShort()
	{
		return shortName;
	}
	
/**
 *
 *
 *
 *
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
 *
 */
	public Object getValue()
	{
		return value;
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
 *
 */
	public boolean getRequired()
	{
		return required;
	}

/**
 *
 *
 *
 *
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
	//"this" keyword refers to the one in the list
		boolean result = false;
		if(o instanceof OptionalArgument)
		{
			OptionalArgument arg = (OptionalArgument) o;
			
			if(this.title.equals(arg.getTitle()) || this.getShort().equals(arg.getTitle()))
				result = true;
			if(this.title.equals(arg.getShort())) //Reflexivity needed by list.contains(Object o)
				result = true;
		}
		return result;
	}
	
/**
 *
 *
 *
 *
 */
	public int hashCode() {
		return 17;	
    }
}