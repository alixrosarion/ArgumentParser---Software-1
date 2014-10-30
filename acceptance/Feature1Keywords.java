import edu.jsu.mcis.*;

public class Feature1Keywords
{
	private ArgumentParser parser;
	
	public void startProgram (String s, String s1, String s2)
	{
		parser = new ArgumentParser();
		String se = s + " " +s1 + " "+s2;
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		addValues(se);
	}
	
	public void startProgram (String s, String s1, String s2, String s3)
	{
		parser = new ArgumentParser();
		
		String se = s + " " +s1 + " "+s2+ " " + s3;
		parser.addArgument("pet");
		parser.addArgument("number");
		parser.addArgument("rainy");
		parser.addArgument("bathrooms");
		addValues(se);
	}

	public void addArgument(String arg)
	{
		parser.addArgument(arg);
	}
	
	public void addValues(String str)
	{
		try{
			parser.parse(str);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){}
	}
	

	public String get(String s)
	{
		return parser.getArgumentValue(s.toLowerCase()).toString();
	}
}