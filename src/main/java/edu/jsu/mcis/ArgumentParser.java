package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private ArrayList<String> argumentList;
	private ArrayList<String> argumentValue;
	private String help;
	private String program;
	private String unmatched;
	
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<String>();
		argumentValue = new ArrayList<String>();
	}
	public void addArgument(String str)
	{
		argumentList.add(str);
	}
	
	public void addArgumentValue(String str){
		argumentValue.add(str);
	}
	
	public int getNumArguments()
	{
		return argumentList.size();
	}
	
	public String getArgumentValue(String str)
	{
		return argumentValue.get(argumentList.indexOf(str));
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
		if(argumentList.size() > argumentValue.size()){
			storeUnmatched();
			throw new NotEnoughArgValuesException();
		}
		
		else if (argumentList.size() < argumentValue.size()){
			storeUnmatched();
			throw new TooManyArgValuesException();
		}
	}
	
	public String getArgumentType(String str){
		String type = "String";
		String value = "";
		if (argumentList.contains(str)){
			value = getArgumentValue(str).toString();
		}
		boolean boolTester;
		float floatNumber;
		int number;
		try {
			if (boolTester = Boolean.parseBoolean(value)){
				type = "Boolean";
			}
		}
		catch (Exception b){
			boolTester = false;
		}
		try {
			floatNumber = Float.valueOf(value);
			if(floatNumber == Float.parseFloat(value)){
				floatNumber = 0;
				type = "Float";
			}
		}
		catch (Exception f) {
			floatNumber = 0;
		}
		try {
			number = Integer.parseInt(value);
			if(number == floatNumber){
				number = 0;
				type = "Integer";
			}
			else{
				type = "Integer";
			}
		}
		catch (Exception i) {
			number = 0;
		}
		
		return type;
	}
	
	public String helpText(){
		String argumentString = "";
		for (String s: argumentList){
			argumentString += s + " ";
		}
		help = "usage: java " + program + " " + argumentString + "\n" + "Calculate the volume of a box\nPositional Arguments:\nlength\t\tthe length of the box\n width\t\tthe width of the box\n height\t\ttheheight of the box";
		return help;
		//no such elemnt
		
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