package edu.jsu.mcis;

public class OptionalArgument{
	
	private String title;
	private int numValues;
	private String type;
	private String description;
	private Object defaultValue;
	
	public OptionalArgument(String title)
	{
		this.title = title;
	}
	
	public OptionalArgument(String title, int numValues)
	{
		this.title = title;
		this.numValues = numValues;
	}
	
	public OptionalArgument(String title, int numValues, String type, String description, Object defaultValue)
	{
		this.title = title;
		this.numValues = numValues;
		this.type = type;
		this.description = description;
		this.defaultValue = defaultValue;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setNumValues(int num)
	{
		numValues = num;
	}
	
	public int getNumValues()
	{
		return numValues;
	}
	
	public void setType(String type)
	{
		type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setDescription(String desc)
	{
		description = desc;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDefaultValue(Object obj)
	{
		defaultValue = obj;
	}
	
	public Object getDefaultValue()
	{
		return defaultValue;
	}
	
	public boolean equals(Object o)
	{	
		OptionalArgument arg = (OptionalArgument) o;
		return (this.title.equals(arg.getTitle()));
	}
	
	public int hashCode() {
        int result = 17;
		
		result = 31 * title.hashCode();
		return result;
    }
}