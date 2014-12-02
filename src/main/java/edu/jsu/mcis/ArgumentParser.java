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
    
/**
 *Class constructor.
 */
    public ArgumentParser()
    {
        argumentList = new ArrayList<CommandLineArgument>();
        requiredOptionals = new ArrayList<CommandLineArgument>();
        unmatched ="";
        incorrectType= "";
		addOptionalArgument("help", CommandLineArgument.DataType.Boolean);
		setShortOption("help", "h");
    }

/**
 *Returns the size of the argumentList
 *
 *@return The size of the argument list
 */
    public int getSize()
    {
        return argumentList.size();
    }
	
/**
 *Sets the argument to only accept a set of restricted values. Multiple values can be
 *added(separated by a comma). 
 *
 *@param title The name of the argument
 *@param args The value(s) the argument is restricted to
 *@throws IncorrectTypeException If an incorrect data type is entered for the argument
 */	
	public void setRestricted(String title, Object ... args) throws IncorrectTypeException
	{
		int k =0;
		incorrectType = program + ".java: error: argument ";
		for(Object arg : args)
		{
			if(argumentList.contains(new Argument(title)))
			{
				try {
					Argument tmpArg = new Argument(title);
					argumentList.get(argumentList.indexOf(tmpArg)).setRestricted(arg);
					k = argumentList.indexOf(tmpArg);
				} catch (Exception e) {
					incorrectType += title +" invalid "+argumentList.get(k).getDataType().toString().toLowerCase() +
								" value: " + arg.toString();
					throw new IncorrectTypeException(incorrectType);
					
				}
			}
			else {
				try {
					OptionalArgument tmpArg = new OptionalArgument(title);
					argumentList.get(argumentList.indexOf(tmpArg)).setRestricted(arg);
					k = argumentList.indexOf(tmpArg);
				} catch (Exception e) {
				incorrectType += title +" invalid "+argumentList.get(k).getDataType().toString().toLowerCase() +
												" value: " + arg.toString();
				throw new IncorrectTypeException(incorrectType);

				}
			}
		}
	}
	
/**
 *@param title the title of the argument
 *
 *@return the values the argument is restricted to
 */	
	public String checkRestricted(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return argumentList.get(argumentList.indexOf(new Argument(title))).getRestricted();
		else
			return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getRestricted();
	}
    
/**
 *Adds the program name and the program's description.
 *
 *@param name the name of the program
 *@param description the description of the program
 */
    public void addProgram(String name, String description)
    {
        program = name;
        programDescription = description;
    }
    
/**
 *Adds a new argument to the list of arguments.
 *
 *@param str the name of the argument
 */
    public void addArgument(String str)
    {
        argumentList.add(new Argument(str));
    }
    
/**
 *Adds a new argument to the list of arguments and sets the data type
 *that argument can hold.
 *
 *@param title the name of the argument
 *@param type the data type of the argument
 */
    public void addArgument(String title, CommandLineArgument.DataType type)
    {
		Argument arg = new Argument(title);
		arg.setDataType(type);
		argumentList.add(arg);
    }
    
/**
 * 
 *
 *@param title the name of the argument
 *@param str the short name to be attached to the argument
 */
    public void setShortOption(String title, String str)
    {
        argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setShort(str);
    }
    
/**
 *Returns the short option associated with an argument, if it has one.
 *
 *@param title the name of the argument
 *@return the short option associated with the argument
 */
    public String getShortOption(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
    }
    
/**
 *Adds a new optional argument to the argument list with a specified data type.
 *
 *@param title the title of the optional argument
 *@param type the data type of the optional argument
 */
    public void addOptionalArgument(String title, CommandLineArgument.DataType type)
    {
		OptionalArgument arg = new OptionalArgument(title);
		arg.setDataType(type);
        argumentList.add(arg);
        countOptionalArguments++;
    }
    
/**
 *Adds a new optional argument to the argument list. 
 *
 *
 *@param title name of the optional argument
 */
    public void addOptionalArgument(String title)
    {
        argumentList.add(new OptionalArgument(title));
        countOptionalArguments++;
    }	
	
/**
 *
 *
 *@param title the name of the argument
 *@param value the desired default value of the argument
 */
	public void setDefaultValue(String title, String value)
	{
		setValue(title, value);
	}
	
/**
 *
 *
 *@param args the value(s) of the referenced optional argument or the next positional argument
 *@throws IncorrectTypeException if the data type does not match the data type of the argument
 *@throws IncorrectValueException if the value does not match the restricted values
 */
	public void setValue(String ... args) throws IncorrectTypeException, IncorrectValueException
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
						System.out.println("GOOD HERE");
						argumentList.get(k).setValue(args[0]);
						System.out.println("WE WERE WRONG");
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
    
/**
 *
 *
 *@param title the name of the argument
 *@param description the description of the argument
 */
    public void setDescription(String title, String description)
    {
        if(argumentList.contains(new Argument(title)))
            argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
        else if(argumentList.contains(new OptionalArgument(title)))
            argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDescription(description);
    }
    
/**
 *Returns the description associated with the argument
 *if that argument has one.
 *
 *@param title the name of the argument
 *@return the set description of the argument
 */
    public String getDescription(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
        else
            return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
    }
    
/**
 *
 *
 *@param title the name of the argument
 *@param <T> the type of the argument being referenced
 *@return the value held by argument
 */
    public <T> T getValue(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return (T)argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
        else
            return (T)argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
    }
    
/**
 *
 *
 *@param title the name of the argument
 *@return the data type associated with the argument
 */
    public CommandLineArgument.DataType getArgumentDataType(String title)
    {
        if(argumentList.contains(new Argument(title)))
            return argumentList.get(argumentList.indexOf(new Argument(title))).getDataType();
        else
            return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDataType();
    }
    
/**
 *
 *
 *@param title the name of the optional argument
 *@return the corresponding optional argument 
 */
    public String getOptionalArgument(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getTitle();
    }
    
/**
 *
 *
 *@param title the name of the argument
 *@param number the number of values the argument holds
 */
    public void setNumberValues(String title, int number)
    {
        argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumberValues(number);
    }
    
/**
 *
 *
 *@param title the name of the argument
 *@return the number of values associated with an argument
 */
    public int getNumberValues(String title)
    {
        return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getNumberValues();
    }

/**
 *
 *
 *
 *@param title the name of the argument
 */
	public void setRequired(String title)
	{
		OptionalArgument arg = new OptionalArgument(title);
		argumentList.get(argumentList.indexOf(arg)).setRequired();
		requiredOptionals.add(arg);
	}
	
/**
 *
 *
 *@param title the name of the argument
 *@return if the argument is required of not required
 */
	public boolean getRequired(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getRequired();
	}

/**
 *
 *
 *
 *@return the required arguments that did not receive a value
 */
    public String getUnmatched()
    {
        return unmatched;
    }

/**
 *
 *
 *
 *@return the name of the program
 */
    public String getProgramName()
    {
        return program;
    }
	
/**
 *
 *
 *
 *@return the description of the program
 */
    public String getProgramDescription()
    {
        return programDescription;
    }
	
/**
 *
 *
 *
 *@return the list of all arguments
 */
    public List getArgumentList()
    {
        return argumentList;
    }
    
/**
 *
 *
 *
 *@param str the string to be parsed
 *@throws NotEnoughArgValuesException if the amount of values parsed does not match the number of required arguments
 *@throws TooManyArgValuesException if the amount of values parsed exceeds the number of required arguments
 *@throws IncorrectTypeException if the data type of a value does not match the data type of the argument
 *@throws IncorrectValueException if the value does not match the restricted values of an argument
 */
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
								//else System.out.println(tempScan + " is not an accepted value!"); System.exit(1);
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
    
/**
 *
 *
 *
 *@return the formatted help text of the program and arguments
 */
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
    
/**
 *
 *
 *
 *@return the XML notation of the argument list
 */
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
    
/**
 *Takes the information of the arguments and optional arguments - title, description,
 *number of values, and the data type - and writes it to an XML file. Automatically 
 *generates the XML tags appropriate for each piece of data.
 *
 *@param filename the name of the file to write the XML code to
 */
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