import edu.jsu.mcis.*;

import java.util.*;

public class VolCalcMutual
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
		
		parser.addOptionalArgument("type", CommandLineArgument.DataType.String);
		parser.addOptionalArgument("color", CommandLineArgument.DataType.String);
		parser.addOptionalArgument("shape", CommandLineArgument.DataType.String);
		parser.addOptionalArgument("paint", CommandLineArgument.DataType.String);
		parser.addOptionalArgument("verbose", CommandLineArgument.DataType.String);
		
		parser.setMutualGroup(1, "type", "color");
		parser.setMutualGroup(2, "paint", "shape");
		
		
		try{
			parser.parse(read);
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

