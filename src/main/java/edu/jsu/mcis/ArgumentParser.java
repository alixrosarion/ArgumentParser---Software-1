package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private ArrayList<String> argumentList;
	private ArrayList<String> argumentValue;
	private String errorMessage;
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<String>();
		argumentValue = new ArrayList<String>();
	}
	public void addArgument(String str)
	{
		argumentList.add(str);
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
	
	public void parse(String str)
	{
		Scanner scan = new Scanner(str);
		String program = scan.next();
		if(argumentList.size() == argumentValue.size() + 1){
			argumentValue.add(scan.next());
			errorMessage = "";
		}
		
		else if (argumentList.size() > argumentValue.size() + 1){
			errorMessage = "Not Enough Arguments!";
			System.out.println(errorMessage);
		}
		
		else{
			errorMessage = "Too Many Arguments! Extra one is " + scan.next();
			System.out.println(errorMessage);
		}
	}
}