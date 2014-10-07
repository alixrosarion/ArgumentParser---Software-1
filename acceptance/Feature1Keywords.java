import edu.jsu.mcis.*;

public class Feature1Keywords
{
	private ArgumentParser parser;
	
	public void startProgram ()
	{
		parser = new ArgumentParser();
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
	
	public String getArgumentValue(String args)
	{
		return parser.getArgumentValue(args).toString();
	}
}