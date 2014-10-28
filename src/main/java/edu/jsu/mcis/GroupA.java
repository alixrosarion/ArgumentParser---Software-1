/*package edu.jsu.mcis;

import java.util.*;

public class GroupA
{
	public static void main(String [] args) 
	{
		Scanner scan = new Scanner(System.in);
		try
		{
			String input = scan.nextLine();
			if( input.length() > 0)
			{				
				ArgumentParser parser = new ArgumentParser();
				parser.addProgram("Calculates the volume of a box");
				parser.addArgument("length", CommandLineArgument.Type.Integer,"the length of the box");
				parser.addArgument("width",CommandLineArgument.Type.Float,"the width of the box");
				parser.addArgument("height",CommandLineArgument.Type.Integer,"the height of the box");
				parser.addOptArg("-h",0);
				parser.addOptArg("--type", 1, CommandLineArgument.Type.Integer, "whatever", 42);
				try{
					parser.parse(input);
					System.out.println("Your String was well parsed");
					System.out.println((Integer)parser.getArgumentValue("length") * (Integer)parser.getArgumentValue("height") * (Float)parser.getArgumentValue("width"));
					System.out.println(parser.getOptionalValue("--type"));
					System.exit(1);
				}
				catch(Exception e) { 
					e.printStackTrace();
				}
			}
			else System.exit(1);
		}catch(NoSuchElementException e){}
	}
}*/
package edu.jsu.mcis;

import java.util.*;

public class GroupA
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
		parser.addArgument("length", "Integer", "the length of the box");
		parser.addArgument("width", "Float", "the width of the box");
		parser.addArgument("height", "Integer", "the height of the box");
		parser.addOptArg("-h", 0);
		
		try{
			parser.parse(read);
			System.out.println("Volume: " + (Integer)parser.getArgumentValue("length") * (Float)parser.getArgumentValue("width") * (Integer)parser.getArgumentValue("height"));
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

