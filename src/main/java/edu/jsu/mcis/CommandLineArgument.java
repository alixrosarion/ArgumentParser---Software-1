package edu.jsu.mcis;

public abstract class CommandLineArgument {
	protected String title;
	protected Object value;
	protected String description;
	protected Type type;
	public enum Type {
		Integer, Float, String, Boolean, Unknown;
		public String toString()
		{
			if(this == Float) return "Float";
			else if(this == String) return "String";
			else if(this == Boolean) return "Boolean";
			else if(this == Integer) return "Integer";
			else return "";
		}
	};
	
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
	abstract public int getNumberValues();
	abstract public Object getValue();
	abstract public void setNumberValues(int i);
	abstract public void setShort(String s);
	abstract public void addValue(Object v);	
}
