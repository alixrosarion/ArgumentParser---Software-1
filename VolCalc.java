import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalc
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
		parser.addArgument("length", CommandLineArgument.Type.Integer, "the length of the box");
		parser.addArgument("width", CommandLineArgument.Type.Float, "the width of the box");
		parser.addArgument("height", CommandLineArgument.Type.Integer, "the height of the box");
		parser.addProgram("VolCalc","Calculates the volume of an object");
		parser.addOptArg("-h", 0);
		parser.addOptArg("--type", 1, CommandLineArgument.Type.String, "Shape of object to be calculated", "Box");
		
		try{
			parser.parse(read);
			try{
            //int length = parser.getArgumentValue("length");
			//int height = parser.getArgumentValue("height");
			//float width = parser.getArgumentValue("width");
			System.out.println("Volume is " + /*parser.getArgumentValue("length") * parser.getArgumentValue("width") **/ CommandLineArgument.getValue("height"));
			
			}
			catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

