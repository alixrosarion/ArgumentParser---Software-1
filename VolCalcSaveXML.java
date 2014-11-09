import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcSaveXML
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		String filename = args[0];
		parser.addArgument("length", CommandLineArgument.Type.Float, "the length of the box");
		parser.addArgument("width", CommandLineArgument.Type.Float, "the width of the box");
		parser.addArgument("height", CommandLineArgument.Type.Float, "the height of the box");
		parser.addProgram("VolCalc","Calculates the volume of an object");
		parser.addOptArg("-h", 0);
		
		for(String arg:args){
			if(!arg.contains(".xml")) read += arg + " ";
			
		}try{
			parser.parse(read);
			parser.writeToXMLFile(filename);
			try{
            float length = parser.getArgumentValue("length");
			float height = parser.getArgumentValue("height");
			float width = parser.getArgumentValue("width");
			System.out.println("Volume is " + length * width * height);
			} catch(Exception e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

