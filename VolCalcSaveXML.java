import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcSaveXML
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		parser.addArgument("length", CommandLineArgument.Type.Float, "the length of the box");
		parser.addArgument("width", CommandLineArgument.Type.Float, "the width of the box");
		parser.addArgument("height", CommandLineArgument.Type.Float, "the height of the box");
		parser.addProgram("VolCalc","Calculates the volume of an object");
		parser.addOptionalArgument("-h", 0);
		
		for(String arg:args){
			read += arg + " ";
		}try{
			parser.parse(read);
			parser.writeToXMLFile("test.xml");
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

