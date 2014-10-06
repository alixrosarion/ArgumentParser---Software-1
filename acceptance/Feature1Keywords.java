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
	
	public void addValue(String arg)
	{
		parser.addArgumentValue(arg);
	}
	
	public String getLength(String args)
	{
		return parser.getArgumentValue(args);
	}
}