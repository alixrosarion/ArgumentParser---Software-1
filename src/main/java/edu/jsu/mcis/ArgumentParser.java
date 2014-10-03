package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private ArrayList<String> argumentList;
	private ArrayList<String> argumentValue;
	private String errorMessage;
	private String help;
	private String program;
	
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
	public String wrongArgument(){
		return errorMessage;
	}
	
	public void parse(String str) throws NotEnoughArgsException, TooManyArgsException
	{
		//errorMessage = "";
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		if(argumentList.size() > argumentValue.size()){
			throw new NotEnoughArgsException();
		}
		
		else if (argumentList.size() < argumentValue.size()){
			///errorMessage = "Not Enough";
			throw new TooManyArgsException();
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
}