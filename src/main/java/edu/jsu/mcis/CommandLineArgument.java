package edu.jsu.mcis;
import java.util.*;

/**
 *The CommandLineArgument class represents the abstraction of a
 *general argument that can be parsed from the command-line. This class
 *provides Optional and Positional Arguments with methods for specifying title, 
 *values that can be restrictive if the user desires, description,
 *data type, and setting the number of expected values for an argument.
 *
 *
 */

public abstract class CommandLineArgument {
	protected String title;
	protected Object value;
	protected String description;
	protected DataType type;
	protected int numberValues;
	public enum DataType {Integer, Float, String, Boolean}
	protected List <Object> restrictedValues = new ArrayList<Object>();
	private boolean hasRestricted;
	protected List <Object> multipleValues = new ArrayList<Object>();
	
/**
 *Returns the values an argument is restricted to using.
 *
 *
 *@return the restricted values associated with an argument
 */
	public String getRestricted()
	{
		String output = "";
		for (int i = 0; i<restrictedValues.size(); i++)
		{
			if(i == restrictedValues.size() -1)
				output += restrictedValues.get(i).toString();
			else
				output += restrictedValues.get(i).toString() + " ";
		}
		return output;
	}
	
	
	
/**
 *Returns whether or not the argument has restricted values.
 *
 *
 *@return if the argument has restricted values or no restricted values (true/false)
 */
	public boolean hasRestricted()
	{
		return hasRestricted;
	}
	
/**
 *Returns the title of the argument.
 *
 *
 *@return the title of the argument
 */
	public String getTitle() {
		return title;
	}
	
/**
 *Sets the description of an argument.
 *
 *
 *@param d the description of the argument
 */
	public void setDescription(String d)
	{
		description = d;
	}
	
/**
 *Sets the data type of an argument.
 *<p>
 *Accepted Data Types: Boolean, Float, String, Integer
 *
 *@param t the data type of the argument
 */
	public void setDataType(DataType t)
	{
		type = t;
	}
	
/**
 *Returns the description of the argument for generating
 * and formatting the help text.
 *
 *@return the description of the argument
 */
	public String getDescription()
	{
		return description;
	}
	
/**
 *Returns what data type the argument can hold. This is
 *used when the program checks to make sure the argument
 *and value have matching data types when adding a value
 *to the argument.
 *
 *@return the data type of the argument
 */
	public DataType getDataType()
	{
		return type;
	}
	
/**
 *Adds a set list of values an argument is restricted to using.
 *
 *@param arg the value the argument is restricted to
 *@throws IncorrectTypeException - if the data type of the argument
 *                               and of the value do not match
 */
	public void addRestricted(Object arg) throws IncorrectTypeException
	{
		if(type == DataType.Float)
			try {
				restrictedValues.add(Float.parseFloat(arg.toString()));
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		else if(type == DataType.Integer)
			try {
				restrictedValues.add(Integer.parseInt(arg.toString()));
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		else if(type == DataType.Boolean)
			try {
				restrictedValues.add(Boolean.parseBoolean(arg.toString()));
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		else restrictedValues.add(arg);
		hasRestricted = true;
	}

/**
 *Sets the number of values an argument has. This is used
 *when parsing the values to make sure the argument receives
 *all of the values it is supposed to.
 *<p>
 *If the number of values is set to zero ('0') then the
 *argument becomes a flag argument and will receive no values.
 *
 *@param number the number of values the argument can hold
 */
	public void setNumberValues(int number)
	{
		numberValues = number;
	}
	
/**
 *Returns the number of values an argument has
 *
 *
 *@return the number of values the argument can hold
 */
	public int getNumberValues()
	{
		return numberValues;
	}
	
	abstract public String getShort();
	abstract public <T> T getValue();
	abstract public void setShort(String s);
	abstract public void setRequired();
	abstract public boolean getRequired();
	abstract public int getMultiplesCount();
	abstract public void setValue(Object o);
}
