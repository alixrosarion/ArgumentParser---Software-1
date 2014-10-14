package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
<<<<<<< HEAD
	private List <Argument> argumentList;
	private String unmatched;
	private String help;
	private String program;
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<Argument>();
		unmatched ="";
	}
	
	public int getSize()
	{
		return argumentList.size();
=======
	private List<String> argumentList;
	private List<Object> argumentValue;
	private List<String> argumentType;
	private List<String> optArguments;
	private List<String> optArgumentsValue;
	private List<String> argumentDescription;
	private String help;
	private String program;
	private String unmatched;
	private boolean flag;
	
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<String>();
		argumentValue = new ArrayList<Object>();
		argumentType = new ArrayList<String>();
		optArguments = new ArrayList<String>();
		optArgumentsValue = new ArrayList<String>();
		argumentDescription = new ArrayList<String>();
		flag = false;
>>>>>>> origin/master
	}
	
	public void addArgument(String str)
	{
		argumentList.add(new Argument(str));

	}
	
<<<<<<< HEAD
	public void addArgument(String title, String type)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
=======
	public void addArgumentValue(Object  str)
	{
		argumentValue.add(str);
>>>>>>> origin/master
	}
	
	public void addArgument(String title, String type, String description)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
<<<<<<< HEAD
	public void addArgumentValue(Object o, int index)
=======
	public void addArgumentDescription(String str)
	{
		argumentDescription.add(str);
	}
	
	public void addOptArg(String str, int value)
	{
		optArguments.add(str);
		if(value == 0) 
		{
			flag = true;
		}
		else if(value > 0){
			for(int i = 0;i < value; i++){
				optArgumentsValue.add("");
			}
		}
	}
	
	public int getNumArguments()
>>>>>>> origin/master
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
	
<<<<<<< HEAD
	public void addDescription(String title, String description)
=======
	public int getNumberOfOpts()
	{
		return optArguments.size();
	}
	
	public Object getArgumentValue(String str)
>>>>>>> origin/master
	{
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
<<<<<<< HEAD
	public Object getArgumentDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
	}
	
	public Object getArgumentValue(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
	}
=======
	public boolean getOptArgs(String str)
	{	
		return optArguments.contains(str);
	}
	
	public String getArgumentDescription(String str)
	{
		return argumentDescription.get(argumentList.indexOf(str));
	}
	
	public String getArgumentType(String str) 
	{
		return argumentType.get(argumentList.indexOf(str));
	}
	
	public String getObjectType(String str)
	{
		return optArgumentsValue.get(optArguments.indexOf(str));
	}
	
	public boolean getOptArgumentFlag() 
	{
		return flag;
	}	
>>>>>>> origin/master
	
	public String getArgumentType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getType();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException//, NoSuchElementException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
<<<<<<< HEAD
		
		int countArgValues = 0;
		if (str.contains("-h"))
		{
=======
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
					int loc = optArguments.indexOf(extra);
					if(scan.hasNextInt()){
						addArgumentValue(scan.next());
						optArgumentsValue.set(loc, "box");
					}
					else{
						optArgumentsValue.set(loc, scan.next());
					}
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
>>>>>>> origin/master
			getHelpText();
		}
		else
		{
			unmatched = "unrecognised arguments: ";
			while(scan.hasNext())
			{
					if(countArgValues <argumentList.size())
					{
						addArgumentValue(scan.next(), countArgValues);
					}
					else
					{
						
							unmatched += scan.next() + " "; 
					}
					
						countArgValues++;
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
		
	}
	public String getHelpText()
	{
<<<<<<< HEAD
		String argumentTitles = "";
		String description = "";
		for (Argument a : argumentList)
		{
			argumentTitles += a.getTitle() + " ";
			description += a.getTitle() + "\t\t"+a.getDescription() + "\n";
			
		}
		help = "usage: java " + program + " " + argumentTitles + "\nCalculate the volume of a box\nPositional Arguments:\n" + description;
=======
		String argumentString = "";
		String description = "";
		for (int i = 0; i<argumentList.size(); i++){
			argumentString += argumentList.get(i) + " ";
			description += argumentList.get(i) + "\t\t" + argumentDescription.get(i) + "\n";
		}
		help = "usage: java " + program + " " + argumentString + "\nCalculate the volume of a box\nPositional Arguments:\n" + description;
		return help;
>>>>>>> origin/master
		
		return help;
	}
	
	/*public static void main(String [] args) 
	{
		System.out.println("Inside main");
		ArgumentParser parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		String input = "VolCalc 7 2";
		try{
			parser.parse(input);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){
			System.out.println(e);
			System.out.println("Inside catch");
		}
	}*/
}