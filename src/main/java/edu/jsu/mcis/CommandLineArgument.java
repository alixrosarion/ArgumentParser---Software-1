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
	
	public boolean hasRestricted()
	{
		return hasRestricted;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public void setDataType(DataType t)
	{
		type = t;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public DataType getDataType()
	{
		return type;
	}
	
	public void setRestricted(Object arg) throws IncorrectTypeException
	{
		if(type == DataType.Float)
			try {
				Float.parseFloat(arg.toString());
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		else if(type == DataType.Integer)
			try {
				Integer.parseInt(arg.toString());
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		else if(type == DataType.Boolean)
			try {
				Boolean.parseBoolean(arg.toString());
		} catch (Exception e) {throw new IncorrectTypeException(arg.toString());
		}
		restrictedValues.add(arg);
		hasRestricted = true;
	}
	
	public void setNumberValues(int number)
	{
		numberValues = number;
	}
	
	public int getNumberValues()
	{
		return numberValues;
	}
	
	abstract public String getShort();
	abstract public <T> T getValue();
	abstract public void setShort(String s);
	abstract public void setValue(Object v);
	abstract public void setRequired();
	abstract public boolean getRequired();
}
