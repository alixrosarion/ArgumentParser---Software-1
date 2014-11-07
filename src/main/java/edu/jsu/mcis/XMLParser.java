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
	private int optArgXML;
	private String tmpValue;
	private ArgumentParser argPars;
	private List <CommandLineArgument> argumentList;
	private int optCount;
	
	public static ArgumentParser createArgumentParser(String file)
	{
		XMLParser p = new XMLParser(file);
		return p.getArgumentParser();
	}

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

	private ArgumentParser getArgumentParser()
	{
		return argPars;
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
				argPars.addArgument(tmpValue);
				tmpName = tmpValue;
			}
			if (element.equalsIgnoreCase("type")) {
				if(tmpValue.equals("Integer")) argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Integer);
				
				else if(tmpValue.equalsIgnoreCase("String")) argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.String);
				
				else if(tmpValue.equalsIgnoreCase("Float")) argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Float);
				
				else if(tmpValue.equalsIgnoreCase("Boolean")) argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.Boolean);
				
				else argPars.argumentList.get(argPars.argumentList.indexOf(new Argument(tmpName))).setType(CommandLineArgument.Type.String);
				
			}
			if(element.equalsIgnoreCase("description")){
			   argPars.addDescription(tmpName, tmpValue);
			}
		}
		else
		{
			if (element.equalsIgnoreCase("name")) {
				argPars.addOptArg(tmpValue, 0);
				tmpName = tmpValue;
			}
			if (element.equalsIgnoreCase("numValues")) {
				optArgXML = Integer.parseInt(tmpValue);
				argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setNumValues(optArgXML);
			}
			if (element.equalsIgnoreCase("description"))
			{
				argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setDescription(tmpValue);
			}
			if (optArgXML > 0 )
			{
				if (element.equalsIgnoreCase("type")) {
					if(tmpValue.equals("Integer")) argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Integer);
					
					else if(tmpValue.equalsIgnoreCase("Float")) argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Float);
					
					else argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.String);
					
				}
				
				if (element.equalsIgnoreCase("value")) {
					argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).addValue(tmpValue);
				}
			}
			else
			{	
				argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setType(CommandLineArgument.Type.Boolean);
			}
			if (element.equalsIgnoreCase("shortName")) {
				argPars.argumentList.get(argPars.argumentList.indexOf(new OptionalArgument(tmpName, optArgXML))).setShort(tmpValue);
			}
		}
    }
	
	@Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
}