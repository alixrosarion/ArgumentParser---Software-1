package edu.jsu.mcis;

public class Argument extends CommandLineArgument
{	
/**
 *Class constructor.
 *
 *@param title The title of the positional argument
 */
	public Argument(String title)
	{
		this.title = title;
		type =	CommandLineArgument.DataType.String;
		description = "";
		numberValues = 1;
	}
	
/**
 *
 *
 *@param title The title of the argument
 *@param type The Data type of the argument
 */
	public Argument(String title, CommandLineArgument.DataType type)
	{
		this.title = title;
		this.type = type;
		description = "";
		numberValues = 1;
	}
	

	

	
/**
 *
 *@return the title of the argument
 */
	public String getTitle()
	{
		return title;
	}
	
/**
 *
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
 *
 *
 *
 *@return nothing: positional arguments do not have a short
 */
	public String getShort()
	{
		return "";
	}

/**
 *
 *
 *
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
 *
 *
 *
 *@return <code>true</code>If the positional argument is required or not.
 */
	public boolean getRequired()
	{
		return true;
	}
	
	public void setShort(String s){}
	public void setRequired(){}
}