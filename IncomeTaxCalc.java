import edu.jsu.mcis.*;

import java.util.*;
@SuppressWarnings("unchecked")
public class IncomeTaxCalc
{
	public static void main(String [] args) 
	{
		ArgumentParser parser = new ArgumentParser();
		String read = "";
		for(String arg:args){
			read += arg + " ";
		}
		
		parser.addArgument("rate", CommandLineArgument.Type.Float, "the income tax rate");
		parser.addArgument("income", CommandLineArgument.Type.Float, "the annual salary");
		parser.addProgram("IncomeTaxCalc","Calculates income tax");
		parser.addOptionalArgument("-h", 0);
		
		try{
			parser.parse(read);
			try{
			float rate = parser.getArgumentValue("rate");
			float income = parser.getArgumentValue("income");
            float taxesPaid = rate * income;
			System.out.println("Income taxes paid: $" + taxesPaid);
			}
			catch(NullPointerException e){}
			System.exit(1);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}

