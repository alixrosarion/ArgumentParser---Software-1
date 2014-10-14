package edu.jsu.mcis;

import java.util.*;

public class ArgumentParser
{
	private List <Argument> ourList;
	private String unmatched;
	private String help;
	private String program;
	
	public ArgumentParser()
	{
		ourList = new ArrayList<Argument>();
		unmatched ="";
	}
	
	public int getSize()
	{
		return ourList.size();
	}
	
	public void addArgument(String str)
	{
		ourList.add(new Argument(str));

	}
	
	public void addArgument(String title, String type)
	{
		ourList.add(new Argument(title));
		ourList.get(ourList.indexOf(new Argument(title))).setType(type);
	}
	
	public void addArgument(String title, String type, String description)
	{
		ourList.add(new Argument(title));
		ourList.get(ourList.indexOf(new Argument(title))).setType(type);
		ourList.get(ourList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public void addArgumentValue(Object o, int index)
	{
		if(ourList.get(index).getType().equals("Integer"))
			try{
				o =Integer.parseInt(o.toString());
			}catch (Exception a){}
		else if(ourList.get(index).getType().equals("Boolean"))
			try{
				o =Boolean.parseBoolean(o.toString());
			}catch (Exception b){}
		else if(ourList.get(index).getType().equals("Float"))
			try{
				o =Float.parseFloat(o.toString());
			}catch (Exception c){}
			
		ourList.get(index).addValue(o);
	}
	
	public void addDescription(String title, String description)
	{
		ourList.get(ourList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public Object getArgumentDescription(String title)
	{
		return ourList.get(ourList.indexOf(new Argument(title))).getDescription();
	}
	
	public Object getArgumentValue(String title)
	{
		return ourList.get(ourList.indexOf(new Argument(title))).getValue();
	}
	
	public String getArgumentType(String title)
	{
		return ourList.get(ourList.indexOf(new Argument(title))).getType();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException//, NoSuchElementException
	{
		
		Scanner scan = new Scanner(str);
		program = scan.next();
		
		int countArgValues = 0;
		if (str.contains("-h"))
		{
			getHelpText();
		}
		else
		{
			unmatched = "unrecognised arguments: ";
			while(scan.hasNext())
			{
					if(countArgValues <ourList.size())
					{
						addArgumentValue(scan.next(), countArgValues);
					}
					else
					{
						
							unmatched += scan.next() + " "; 
					}
					
						countArgValues++;
			}
			if (unmatched != "")
				unmatched = unmatched.substring(0, unmatched.length() -1);
				
			if(ourList.size() > countArgValues){
				unmatched = "the following arguments are required: ";
				for(int k = countArgValues; k< ourList.size(); k++)
				{
					if( k == ourList.size() -1)
							unmatched += ourList.get(k).getTitle();
					else
						unmatched += ourList.get(k).getTitle() + " ";
				}
				throw new NotEnoughArgValuesException(unmatched);
			}
			
			else if (ourList.size() < countArgValues){
				
					throw new TooManyArgValuesException(unmatched);
			}
		}
		
	}
	public String getHelpText()
	{
		String argumentTitles = "";
		String description = "";
		for (Argument a : ourList)
		{
			argumentTitles += a.getTitle() + " ";
			description += a.getTitle() + "\t\t"+a.getDescription() + "\n";
			
		}
		help = "usage: java " + program + " " + argumentTitles + "\nCalculate the volume of a box\nPositional Arguments:\n" + description;
		
		return help;
	}
	
	public static void main(String [] args) 
	{
		System.out.println("Inside main");
		ArgumentParser parser = new ArgumentParser();
		parser.addArgument("length");
		parser.addArgument("width");
		parser.addArgument("height");
		String input = "VolCalc 7 2";
		try{
			parser.parse(input);
		}catch(TooManyArgValuesException | NotEnoughArgValuesException e){
			System.out.println(e);
			System.out.println("Inside catch");
		}
	}
}