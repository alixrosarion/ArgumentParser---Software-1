package edu.jsu.mcis;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * XMLParser is an implementation of Argument Parser dedicated for XML. 
 *It implements methods used by Argument Parser and it reads all the tags
 *in an XML file and automatically adds the arguments with their attributes
 *to the dedicated Arguments list.
 *<p>
 *This is done by scanning the tags within the XML file. If the format of the
 *file is incorrect an error will be thrown and the file will not be parsed.
 *
 *
 *
 */

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
 *Class constructor.
 *
 *
 *@param file the XML file (with it's extension) the arguments and their data to be loaded from
 *@return the argument parser created from the file
 */
	public static ArgumentParser createArgumentParser(String file)
	{
		XMLParser p = new XMLParser(file);
		return p.getArgumentParser();
	}
	
/**
 *Class constructor that specifies the XML file to load the argument
 *data from.
 *
 *@param filename the complete name of the file with the file extension (.xml)
 *                to be parsed. Must be within same folder
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
 *Creates a new ArgumentParser to be used while parsing
 *the information in the XML file.
 *
 *@return the argument parser created
 */
	private ArgumentParser getArgumentParser()
	{
		return argPars;
	}

/**
 *Takes the information of the arguments and optional arguments - title, description,
 *number of values, and the data type - and writes it to an XML file. Automatically 
 *generates the XML tags appropriate for each piece of data.
 *
 *@param filename the name of the file to write the XML code to
 *@param argPars the argument parser that is used to write to the XML file
 */
    public static void saveXMLFile(ArgumentParser argPars, String filename)
    {
        try
        {
            PrintWriter file = new PrintWriter(filename);
            file.write(argPars.getOutput());
            file.close();
        }catch(FileNotFoundException e){e.printStackTrace();}
    }
	
	
/**
 *Starts the XML Parsing method called by the program automatically by checking each tag and starts a process using it.
 *
 *
 *@param s the Namespace URI, or the empty string if the element has no Namespace URI 
 *@param s1 the local name (without prefix), or the empty string if Namespace 
 *@param elementName the name of the element in the XML file
 *@param attributes  the attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
 *@throws SAXException any SAX exception, possibly wrapping another exception
 */
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("argument")) argCheck = true;
		else if (elementName.equalsIgnoreCase("optionalArgument")) argCheck = false;
    }
		
/**
 *Checks if it is the end of a tag while in the XML Parsing process. If so, it finishes the process.
 *
 *
 *@param s the Namespace URI, or the empty string if the element has no Namespace URI 
 *@param s1 the local name (without prefix), or the empty string if Namespace 
 *@param element the name of the element being used in the 
 *@throws SAXException any SAX exception, possibly wrapping another exception
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
						argPars.addRestricted(tmpName, tempStr);
						tempStr = "";
					}
					else if(ch != ' ') tempStr += ch;
				}
			}
			
			if (element.equalsIgnoreCase("numValues")) {
				argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setNumberValues(Integer.parseInt(tmpValue));
			}
		}
		else
		{
			if (element.equalsIgnoreCase("name")) {
				argPars.addOptionalArgument(tmpValue);
				tmpName = tmpValue;
			}
			
			if(element.equalsIgnoreCase("mutualGroup")){
				int groupNum = Integer.parseInt(tmpValue);
				argPars.setMutualGroup(groupNum, tmpName);
			}
			
			if (element.equalsIgnoreCase("restricted")) {
				String tempStr = "";
				for(char ch: tmpValue.toCharArray())
				{					
					if(ch == ',')
					{
						argPars.addRestricted(tmpName, tempStr);
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
 *Reads each character inside the XML file, and checks if it is a tag, attribute, etc.
 *And saves it in a temporary variable.
 *
 *@param ac the characters from the XML document
 *@param i the start position in the array
 *@param j the number of characters to read from the array
 *@throws SAXException any SAX exception, possibly wrapping another exception
 */
	@Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
}