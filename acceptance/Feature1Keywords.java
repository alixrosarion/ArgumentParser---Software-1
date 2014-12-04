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
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);
		addValues(args);
	}
	
	public void startProgramWithShort(String args)
	{
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);
		parser.setShortOption("type", "t");
		addValues(args);
	}
	
	public void startProgramWithXml(String args)
	{
		parser = XMLParser.createArgumentParser("arguments.xml");
		addValues(args);
	}

	public void startProgramWithRestricted(String args)
	{
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);
		parser.setShortOption("type", "t");
		
		parser.addRestricted("type", "sphere");
		
		addValues(args);
	}
		public void startProgramWithRequired(String args)
	{
		parser = new ArgumentParser();
		
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);
		parser.setShortOption("type", "t");

		parser.setRequired("type");
		
		addValues(args);
	}
		public void startProgramWithMutuallyExclusive(String args)
	{
		parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		
		parser.addOptionalArgument("quiet", CommandLineArgument.DataType.String);
		parser.setNumberValues("quiet", 0);
		parser.setMutualGroup(1, "quiet");
		
		parser.addOptionalArgument("verbose", CommandLineArgument.DataType.String);
		parser.setNumberValues("verbose", 0);
		parser.setMutualGroup(2, "verbose");
		
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
		return parser.getValue(s.toLowerCase()).toString();
	}

	public String getRest(String s)
	{	
		return parser.checkRestricted(s.toLowerCase()).toString();
	}
	
	public String getReq(String s)
	{	
		return parser.getRequired(s) + "";
	}
	
	public String getGroup(int s)
	{
		return parser.getMutualGroup(s);
	}
}