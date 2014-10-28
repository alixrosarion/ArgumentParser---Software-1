package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private List <CommandLineArgument> argumentList;
	private String unmatched;
	private String help;
	private String program;
	private String programDescription;
	private String incorrectType;
	public int countOptionalArguments;
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<CommandLineArgument>();
		unmatched ="";
		incorrectType= "";
		countOptionalArguments = 0;
	}
	
	public int getSize()
	{
		return argumentList.size();
	}
	
	public void addProgram(String description)
	{
		programDescription = description;
	}
	
	public void addArgument(String str)
	{
		argumentList.add(new Argument(str));

	}
	
	public void addArgument(String title, CommandLineArgument.Type type)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
	}
	
	public void addArgument(String title, CommandLineArgument.Type type, String description)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public void addShortOpt(String title, String str)
	{
		OptionalArgument arg = new OptionalArgument(title);
		argumentList.get(argumentList.indexOf(arg)).setShort(str);
	}
	
	public String getShortOpt(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
	}
	
	public void addOptArg(String title, int numValues)
	{
		argumentList.add(new OptionalArgument(title));
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
		countOptionalArguments++;
	}
	
	public void addOptArg(String title, int numValues, CommandLineArgument.Type type, String description, Object defaultValue)
		{
			countOptionalArguments++;
			argumentList.add(new OptionalArgument(title));
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setType(type);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDescription(description);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(defaultValue);
		}
		
	public void addArgumentValue(Object o, int index) throws IncorrectTypeException
	{
		incorrectType = program + ".java: error: argument ";
			if(argumentList.get(index).getType() == CommandLineArgument.Type.Integer)
			{
				try{
					o =Integer.parseInt(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid integer value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getType() == CommandLineArgument.Type.Boolean)
			{
				try{
					o =Boolean.parseBoolean(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid boolean value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getType() == CommandLineArgument.Type.Float)
			{
				try{												o =Float.parseFloat(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid float value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}

		argumentList.get(index).addValue(o);
	}
	
	public void addDescription(String title, String description)
	{
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public Object getArgumentDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
	}
	
	public Object getArgumentValue(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
	}
	
	public CommandLineArgument.Type getArgumentType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getType();
	}
	
	public String getOptArg(String title, int numValues)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title, numValues))).getTitle();
	}
	
	public void addOptionalFlag(String title) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(true);
	}
	
	public void addOptionalValue(String title, String value) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(value);
	}
	
	public Object getDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
	}
		
	public Object getOptionalValue(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException, IncorrectTypeException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		int countArgValues = 0;
		unmatched = "unrecognised arguments: ";
		int numberValues = 0;
		while(scan.hasNext())
		{
			String extra  = scan.next();
			if (argumentList.contains(new OptionalArgument(extra)))
			{
				if (extra.equals("-h"))
				{
					System.out.println(getHelpText());
					System.exit(0);
				}
				else if(argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumValues() == 0)
				{
					addOptionalFlag(extra);
				}
				else
				{
					numberValues = argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumValues();
					for (int i = 0; i<numberValues; i++)
					{
						addOptionalValue(extra, scan.next());
					}	
				}
			}
			
			else
			{	
				if(countArgValues <argumentList.size() - countOptionalArguments)
				{
					addArgumentValue(extra, countArgValues);
				}
				else
				{
						unmatched += extra + " "; 
				}
				
				countArgValues++;
			}
		}
		if (unmatched != "")
			unmatched = unmatched.substring(0, unmatched.length() -1);
			
		if(argumentList.size() > countArgValues + countOptionalArguments){
			unmatched = "the following arguments are required: ";
			for(int k = countArgValues; k< argumentList.size(); k++)
			{
				if( k == argumentList.size() -1)
						unmatched += argumentList.get(k).getTitle();
				else
					unmatched += argumentList.get(k).getTitle() + " ";
			}
			throw new NotEnoughArgValuesException(unmatched);
		}
		
		else if (argumentList.size() - countOptionalArguments < countArgValues)
		{	
				throw new TooManyArgValuesException(unmatched);
		}	
	}
	public String getHelpText()
	{
		String argumentTitles = "";
		String description = "";
		for (CommandLineArgument a : argumentList)
		{	
			if (!a.getTitle().contains("-"))
			{
				argumentTitles += a.getTitle() + " ";
				description += a.getTitle() + "\t\t"+a.getDescription() + "\n";
			}
		}
		help = "usage: java " + program + " " + argumentTitles + "\n" + programDescription +"\nPositional Arguments:\n" + description;
		
		return help;
	}
}