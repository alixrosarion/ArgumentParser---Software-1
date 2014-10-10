package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private List<String> argumentList;
	private List<Object> argumentValue;
	private List<String> argumentType;
	private List<String> optArguments;
	private List<String> argumentDescription;
	private String help;
	private String program;
	private String unmatched;
	private String objectType;
	
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<String>();
		argumentValue = new ArrayList<Object>();
		argumentType = new ArrayList<String>();
		optArguments = new ArrayList<String>();
		argumentDescription = new ArrayList<String>();
	}
	public void addArgument(String str)
	{
		argumentList.add(str);
	}
	
	public void addArgumentValue(Object  str){
		argumentValue.add(str);
	}
	
	public void addArgumentType(String str){
		argumentType.add(str);
	}
	
	public void addArgumentDescription(String str)
	{
		argumentDescription.add(str);
	}
	
	public void addOptArg(String str, int value)
	{
		optArguments.add(str);
	}
	
	public int getNumArguments()
	{
		return argumentList.size();
	}
	
	public int getNumberOfOpts()
	{
		return optArguments.size();
	}
	
	public Object getArgumentValue(String str)
	{
		return argumentValue.get(argumentList.indexOf(str));
	}
	
	public boolean getOptArgs(String str)
	{	
		return optArguments.contains(str);
	}
	
	public String getArgumentDescription(String str)
	{
		return argumentDescription.get(argumentList.indexOf(str));
	}
	
	public String getArgumentType(String str) {
		return argumentType.get(argumentList.indexOf(str));
	}
	
	public String getObjectType()
	{
		return objectType;
	}
	
	
	public void storeUnmatched()
	{
		unmatched = "";
		if(argumentList.size() > argumentValue.size())
		{
			for (int i= argumentValue.size(); i< argumentList.size();i++)
			{
				unmatched += argumentList.get(i);
			}
		}
		else if (argumentList.size() < argumentValue.size())
		{
			for (int i= argumentList.size(); i< argumentValue.size();i++)
			{
				unmatched += argumentValue.get(i);
			}
		}
	}
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		while(scan.hasNext())
		{
			String extra = scan.next();
			if(optArguments.contains(extra)){
				if(extra.equals("-h"))
				{
					getHelpText();
				}
				
				else
				{
					objectType = scan.next();
				}
			}
			else
			{
				addArgumentValue(extra);
			}
		}
		if(argumentList.size() > argumentValue.size()){
			storeUnmatched();
			throw new NotEnoughArgValuesException();
		}
		
		else if (argumentList.size() < argumentValue.size()){
			storeUnmatched();
			throw new TooManyArgValuesException();
		}
	}
	
	public void parseType(String str) throws NotEnoughArgValuesException, TooManyArgValuesException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		if( str.contains("-h")){
			getHelpText();
		}
		else
		{
			boolean boolTester = false;
			int intTester = 0;
			float floatTester = 0;
			int count = 0;
			while(scan.hasNext() && count < argumentType.size())
			{
				String extra = argumentType.get(count);
				try {
						if(extra.equals("Integer")){
							intTester = Integer.parseInt(scan.next().toString());
							argumentValue.add(intTester);
						}
					}catch (Exception in) {}
					
				try {
					if (extra.equals("Boolean")){
						boolTester = Boolean.parseBoolean(scan.next().toString());
						argumentValue.add(boolTester);
					}
				}
				catch (Exception b) {}
				
				try {
					if(extra.equals("Float"))
						{
							floatTester = Float.parseFloat(scan.next().toString());
							argumentValue.add(floatTester);
						}
					}catch (Exception f) {}
							try {
								if(extra.equals("String")){
									argumentValue.add(scan.next());
								} 
							}
					catch (Exception s) {}
				count ++;
			}
			if(argumentList.size() > argumentValue.size()){
				storeUnmatched();
				throw new NotEnoughArgValuesException();
			}
			
			else if (argumentList.size() < argumentValue.size()){
				storeUnmatched();
				throw new TooManyArgValuesException();
			}
		}
	}
	
	public String getHelpText(){
		String argumentString = "";
		String description = "";
		for (int i = 0; i<argumentList.size(); i++){
			argumentString += argumentList.get(i) + " ";
			description += argumentList.get(i) + "\t\t" + argumentDescription.get(i) + "\n";
		}
		help = "usage: java " + program + " " + argumentString + "\nCalculate the volume of a box\nPositional Arguments:\n" + description;
		return help;
		
	}
	/*public static void main(String [] args)
	{
		ArgumentParser tester = new ArgumentParser();
		String input = "";
		for (int i=0; i<args.length; i++)
		{
			input += args[i] + " ";
		}
		try{
		tester.parse(input);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){
			System.out.println(tester.getUnmatched());
		}
	}*/
}