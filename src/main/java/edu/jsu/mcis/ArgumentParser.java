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
		addOptionalArgument("help", CommandLineArgument.DataType.Boolean);
		setShortOption("help", "h");
    }
    
    public int getSize()
    {
        return argumentList.size();
    }
	
	public void setRestricted(String title, Object ... args) throws IncorrectTypeException
	{
		int k =0;
		incorrectType = program + ".java: error: argument ";
		for(Object arg : args)
		{
			if(argumentList.contains(new Argument(title)))
			{
				try {
					argumentList.get(argumentList.indexOf(new Argument(title))).setRestricted(arg);
					k = argumentList.indexOf(new Argument(title));
				} catch (Exception e) {
					incorrectType += title +" invalid "+argumentList.get(k).getDataType().toString().toLowerCase() +
								" value: " + arg.toString();
					throw new IncorrectTypeException(incorrectType);
					
				}
			}
			else {
				try {
					argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setRestricted(arg);
					k = argumentList.indexOf(new OptionalArgument(title));
				} catch (Exception e) {
				incorrectType += title +" invalid "+argumentList.get(k).getDataType().toString().toLowerCase() +
												" value: " + arg.toString();
				throw new IncorrectTypeException(incorrectType);

				}
			}
		}
	}
	
	public String checkRestricted(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return argumentList.get(argumentList.indexOf(new Argument(title))).getRestricted();
		else
			return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getRestricted();
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
		OptionalArgument arg = new OptionalArgument(title);
		arg.setDataType(type);
        argumentList.add(arg);
        countOptionalArguments++;
    }
    
    public void addOptionalArgument(String title)
    {
        argumentList.add(new OptionalArgument(title));
        countOptionalArguments++;
    }	public void setValue(String ... args) throws IncorrectTypeException, IncorrectValueException
	{
		int k=0;
		incorrectType = program + ".java: error: argument ";
		try{
			if(argumentList.contains(new OptionalArgument(args[0])))
			{
				k = argumentList.indexOf(new OptionalArgument(args[0]));
				if(args.length == 1)
				{
					argumentList.get(k).setValue(true);
				}
				else if (args.length == 2)
				{
					argumentList.get(k).setValue(args[1]);
				}
			}
			else //It is an Argument, let s find the index.
				for (k =0; k<argumentList.size(); k++)
				{
					if (argumentList.get(k) instanceof Argument && argumentList.get(k).getValue() == null)
					{
						argumentList.get(k).setValue(args[0]);
						return;
					}
				}
		}catch(NumberFormatException | IncorrectValueException e){
			
			incorrectType += argumentList.get(k).getTitle() +
            " invalid "+argumentList.get(k).getDataType().toString().toLowerCase() +
			" value: ";
			if (args.length ==2)
				incorrectType+= args[1];
			else
				incorrectType += args[0];
				
			if( e instanceof NumberFormatException)
				throw new IncorrectTypeException(incorrectType);
			else
				throw new IncorrectValueException(incorrectType);
		 }
		 
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
    
    public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException, IncorrectTypeException, IncorrectValueException
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
			
            if (extra.startsWith("-"))
            {
				for(int i =0; i < extra.length(); i++)
				{
					if (extra.charAt(i) != '-') tempOpt += extra.charAt(i);
				}
				
				OptionalArgument tempArg = new OptionalArgument(tempOpt);
				if (argumentList.contains(tempArg))

				if (argumentList.contains(new OptionalArgument(tempOpt)))

				{			
	                if(argumentList.get(argumentList.indexOf(tempArg)).getNumberValues() == 0)
	                {
						 setValue(tempOpt);
	                }
	                else
	                {
	                    numberValues = argumentList.get(argumentList.indexOf(tempArg)).getNumberValues();
	                    for (int i = 0; i<numberValues; i++)
	                    {
							String tempScan = scan.next();
							if (tempArg.hasRestricted())
							{
								if(checkRestricted(tempOpt).contains(tempScan)) setValue(tempOpt, tempScan);
								else System.out.println(tempScan + " is not an accepted value!"); System.exit(1);
							}
							else setValue(tempOpt, tempScan);
	                    }
	                }
				}
            }
            else
            {
                if(countArgValues <argumentList.size() - countOptionalArguments)
                {
                    setValue(extra);
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
          
			for (int k =0; k<argumentList.size(); k++)
			{
				if (argumentList.get(k) instanceof Argument && argumentList.get(k).getValue() == null)
				{
                if( k == argumentList.size() -1)
                    unmatched += argumentList.get(k).getTitle();
                else
                    unmatched += argumentList.get(k).getTitle() + " ";
				}
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
            if ( a instanceof Argument)
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