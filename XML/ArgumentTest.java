import java.util.*;

public class ArgumentTest
{
	public String name;
	public String type;
	public String description;
	
	public ArgumentTest()
	{
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
	public String toString()
	{
		String s = "Argument Name: " + name + "\nArgument Type: " + type + "\nArgument description: " + description;
		return s;
	}
	
}