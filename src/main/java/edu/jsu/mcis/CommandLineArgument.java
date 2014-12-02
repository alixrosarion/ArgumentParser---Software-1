package edu.jsu.mcis;
import java.util.*;

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
 *
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
 *
 *
 *
 *@return if the argument has restricted values or no restricted values (true/false)
 */
	public boolean hasRestricted()
	{
		return hasRestricted;
	}
	
/**
 *
 *
 *
 *@return the title of the argument
 */
	public String getTitle() {
		return title;
	}
	
/**
 *
 *
 *
 *@param d the description of the argument
 */
	public void setDescription(String d)
	{
		description = d;
	}
	
/**
 *
 *
 *
 *@param t the data type of the argument
 */
	public void setDataType(DataType t)
	{
		type = t;
	}
	
/**
 *
 *
 *
 *@return the description of the argument
 */
	public String getDescription()
	{
		return description;
	}
	
/**
 *
 *
 *
 *@return the data type of the argument
 */
	public DataType getDataType()
	{
		return type;
	}
	
/**
 *
 *
 *@param arg the value the argument is restricted to
 *@throws IncorrectTypeException if the data type of the argument
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
 *
 *
 *@param o The value to be set
 *@throws NumberFormatException Improper Data type
 *@throws IncorrectValueException The value is not within the restricted settings
 */
	public void setValue(Object o) throws NumberFormatException, IncorrectValueException
	{
	
		if(type == CommandLineArgument.DataType.Integer)
		{
				value = Integer.parseInt(o.toString());
				if(restrictedValues.size() != 0 && !restrictedValues.contains(value))
					throw new IncorrectValueException();
		}
		else if(type == CommandLineArgument.DataType.Boolean)
		{
			if((o.toString().equals("true")) || o.toString().equals("false"))
				value = Boolean.parseBoolean(o.toString());
			else 
				throw new NumberFormatException();
		}
		else if(type == CommandLineArgument.DataType.Float)
		{
			value = Float.parseFloat(o.toString());
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
 *@param number the number of values the argument can hold
 */
	public void setNumberValues(int number)
	{
		numberValues = number;
	}
	
/**
 *
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
}
