import edu.jsu.mcis.*;

import java.util.*;

public class VolCalcRestrictedValues
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
		parser.setShortOption("type", "t");
		
		parser.setRestricted("type","sphere","pyramid");
		parser.setRestricted("length", 6, 4);
		
		try{
			parser.parse(read);
			try{
            int length = parser.getValue("length");
			int height = parser.getValue("height");
			float width = parser.getValue("width");
			
			if(parser.getValue("type").toString().equalsIgnoreCase("pyramid"))
			{
				System.out.println("Volume of the " + parser.getValue("type") + " is " +(height * width * length) / 3 );
			}
			else if(parser.getValue("type").toString().equalsIgnoreCase("sphere"))
			{
				System.out.println("Volume of the " + parser.getValue("type") + " is " +length * width * Math.pow(height, 3));
			}
			}
			catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

