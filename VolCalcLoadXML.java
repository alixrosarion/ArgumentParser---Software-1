import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcXML
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = XMLParser.createArgumentParser("test.xml");
		parser.addProgram("VolCalc","Calculates the volume of an object");
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}try{
			parser.parse(read);
			try{
            float length = parser.getArgumentValue("length");
			float height = parser.getArgumentValue("height");
			float width = parser.getArgumentValue("width");
			System.out.println("Volume is " + length * width * height);
			} catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

