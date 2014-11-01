package edu.jsu.mcis;

import java.util.*;
import java.io.IOException;
import java.text.ParseException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ArgumentParser extends DefaultHandler
{
	private List <CommandLineArgument> argumentList;
	private String unmatched;
	private String help;
	public String program;
	public String programDescription;
	private String incorrectType;
	public int countOptionalArguments;
	private String tmpValue;
	private String tmpName;
	private boolean argCheck;
	private int optArgXML;
	
	public ArgumentParser()
	{
		argumentList = new ArrayList<CommandLineArgument>();
		unmatched ="";
		incorrectType= "";
		countOptionalArguments = 0;
	}
	
	public ArgumentParser(String file) 
	{
		argumentList = new ArrayList<CommandLineArgument>();
		unmatched ="";
		incorrectType= "";
		countOptionalArguments = 0;
		parseFile(file);
	}
	
	public void parseFile(String filename)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try{
			SAXParser parser = factory.newSAXParser();
			parser.parse(filename, this);
		}catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
		
	}
	
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("argument")) argCheck = true;
		else if (elementName.equalsIgnoreCase("optionalArgument")) argCheck = false;
    }
	
	@Override
    public void endElement(String s, String s1, String element) throws SAXException {
		if (argCheck)
		{
			if (element.equalsIgnoreCase("name")) {
				addArgument(tmpValue);
				tmpName = tmpValue;
			}
			if (element.equalsIgnoreCase("type")) {
				if(tmpValue.equals("Integer")) argumentList.get(argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Integer);
				
				else if(tmpValue.equalsIgnoreCase("String")) argumentList.get(argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.String);
				
				else if(tmpValue.equalsIgnoreCase("Float")) argumentList.get(argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Float);
				
				else if(tmpValue.equalsIgnoreCase("Boolean")) argumentList.get(argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Boolean);
				
				else argumentList.get(argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Unknown); 
			
			}
			if(element.equalsIgnoreCase("description")){
			   addDescription(tmpName, tmpValue);
			}
		}
		else
		{
			if (element.equalsIgnoreCase("name")) {
				addOptArg(tmpValue, 0);
				tmpName = tmpValue;

			}
			if (element.equalsIgnoreCase("numValues")) {
				optArgXML = Integer.parseInt(tmpValue);
				argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setNumValues(optArgXML);
			}
			if (element.equalsIgnoreCase("description"))
			{
				argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setDescription(tmpValue);
			}
			if (optArgXML > 0 )
			{
				if (element.equalsIgnoreCase("type")) {
					if(tmpValue.equals("Integer")) argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Integer);
					
					else if(tmpValue.equalsIgnoreCase("String")) argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.String);
					
					else if(tmpValue.equalsIgnoreCase("Float")) argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Float);
					
					else if(tmpValue.equalsIgnoreCase("Boolean")) argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Boolean);
					
					else argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Unknown); 
				}
				
				if (element.equalsIgnoreCase("value")) {
					argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).addValue(tmpValue);
				}
				
				if (element.equalsIgnoreCase("shortName")) {
					argumentList.get(argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setShort(tmpValue);
				}
			}
		}
    }
	
	@Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
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
	
	public void addArgument(String title, CommandLineArgument.Type type)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
	}
	
	public void addArgument(String title, CommandLineArgument.Type type, String description)
	{
		argumentList.add(new Argument(title));
		argumentList.get(argumentList.indexOf(new Argument(title))).setType(type);
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public void addShortOpt(String title, String str)
	{
		OptionalArgument arg = new OptionalArgument(title);
		argumentList.get(argumentList.indexOf(arg)).setShort(str);
	}
	
	public String getShortOpt(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getShort();
	}
	
	public void addOptArg(String title, int numValues)
	{
		argumentList.add(new OptionalArgument(title));
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
		countOptionalArguments++;
	}
	
	public void addOptArg(String title, int numValues, CommandLineArgument.Type type, String description, Object defaultValue)
		{
			countOptionalArguments++;
			argumentList.add(new OptionalArgument(title));
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setNumValues(numValues);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setType(type);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).setDescription(description);
			argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(defaultValue);
		}
		
	public void addArgumentValue(Object o, int index) throws IncorrectTypeException
	{
		incorrectType = program + ".java: error: argument ";
			if(argumentList.get(index).getType() == CommandLineArgument.Type.Integer)
			{
				try{
					o =Integer.parseInt(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid integer value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getType() == CommandLineArgument.Type.Boolean)
			{
				try{
					o =Boolean.parseBoolean(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid boolean value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}
			else if(argumentList.get(index).getType() == CommandLineArgument.Type.Float)
			{
				try{												o =Float.parseFloat(o.toString());
				} catch (NumberFormatException e) {
					incorrectType = incorrectType + argumentList.get(index).getTitle() + " invalid float value: " + o; 
					throw new IncorrectTypeException(incorrectType);
				}
			}

		argumentList.get(index).addValue(o);
	}
	
	public void addDescription(String title, String description)
	{
		argumentList.get(argumentList.indexOf(new Argument(title))).setDescription(description);
	}
	
	public Object getArgumentDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getDescription();
	}
	
	public <T> T getArgumentValue(String title)
	{
		if(argumentList.contains(new Argument(title)))
			return (T)argumentList.get(argumentList.indexOf(new Argument(title))).getValue();
		else
			return (T)argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getValue();
	}
	
	public CommandLineArgument.Type getArgumentType(String title)
	{
		return argumentList.get(argumentList.indexOf(new Argument(title))).getType();
	}
	
	public String getOptArg(String title, int numValues)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title, numValues))).getTitle();
	}
	
	public void addOptionalFlag(String title) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(true);
	}
	
	public void addOptionalValue(String title, String value) 
	{
		argumentList.get(argumentList.indexOf(new OptionalArgument(title))).addValue(value);
	}
	
	public Object getDescription(String title)
	{
		return argumentList.get(argumentList.indexOf(new OptionalArgument(title))).getDescription();
	}
	
	public String getUnmatched()
	{
		return unmatched;
	}
	
	public void parse(String str) throws NotEnoughArgValuesException, TooManyArgValuesException, IncorrectTypeException
	{
		
		Scanner scan = new Scanner(str);
		
		int countArgValues = 0;
		unmatched = "unrecognised arguments: ";
		int numberValues = 0;
		while(scan.hasNext())
		{
			String extra  = scan.next();
			if (argumentList.contains(new OptionalArgument(extra)))
			{
				if (extra.equals("-h"))
				{
					System.out.println(getHelpText());
				}
				else if(argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumValues() == 0)
				{
					addOptionalFlag(extra);
				}
				else
				{
					numberValues = argumentList.get(argumentList.indexOf(new OptionalArgument(extra))).getNumValues();
					for (int i = 0; i<numberValues; i++)
					{
						addOptionalValue(extra, scan.next());
					}	
				}
			}
			
			else
			{	
				if(countArgValues <argumentList.size() - countOptionalArguments)
				{
					addArgumentValue(extra, countArgValues);
				}
				else
				{
						unmatched += extra + " "; 
				}
				
				countArgValues++;
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
		String description = "";
		for (CommandLineArgument a : argumentList)
		{	
			if (!a.getTitle().contains("-"))
			{
				argumentTitles += a.getTitle() + " ";
				description += a.getTitle() + "\t\t"+a.getDescription() + "\n";
			}
		}
		help = "usage: java " + program + " " + argumentTitles + "\n" + programDescription +"\nPositional Arguments:\n" + description;
		
		return help;
	}
}