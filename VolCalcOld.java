import edu.jsu.mcis.*;

import java.util.*;

public class VolCalcOld
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
		parser.addProgram("VolCalc","Calculates the volume of an object");
		
		parser.addArgument("length", CommandLineArgument.DataType.Float);
		parser.setDescription("length", "the length of the object");
		
		parser.addArgument("width", CommandLineArgument.DataType.Float);
		parser.setDescription("width", "the width of the object");
		
		parser.addArgument("height", CommandLineArgument.DataType.Integer);
		parser.setDescription("height", "the height of the object");
		
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.setNumberValues("type", 1);		
		parser.setDescription("type", "Shape of object to be calculated");
		parser.setShortOption("type", "t");
		parser.setDefaultValue("type", "box");
		
		try{
			parser.parse(read);
			try{
            float length = parser.getValue("length");
			float width = parser.getValue("width");
			int height = parser.getValue("height");
			
			if(parser.getValue("type").toString().equalsIgnoreCase("box"))
			{
				System.out.println("Volume of the " +parser.getValue("type") + " is " +  height * width * length);
			}
			else if(parser.getValue("type").toString().equalsIgnoreCase("pyramid"))
			{
				System.out.println("Volume of the " + parser.getValue("type") + " is " + (height * width * length) / 3 );
			}
			else if(parser.getValue("type").toString().equalsIgnoreCase("sphere"))
			{
				System.out.println("Volume of the " +  parser.getValue("type") + " is " +length * width * Math.pow(height, 3));
			}
			else {
				System.out.println("Volume is " + length * width * height);
			}
			}catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

