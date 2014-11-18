import edu.jsu.mcis.*;

import java.util.*;

public class VolCalcRequiredOptionals
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
		parser.addProgram("VolCalc","Calculates the volume of an object");
		
		parser.addArgument("length", CommandLineArgument.DataType.Integer);
		parser.setDescription("length", "the length of the box");
		
		parser.addArgument("width", CommandLineArgument.DataType.Float);
		parser.setDescription("width", "the width of the box");
		
		parser.addArgument("height", CommandLineArgument.DataType.Integer);
		parser.setDescription("height", "the height of the box");
		
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);		
		parser.setDescription("type", "Shape of object to be calculated");
		parser.setRequired("type");
		
		try{
			parser.parse(read);
			try{
            int length = parser.getValue("length");
			int height = parser.getValue("height");
			float width = parser.getValue("width");
			System.out.println("Volume is " + height * width * length);
			
			}
			catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

