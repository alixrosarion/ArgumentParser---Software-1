package edu.jsu.mcis;

public abstract class CommandLineArgument {
	protected String title;
	protected Object value;
	protected String description;
	public enum Type {Integer, Float, String, Boolean, Unknown};
	protected Type type;
	
	public String getTitle() {
		return title;
	}
	public void setDescription(String d)
	{
		description = d;
	}
	public void setType(Type t)
	{
		type = t;
	}
	public String getDescription()
	{
		return description;
	}
	public Type getType()
	{
		return type;
	}
	abstract public String getShort();
	abstract public int getNumValues();
	abstract public Object getValue();
	abstract public void setNumValues(int i);
	abstract public void setShort(String s);
	abstract public void addValue(Object v);	
}
