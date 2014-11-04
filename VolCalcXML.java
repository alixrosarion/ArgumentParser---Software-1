import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcXML
{
	public static void main(String [] args) 
	{
		XMLParser parser = new XMLParser("arguments.xml");
		parser.getArgumentParser().addProgram("VolCalc","Calculates the volume of an object");
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}try{
			parser.getArgumentParser().parse(read);
			try{
            float length = parser.getArgumentParser().getArgumentValue("length");
			float height = parser.getArgumentParser().getArgumentValue("height");
			float width = parser.getArgumentParser().getArgumentValue("width");
			System.out.println("Volume is " + length * width * height);
			} catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

