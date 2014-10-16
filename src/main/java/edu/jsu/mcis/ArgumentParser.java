package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private List <Argument> argumentList;
	private List <OptionalArgument> optionalList;
	private String unmatched;
	private String help;
	private String program;
	private String programDescription;
	private String optionalValue;
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<Argument>();
		optionalList = new ArrayList<OptionalArgument>();
		unmatched ="";
	}
	
	public int getSize()
	{
		return argumentList.size();
	}
	
	public void addProgram(String program, String description)
	{
		programDescription = description;
	}
	
	public void addArgument(String str)
	{
		argumentList.add(new Argument(str));

	}
	
	public void addArgument(String title, String type)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
	}
	
	public void addArgument(String title, String type, String description)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public void addOptArg(String title, int numValues)
	{
		optionalList.add(new OptionalArgument(title));
		optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
	}
	
	public void addOptArg(String title, int numValues, String type, String description, Object defaultValue)
		{
			optionalList.add(new OptionalArgument(title));
			optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
			optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setType(type);
			optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setDescription(description);
			optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setDefaultValue(defaultValue);
		}
		
	public void addArgumentValue(Object o, int index)
	{
		if(argumentList.get(index).getType().equals("Integer"))
			try{
				o =Integer.parseInt(o.toString());
			}catch (Exception a){}
		else if(argumentList.get(index).getType().equals("Boolean"))
			try{
				o =Boolean.parseBoolean(o.toString());
			}catch (Exception b){}
		else if(argumentList.get(index).getType().equals("Float"))
			try{
				o =Float.parseFloat(o.toString());
			}catch (Exception c){}
			
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
	
	public String getArgumentType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getType();
	}
	
	public String getOptArg(String title, int numValues)
	{
		return optionalList.get(optionalList.indexOf(new OptionalArgument(title, numValues))).getTitle();
	}
	
	public void addOptionalFlag(String title) 
	{
		optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setDefaultValue(true);
	}
	
	public void addOptionalValue(String title, String value) 
	{
		optionalList.get(optionalList.indexOf(new OptionalArgument(title))).setDefaultValue(value);
	}
		
	public Object getDefaultValue(String title)
	{
		return optionalList.get(optionalList.indexOf(new OptionalArgument(title))).getDefaultValue();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public String getOptionalValue()
	{
		return optionalValue;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		int countArgValues = 0;
		unmatched = "unrecognised arguments: ";
		while(scan.hasNext())
		{
			String extra  = scan.next();
			if (optionalList.contains(new OptionalArgument(extra,2)))  //optionalList.get(optionalList.indexOf(new OptionalArgument(extra, 0))))
			{
				if (extra.equals("-h"))
				{
					getHelpText();
				}
				else if(optionalList.get(optionalList.indexOf(new OptionalArgument(extra))).getNumValues() == 0)
				{
					addOptionalFlag(extra);
				}
				else
				{
					int numberValues = optionalList.get(optionalList.indexOf(new OptionalArgument(extra, 9))).getNumValues();
					for (int i = 0; i<numberValues; i++)
					{
						optionalValue = scan.next();
					}	
				}
			}
			
			else
			{	
				if(countArgValues <argumentList.size())
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
			
		if(argumentList.size() > countArgValues){
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
		
		else if (argumentList.size() < countArgValues){
			
				throw new TooManyArgValuesException(unmatched);
		}		
	}
	public String getHelpText()
	{
		String argumentTitles = "";
		String description = "";
		for (Argument a : argumentList)
		{
			argumentTitles += a.getTitle() + " ";
			description += a.getTitle() + "\t\t"+a.getDescription() + "\n";
			
		}
		help = "usage: java " + program + " " + argumentTitles + "\n" + programDescription +"\nPositional Arguments:\n" + description;
		
		return help;
	}
	
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		String input = "VolCalc 7 2";
		try{
			parser.parse(input);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){e.printStackTrace();		}
	}
}