import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class xmlParser extends DefaultHandler {

	private List <ArgumentTest> argumentList;
	private String tmpValue;
	private ArgumentTest argTmp;
	
	public xmlParser(String fileName)
	{
		argumentList = new ArrayList<ArgumentTest>();
		parseFile(fileName);
		printDatas();
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
	
	private void printDatas() {
        for (ArgumentTest tmpB : argumentList) {
            System.out.println(tmpB.toString());
        }
    }
	
	 public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("argument")) {
            argTmp = new ArgumentTest();
        }
    }
    
	@Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // if end of book element add to list
        if (element.equals("argument")) {
            argumentList.add(argTmp);
        }
        if (element.equalsIgnoreCase("name")) {
            argTmp.name = tmpValue;
        }
        if (element.equalsIgnoreCase("type")) {
            argTmp.type = tmpValue;
        }
        if(element.equalsIgnoreCase("description")){
           argTmp.description = tmpValue;
        }
    }
	
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
    public static void main(String[] args) {
        new xmlParser("arguments.xml");
    }
	
}