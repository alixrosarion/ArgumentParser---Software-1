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

public class XMLParser extends DefaultHandler
{
	private String tmpName;
	private boolean argCheck;
	private CommandLineArgument.DataType optArgXML;
	private String tmpValue;
	private ArgumentParser argPars;
	private List <CommandLineArgument> argumentList;
	private int optCount;
	
/**
 *
 *
 *@param file the XML file (with it's extension) the arguments and their data to be loaded from
 *@return i
 */
	public static ArgumentParser createArgumentParser(String file)
	{
		XMLParser p = new XMLParser(file);
		return p.getArgumentParser();
	}
	
/**
 *Class constructor with a specified XML file to load the argument
 *data from.
 *
 *@param filename the name of the file to be parsed. Must be within same folder
 *                as the parse and must include the file extension (.xml)
 */
	private XMLParser(String filename)
	{
		argPars = new ArgumentParser();	
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
	
/**
 *
 *
 *
 *@return i
 */
	private ArgumentParser getArgumentParser()
	{
		return argPars;
	}
	
/**
 *
 *
 *
 *@param s
 *@param s1
 *@param elementName the name of the element in the XML file
 *@param attributes
 *@throws SAXException
 */
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("argument")) argCheck = true;
		else if (elementName.equalsIgnoreCase("optionalArgument")) argCheck = false;
    }
		
/**
 *
 *
 *
 *@param s
 *@param s1
 *@param element the name of the element being used in the 
 *@throws SAXEException
 */
	@Override
    public void endElement(String s, String s1, String element) throws SAXException {
		if (argCheck)
		{
			if (element.equalsIgnoreCase("name")) {
				argPars.addArgument(tmpValue);
				tmpName = tmpValue;
			}
			if (element.equalsIgnoreCase("type")) {
				Argument arg = new Argument(tmpName);
				if(tmpValue.equals("Integer")) argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.Integer);
				
				else if(tmpValue.equalsIgnoreCase("String")) argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.String);
				
				else if(tmpValue.equalsIgnoreCase("Float")) argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.Float);
				
				else if(tmpValue.equalsIgnoreCase("Boolean")) argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.Boolean);
				
				else argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.String);
				
			}
			if(element.equalsIgnoreCase("description")){
			   argPars.setDescription(tmpName, tmpValue);
			}
			if (element.equalsIgnoreCase("restricted")) {
				String tempStr = "";
				for(char ch: tmpValue.toCharArray())
				{					
					if(ch == ',')
					{
						argPars.setRestricted(tmpName, tempStr);
						tempStr = "";
					}
					else if(ch != ' ') tempStr += ch;
				}
			}
		}
		else
		{
			if (element.equalsIgnoreCase("name")) {
				argPars.addOptionalArgument(tmpValue);
				tmpName = tmpValue;
			}
			
			if (element.equalsIgnoreCase("restricted")) {
				String tempStr = "";
				for(char ch: tmpValue.toCharArray())
				{					
					if(ch == ',')
					{
						argPars.setRestricted(tmpName, tempStr);
						tempStr = "";
					}
					else if(ch != ' ') tempStr += ch;
				}
			}
			
			OptionalArgument argOpt = new OptionalArgument(tmpName, optArgXML);
			OptionalArgument arg = new OptionalArgument(tmpName);

			
			if (element.equalsIgnoreCase("numValues")) {
				argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setNumberValues(Integer.parseInt(tmpValue));
			}
			
			
			
			if (element.equalsIgnoreCase("required")) {
				if(tmpValue.equals("true"))	
				{
					argPars.setRequired(tmpName);
				}
			}	
			
			if (element.equalsIgnoreCase("type")) {
				if(tmpValue.equals("Integer")) {
					optArgXML = CommandLineArgument.DataType.Integer;
					argPars.argumentList.get(argPars.argumentList.indexOf(argOpt)).setDataType(CommandLineArgument.DataType.Integer);
				}
				
				else if(tmpValue.equalsIgnoreCase("Float")) {
					optArgXML = CommandLineArgument.DataType.Integer;
					argPars.argumentList.get(argPars.argumentList.indexOf(argOpt)).setDataType(CommandLineArgument.DataType.Float);
				}
				else if(tmpValue.equalsIgnoreCase("Boolean")) {
					optArgXML = CommandLineArgument.DataType.Boolean;
					argPars.argumentList.get(argPars.argumentList.indexOf(argOpt)).setDataType(CommandLineArgument.DataType.Boolean);
				}
				else {
					optArgXML = CommandLineArgument.DataType.String;
					argPars.argumentList.get(argPars.argumentList.indexOf(argOpt)).setDataType(CommandLineArgument.DataType.String);
				}
			}
			if (element.equalsIgnoreCase("description"))
			{
				argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDescription(tmpValue);
			}
			if (optArgXML != CommandLineArgument.DataType.Boolean)
			{
				if (element.equalsIgnoreCase("default")) {
					argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setValue(tmpValue);
				}
			}
			else
			{	
				argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setDataType(CommandLineArgument.DataType.Boolean);
			}
			if (element.equalsIgnoreCase("shortName")) {
				argPars.argumentList.get(argPars.argumentList.indexOf(arg)).setShort(tmpValue);
			}
		}
    }
		
/**
 *
 *
 *
 *@param ac
 *@param i
 *@param j
 *@throws SAXException
 */
	@Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
}