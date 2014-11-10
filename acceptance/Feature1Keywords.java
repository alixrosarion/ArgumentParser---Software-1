import edu.jsu.mcis.*;

public class Feature1Keywords
{
	private ArgumentParser parser;
	
	public void startProgram (String args)
	{
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		addValues(args);
	}
	
	public void startProgramMixed(String args)
	{
		parser = new ArgumentParser();
		
		parser.addArgument("pet");
		parser.addArgument("number");
		parser.addArgument("rainy");
		parser.addArgument("bathrooms");
		addValues(args);
	}
	
	public void startProgramWithOptionals(String args) {
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		parser.addOptionalArgument("--type", 1);
		addValues(args);
	}
	
	public void startProgramWithShort(String args)
	{
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		parser.addOptionalArgument("-type", 1);
		parser.addShortOption("--type", "-t");
		addValues(args);
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