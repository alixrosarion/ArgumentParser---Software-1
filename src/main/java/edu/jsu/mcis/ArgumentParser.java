package edu.jsu.mcis;

import java.util.*;
import java.io.*;

public class ArgumentParser
{
    public List <CommandLineArgument> argumentList;
    private String unmatched;
    private String help;
    private String program;
    private String programDescription;
    private String incorrectType;
    private int countOptionalArguments;
    private String output;
	private List <CommandLineArgument> requiredOptionals;
    
    public ArgumentParser()
    {
        argumentList = new ArrayList<CommandLineArgument>();
        requiredOptionals = new ArrayList<CommandLineArgument>();
        unmatched ="";
        incorrectType= "";
    }
    
    public int getSize()
    {
        return argumentList.size();
    }
    
    public void addProgram(String name, String description)
    {
        program = name;
        programDescription = description;
    }
    
    public void addArgument(String str)
    {
        argumentList.add(new Argument(str));
    }
    
    public void addArgument(String title, CommandLineArgument.DataType type)
    {
        argumentList.add(new Argument(title));
        argumentList.get(argumentList.indexOf(new Argument(title))).setDataType(type);
    }
    
    public void setShortOption(String title, String str)
    {
        argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setShort(str);
    }
    
    public String getShortOption(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
    }
    
    public void addOptionalArgument(String title, CommandLineArgument.DataType type)
    {
        argumentList.add(new OptionalArgument(title));
        argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDataType(type);
        countOptionalArguments++;
    }
    
    public void addOptionalArgument(String title)
    {
        argumentList.add(new OptionalArgument(title));
        countOptionalArguments++;
    }
    
    public void setValue(Object o, int index) throws IncorrectTypeException
    {
        incorrectType = program + ".java: error: argument ";
        try{
            argumentList.get(index).setValue(o);
        } catch (NumberFormatException e) {
            incorrectType = incorrectType + argumentList.get(index).getTitle() +
            " invalid "+argumentList.get(index).getDataType().toString().toLowerCase() +" value: " + o;
            throw new IncorrectTypeException(incorrectType);
        }
    }
	
	public void setValue(String ... args)
	{
		if(args.length ==1)
			argumentList.get(argumentList.indexOf(new OptionalArgument(args[0]))).setValue(true);
		else if (args.length ==2)
			argumentList.get(argumentList.indexOf(new OptionalArgument(args[0]))).setValue(args[1]);
	}
    
    public void setDescription(String title, String description)
    {
        if(argumentList.contains(new Argument(title)))
            argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
        else if(argumentList.contains(new OptionalArgument(title)))
            argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDescription(description);
    }
    
    public String getDescription(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
        else
            return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
    }
    
    public <T> T getValue(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return (T)argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
        else
            return (T)argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
    }
    
    public CommandLineArgument.DataType getArgumentDataType(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return argumentList.get(argumentList.indexOf(new Argument(title))).getDataType();
        else
            return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDataType();
    }
    
    public String getOptionalArgument(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getTitle();
    }
    
    public void setNumberValues(String title, int number)
    {
        argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumberValues(number);
    }
    
    public int getNumberValues(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getNumberValues();
    }

	public void setRequired(String title)
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setRequired();
		requiredOptionals.add(new OptionalArgument(title));
	}
	
	public boolean getRequired(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getRequired();
	}
    
    public String getUnmatched()
    {
        return unmatched;
    }
    public String getProgramName()
    {
        return program;
    }
    public String getProgramDescription()
    {
        return programDescription;
    }
    public List getArgumentList()
    {
        return argumentList;
    }
    
    public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException, IncorrectTypeException
    {
        Scanner scan = new Scanner(str);
		String tempOpt = "";
		String tempLine = "";
        int countArgValues = 0;
        unmatched = "unrecognised arguments: ";
        int numberValues = 0;
        while(scan.hasNext())
        {
            String extra  = scan.next();
			tempLine += extra;
			if (extra.equals("-h") || extra.equals("--help"))
	        {
	                System.out.println(getHelpText());
	            	return;
	       	}
            else if (extra.contains("-"))
            {
				for(int i =0; i < extra.length(); i++)
				{
					if (extra.charAt(i) != '-') tempOpt += extra.charAt(i);
				}
				if (argumentList.contains(new OptionalArgument(tempOpt)))
				{
	                if(argumentList.get(argumentList.indexOf(new OptionalArgument(tempOpt))).getNumberValues() == 0)
	                {
	                    setValue(tempOpt);
	                }
	                else
	                {
	                    numberValues = argumentList.get(argumentList.indexOf(new OptionalArgument(tempOpt))).getNumberValues();
	                    for (int i = 0; i<numberValues; i++)
	                    {
	                        setValue(tempOpt, scan.next());
	                    }
	                }
				}
            }
            else
            {
                if(countArgValues <argumentList.size() - countOptionalArguments)
                {
                    setValue(extra, countArgValues);
                }
                else
                {
                    unmatched += extra + " ";
                }
                
                countArgValues++;
            }
        }
		
		for(int i = 0; i<requiredOptionals.size(); i++)
		{
			if(!tempLine.contains(requiredOptionals.get(i).getTitle()))
			{
				unmatched = "the following arguments are required: ";
				for(int k = 0; k< requiredOptionals.size(); k++)
				{
					if( k == requiredOptionals.size() -1)
						unmatched += requiredOptionals.get(k).getTitle();
					else
						unmatched += requiredOptionals.get(k).getTitle() + " ";
				}
	            throw new NotEnoughArgValuesException(unmatched);
			}
		}
        
        if (unmatched != "")
            unmatched = unmatched.substring(0, unmatched.length() -1);
        
        if(argumentList.size() > countArgValues + countOptionalArguments){
            unmatched = "the following arguments are required: ";
            for(int k = countArgValues; k< argumentList.size(); k++)
            {
                if( k == argumentList.size() -1)
                    unmatched += argumentList.get(k).getTitle();
                else
                    unmatched += argumentList.get(k).getTitle() + " ";
            }
            throw new NotEnoughArgValuesException(unmatched);
        }
        
        else if (argumentList.size() - countOptionalArguments < countArgValues)
        {
            throw new TooManyArgValuesException(unmatched);
        }
    }
    
    public String getHelpText()
    {
        String argumentTitles = "";
        String optionalArgumentTitles = "";
        String description = "";
        String description2 = "";
        for (CommandLineArgument a : argumentList)
        {	
            if (!a.getTitle().contains("-"))
            {
                argumentTitles += a.getTitle() + " ";
                description += a.getTitle() +" "+ a.getDataType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
            }
            else {
                optionalArgumentTitles += a.getTitle() + " ";
                description2 += a.getTitle() +" "+ a.getDataType().toString().toLowerCase()+"\t\t"+a.getDescription() + "\r\n";
            }
        }
        help = "usage: java " + program + " " + argumentTitles + "\r\n" + programDescription +"\r\nPositional Arguments:\r\n" + description + "\r\nOptional Arguments:\r\n" + description2;
        return help;
    }
    
    public String getOutput()
    {
        output = "<?xml version=\"1.0\" encoding=\""+ "UTF-8" + "\"?>"+"\r\n<arguments>";
        for (int i = 0; i<getSize(); i++)
        {
            output += argumentList.get(i).toString();
        }
        output += "\r\n</arguments>";
        return output;
    }
    
    public void writeToXMLFile(String filename)
    {
        try
        {
            PrintWriter file = new PrintWriter(filename);
            file.write(getOutput());
            file.close();
        }catch(FileNotFoundException e){e.printStackTrace();}
    }
}