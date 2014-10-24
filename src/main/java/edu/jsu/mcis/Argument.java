package edu.jsu.mcis;

public class Argument
{
	protected String title;
	protected Object value;
	protected String type;
	protected String description;
	
	public Argument(){}
	public Argument(String title)
	{
		this.title = title;
		type = "";
		description = "";
	}
	
	public Argument(String title, String type, String description)
	{
		this.title = title;
		this.type = type;
		this.description = description;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	public void addValue(Object v)
	{
		value = v;
	}
	
	public void setType(String t)
	{
		type = t;
	}
	public String getType()
	{
		return type;
	}
	
	
	public String getDescription()
	{
		return description;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public String getTitle()
	{
		return title;
	}
	public String toString()
	{
		String result;
		
		result = title;
		if( type != "")
			result += " " +type;
		else
			result = result;
		if(description != "")
			result += " " +description;
		else
			result = result;
			
		return result;
	}
	
	public boolean equals(Object o)
	{	
		Argument arg = (Argument) o;
		return this.title.equals(arg.getTitle());
	}
	
	public int hashCode() {
        int result = 17;
		
		result = 31 * title.hashCode();
		return result;
		
    }
}