package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private ArrayList<String> argumentList;
	private ArrayList<Object> argumentValue;
	private ArrayList<String> argumentType;
	private String help;
	private String program;
	private String unmatched;
	
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<String>();
		argumentValue = new ArrayList<Object>();
		argumentType = new ArrayList<String>();
	}
	public void addArgument(String str)
	{
		argumentList.add(str);
	}
	
	public void addArgumentValue(String str){
		argumentValue.add(str);
	}
	public void addArgumentType(String str){
		argumentType.add(str);
	}
	
	public int getNumArguments()
	{
		return argumentList.size();
	}
	
	public String getArgumentValue(String str)
	{
		return argumentValue.get(argumentList.indexOf(str)).toString();
	}
	
	public String getArgumentType(String str) {
		String returner = "";
		addArgumentValue(str);
		returner = argumentType.get(argumentValue.indexOf(str));
		return returner;
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
		 if( str.contains("-h")){
			setHelpText();
		}
		else
		{
		while(scan.hasNext())
		{
			addArgumentValue(scan.next());
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
	
	public void parseType(String str) throws NotEnoughArgValuesException, TooManyArgValuesException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		boolean boolTester = false;
		int intTester = 0;
		float floatTester = 0;	
		while(scan.hasNext())
		{
			String extra = getArgumentType(scan.next());
			System.out.println(extra);
			try {
				argumentValue.remove(0);
				if (getArgumentType(scan.match().toString()) == "Boolean"){
					boolTester = Boolean.parseBoolean(scan.match().toString());
					//System.out.println(boolTester);
					argumentValue.add(boolTester);
				}
			}
			catch (Exception b){
				try {
					if(getArgumentType(scan.match().toString()) == "Float"){
						floatTester = Float.parseFloat(scan.match().toString());
						//System.out.println(floatTester);
						argumentValue.add(floatTester);
					}
				}
				catch (Exception f) {
					try {
						if(getArgumentType(scan.match().toString()) == "Integer"){
							intTester = Integer.parseInt(scan.match().toString());
							//System.out.println(intTester);
							argumentValue.add(intTester);
						}
					}
					catch (Exception i) {
						try {
							if(getArgumentType(scan.match().toString()) == "String"){;
								argumentValue.add(scan.match().toString());
							}
						}
						catch (Exception s) {}
					}
				}
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
	
	public String getHelpText()
	{
		return help;
	}

	
	public String setHelpText(){
		String argumentString = "";
		for (String s: argumentList){
			argumentString += s + " ";
		}
		help = "usage: java " + program + " " +argumentList.get(0) + " "+ argumentList.get(1)+" "+argumentList.get(2)+ " "+"\nCalculate the volume of a box\nPositional Arguments:\nlength\t\tthe length of the box\n width\t\tthe width of the box\n height\t\tthe height of the box";
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
		tester.parse("VolCalc 7");
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){
			System.out.println(tester.getUnmatched());
		}
	}*/
}