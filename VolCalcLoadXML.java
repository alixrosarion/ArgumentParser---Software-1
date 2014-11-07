import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalcLoadXML
{
	//If you want this one to display the help info type java VolcalcLoadXML test.xml -h
	public static void main(String [] args) 
	{
		String read = "";
		String filename = args[0];
		for(String arg:args){
			if(!arg.contains(".xml"))
				read += arg + " ";
		}try{
			ArgumentParser parser = XMLParser.createArgumentParser(filename);
			parser.addProgram("VolCalc","Calculates the volume of an object");
			parser.addOptArg("-h", 0);
		
			parser.parse(read);
			try{
            //float length = parser.getArgumentValue("length");
			//float height = parser.getArgumentValue("height");
			//float width = parser.getArgumentValue("width");
			//System.out.println("Volume is " + length * width * height);
			} catch(Exception e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

