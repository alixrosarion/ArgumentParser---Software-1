import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcXML
{
	public static void main(String [] args) 
	{
		XMLParser parser = new XMLParser("arguments.xml");
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}try{
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

