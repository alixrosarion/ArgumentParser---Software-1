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
	
	public void addArgument(String title, CommandLineArgument.DataType type)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setDataType(type);
	}
	
	public void setShortOption(String title, String str)
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setShort(str);
	}
	
	public String getShortOption(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
	}
	
	public void addOptionalArgument(String title, CommandLineArgument.DataType type)
	{
		argumentList.add(new OptionalArgument(title));
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDataType(type);
		countOptionalArguments++;
	}
		
	public void addOptionalArgument(String title)
	{
		argumentList.add(new OptionalArgument(title));
		countOptionalArguments++;
	}
		
	public void setValue(Object o, int index) throws IncorrectTypeException
	{
		incorrectType = program + ".java: error: argument ";
			if(argumentList.get(index).getDataType() == CommandLineArgument.DataType.Integer)
			{
				try{
					o =Integer.parseInt(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid integer value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getDataType() == CommandLineArgument.DataType.Boolean)
			{
				if(Boolean.parseBoolean(o.toString())){
					o =Boolean.parseBoolean(o.toString());
				} else{
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid boolean value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getDataType() == CommandLineArgument.DataType.Float)
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
	
	public void setDescription(String title, String description)
	{
		if(argumentList.contains(new Argument(title)))
			argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
		else if(argumentList.contains(new OptionalArgument(title)))
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDescription(description);
	}
	
	public String getDescription(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
		else
			return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
	}
	
	public <T> T getValue(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return (T)argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
		else
			return (T)argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
	}
	
<<<<<<< HEAD
	public void setDataType(String title, CommandLineArgument.DataType type)
	{
		if(argumentList.contains(new Argument(title)))
			argumentList.get(argumentList.indexOf(new Argument(title))).setDataType(type);
		else if(argumentList.contains(new OptionalArgument(title)))
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDataType(type);
	}
	
	public CommandLineArgument.DataType getDataType(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return argumentList.get(argumentList.indexOf(new Argument(title))).getDataType();
		else 
			return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDataType();
=======
	public CommandLineArgument.DataType getArgumentDataType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getDataType();
>>>>>>> FETCH_HEAD
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
	
	public void setNumberValues(String title, int number)
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumberValues(number);
	}
	
	public int getNumberValues(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getNumberValues();
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
					setValue(extra, countArgValues);
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
		String optionalArgumentTitles = "";
		String description = "";
		String description2 = "";
		for (CommandLineArgument a : argumentList)
		{	
			if (!a.getTitle().contains("-"))
			{
				argumentTitles += a.getTitle() + " ";
				description += a.getTitle() +" "+ a.getDataType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
			}
			else {
				optionalArgumentTitles += a.getTitle() + " ";
				description2 += a.getTitle() +" "+ a.getDataType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
			}
		}
		help = "usage: java " + program + " " + argumentTitles + "\r\n" + programDescription +"\r\nPositional Arguments:\r\n" + description + "\r\nOptional Arguments:\r\n" + description2;
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