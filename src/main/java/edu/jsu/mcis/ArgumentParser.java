package edu.jsu.mcis;

import java.util.*;
import java.io.*;

public class ArgumentParser 
{
	public List <CommandLineArgument> argumentList;
	private String unmatched;
	private String help;
	private String program;
	private String programDescription;
	private String incorrectType;
	private int countOptionalArguments;
	private String output;
		
	public ArgumentParser()
	{
		argumentList = new ArrayList<CommandLineArgument>();
		unmatched ="";
		incorrectType= "";
	}
	
	public int getSize()
	{	
		return argumentList.size();
	}
	
	public void addProgram(String name, String description)
	{
		program = name;
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
	
	public void addShortOption(String title, String str)
	{
		OptionalArgument arg = new OptionalArgument(title);
		argumentList.get(argumentList.indexOf(arg)).setShort(str);
	}
	
	public String getShortOption(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
	}
	
	public void addOptionalArgument(String title, CommandLineArgument.Type type)
	{
		argumentList.add(new OptionalArgument(title));
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setType(type);
		countOptionalArguments++;
	}
		
	public void addOptionalArgument(String title)
	{
		argumentList.add(new OptionalArgument(title));
		countOptionalArguments++;
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
				if(Boolean.parseBoolean(o.toString())){
					o =Boolean.parseBoolean(o.toString());
				} else{
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid boolean value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getType() == CommandLineArgument.Type.Float)
			{
				try{
					o =Float.parseFloat(o.toString());
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
	
	public String getArgumentDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
	}
	
	public <T> T getArgumentValue(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return (T)argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
		else
			return (T)argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
	}
	
	public CommandLineArgument.Type getArgumentType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getType();
	}
	
	public String getOptionalArgument(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getTitle();
	}
	
	public void addOptionalFlag(String title) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(true);
	}
	
	public void addOptionalValue(String title, String value) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(value);
	}
	
	public String getDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	public String getProgramName()
	{
		return program;
	}
	public String getProgramDescription()
	{
		return programDescription;
	}
	public List getArgumentList()
	{
		return argumentList;
	}
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException, IncorrectTypeException
	{
		
		Scanner scan = new Scanner(str);
		int countArgValues = 0;
		unmatched = "unrecognised arguments: ";
		int numberValues = 0;
		while(scan.hasNext())
		{
			String extra  = scan.next();
			if (argumentList.contains(new OptionalArgument(extra)))
			{
				if (extra.equals("-h") || extra.equals("--help"))
				{
					System.out.println(getHelpText());
					return;
				}
				else if(argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumberValues() == 0)
				{
					addOptionalFlag(extra);
				}
				else
				{
					numberValues = argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumberValues();
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
		String description2 = "";
		for (CommandLineArgument a : argumentList)
		{	
			if (!a.getTitle().contains("-"))
			{
				argumentTitles += a.getTitle() + " ";
				description += a.getTitle() +" "+ a.getType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
			}
			/*else {
				argumentTitles += a.getTitle() + " ";
				description += a.getTitle() +" "+ a.getType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
			}*/
		}
		help = "usage: java " + program + " " + argumentTitles + "\r\n" + programDescription +"\r\nPositional Arguments:\r\n" + description /*+ "\r\nOptional Arguments:\r\n" + description2*/;
		return help;
	}
	public String getOutput()
	{
		output = "<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>"+"\r\n<arguments>";
		for (int i = 0; i<getSize(); i++)
		{
			output += argumentList.get(i).toString();
		}
		output += "\r\n</arguments>";
		System.out.println(output);
		return output;
	}
	public void writeToXMLFile(String filename)
	{
		try
		{
			PrintWriter file = new PrintWriter(filename);
			file.write(getOutput());
			file.close();
		}catch(FileNotFoundException e){e.printStackTrace();}
	}
}