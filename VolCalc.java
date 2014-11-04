import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalc
{
	public static void main(String [] args) 
	{
		XMLParser parser = new XMLParser("arguments.xml");
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
/*		parser.addArgument("length", CommandLineArgument.Type.Integer, "the length of the box");
		parser.addArgument("width", CommandLineArgument.Type.Float, "the width of the box");
		parser.addArgument("height", CommandLineArgument.Type.Integer, "the height of the box");
		parser.addOptArg("-h", 0);
		parser.addOptArg("--type", 1, CommandLineArgument.Type.String, "Shape of object to be calculated", "Box");*/
		
		try{
			parser.argPars.parse(read);
            float length = parser.argPars.getArgumentValue("length");
			float height = parser.argPars.getArgumentValue("height");
			float width = parser.argPars.getArgumentValue("width");
			System.out.println("Volume is " + length * width * height);

			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

