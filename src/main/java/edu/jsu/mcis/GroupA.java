package edu.jsu.mcis;

import java.util.*;

public class GroupA
{
	public static void main(String [] args) 
	{
		
		while(true)
		{
			Scanner scan = new Scanner(System.in);
			try
			{
				String input = scan.nextLine();
				if( input.length() > 0)
				{				
					ArgumentParser parser = new ArgumentParser();
					parser.addProgram("Volcalc", "Calculates the volume of a box");
					parser.addArgument("length", "Integer","the length of the box");
					parser.addArgument("width","Float","the width of the box");
					parser.addArgument("height","Integer","the height of the box");
					parser.addOptArg("-h",0);
					try{
						parser.parse(input);
						System.out.println("Your String was well parsed");
					}catch(TooManyArgValuesException | NotEnoughArgValuesException e){e.printStackTrace();		}
					if(input.contains("-h"))
					{
						System.out.println();
						System.out.println(parser.getHelpText());
					}
				}
				
				else System.exit(1);
				
			}catch(NoSuchElementException e){}
		}
	}
}
