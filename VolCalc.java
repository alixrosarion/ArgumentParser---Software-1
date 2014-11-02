/*import edu.jsu.mcis.*;

import java.util.*;

public class GroupA
{
	public static void main(String [] args) 
	{
		Scanner scan = new Scanner(System.in);
		try
		{
			String input = scan.nextLine();
			if( input.length() > 0)
			{				
				ArgumentParser parser = new ArgumentParser();
				parser.addProgram("Volcalc","Calculates the volume of a box");
				parser.addArgument("length", CommandLineArgument.Type.Integer,"the length of the box");
				parser.addArgument("width",CommandLineArgument.Type.Float,"the width of the box");
				parser.addArgument("height",CommandLineArgument.Type.Integer,"the height of the box");
				parser.addOptArg("-h",0);
				parser.addOptArg("--type", 1, CommandLineArgument.Type.Integer, "Shape of object", 42);
				try{
					parser.parse(input);
					System.out.println("Your String was well parsed");
					System.out.println((Integer)parser.getArgumentValue("length") * (Integer)parser.getArgumentValue("height") * (Float)parser.getArgumentValue("width"));
					System.out.println(parser.getOptionalValue("--type"));
					System.exit(1);
				}
				catch(Exception e) { 
					e.printStackTrace();
				}
			}
			else System.exit(1);
		}catch(NoSuchElementException e){}
	}
}
*/
import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class VolCalc
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser("arguments.xml");
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
			parser.parse(read);
/*            int length = parser.getArgumentValue("length");
			int height = parser.getArgumentValue("height");
			float width = parser.getArgumentValue("width");*/
			System.out.println("Volume is " +length * width * height);

			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		
	}
}

