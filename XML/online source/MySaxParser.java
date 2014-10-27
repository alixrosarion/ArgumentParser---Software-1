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
public class MySaxParser extends DefaultHandler {
    List<Book> bookL;
    String tmpValue;
    Book bookTmp;
    SimpleDateFormat sdf= new SimpleDateFormat("yy-MM-dd");
    public MySaxParser(String bookXmlFileName) {
        bookL = new ArrayList<Book>();
        parseDocument(bookXmlFileName);
        printDatas();
    }
    private void parseDocument(String filename) {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(filename, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
    private void printDatas() {
       // System.out.println(bookL.size());
        for (Book tmpB : bookL) {
            System.out.println(tmpB.toString());
        }
    }
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element

        if (elementName.equalsIgnoreCase("book")) {
            bookTmp = new Book();
            bookTmp.id = attributes.getValue("id");
            bookTmp.lang = attributes.getValue("lang");
        }
        // if current element is publisher
        if (elementName.equalsIgnoreCase("publisher")) {
            bookTmp.publisher = attributes.getValue("country");
        }
    }
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // if end of book element add to list
        if (element.equals("book")) {
            bookL.add(bookTmp);
        }
        if (element.equalsIgnoreCase("isbn")) {
            bookTmp.isbn = tmpValue;
        }
        if (element.equalsIgnoreCase("title")) {
            bookTmp.title = tmpValue;
        }
        if(element.equalsIgnoreCase("author")){
           bookTmp.authors.add(tmpValue);
        }
        if(element.equalsIgnoreCase("price")){
            bookTmp.price = Integer.parseInt(tmpValue);
        }
        if(element.equalsIgnoreCase("regDate")){
            try {
                bookTmp.regDate = sdf.parse(tmpValue);
            } catch (ParseException e) {
                System.out.println("date parsing error");
            }
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
    public static void main(String[] args) {
        new MySaxParser("example.xml");
    }
}