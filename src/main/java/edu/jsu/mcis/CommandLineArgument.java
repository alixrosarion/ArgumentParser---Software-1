package edu.jsu.mcis;

public abstract class CommandLineArgument {
	protected String title;
	protected String type;
	protected Object value;
	protected String description;
	
	public String getTitle() {
		return title;
	}
	public void setDescription(String d)
	{
		description = d;
	}
	public void setType(String t)
	{
		type = t;
	}
	public String getDescription()
	{
		return description;
	}
	public String getType()
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
