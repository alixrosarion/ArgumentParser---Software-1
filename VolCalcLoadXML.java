import edu.jsu.mcis.*;

import java.util.*;

public class VolCalcLoadXML
{
	public static void main(String [] args) 
	{
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}try{
			ArgumentParser parser = XMLParser.createArgumentParser("arguments.xml");
			
			parser.addProgram("VolCalc","Calculates the volume of an object");			
			
			parser.parse(read);
			try{
            float length = parser.getValue("length");
			float height = parser.getValue("height");
			float width = parser.getValue("width");
			System.out.println("Volume is " + length * width * height);
			} catch(Exception e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

