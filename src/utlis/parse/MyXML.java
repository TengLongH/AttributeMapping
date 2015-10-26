package utlis.parse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyXML {

	public static void main(String[] args) {
		try {
			MyXML.createXMLDemo("src/templateTreeSrc.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void demo() throws ParserConfigurationException, SAXException, IOException{
		File input = new File("src/input.txt");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(input);
		doc.getDocumentElement().normalize();
		System.out.println( "Root Element:" + doc.getDocumentElement().getNodeName() );
		NodeList nList = doc.getElementsByTagName("student");
		System.out.println("-------------------------------");
		
		Element element = null;
		org.w3c.dom.Node node = null;
		for( int index = 0;index < nList.getLength(); index++ ){
			node = nList.item(index);
			System.out.println("Current Element: " + node.getNodeName() );
			if( node.getNodeType() == Node.ELEMENT_NODE ){
				element = (Element) node;
				System.out.println( "Student role no: " + element.getAttribute("rollno") );
				System.out.println( "First name: " + element.getElementsByTagName("firstname")
				.item(0).getTextContent() );
				System.out.println( "LastName: " + element.getElementsByTagName("lastname")
				.item(0).getTextContent());
				
				System.out.println();
			}
		}
	}
	
	public static void createXMLDemo( String source) throws XMLStreamException, IOException{
		File in = new File(source);
		BufferedReader reader = new BufferedReader( new FileReader(in)) ;
		
		StringWriter stringWriter = new StringWriter();
		XMLOutputFactory out = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlStringWriter = out.createXMLStreamWriter(stringWriter);
		
		xmlStringWriter.writeStartDocument();
		xmlStringWriter.writeStartElement("book");
		xmlStringWriter.writeAttribute("name", source);
		
		String sheetBuf = reader.readLine();
		int index = sheetBuf.indexOf(':');
		if( index < 0 )return ;
		sheetBuf = sheetBuf.substring(index+1).trim();
		String[] sheets = sheetBuf.split(" ");
		for( int i = 0; i < sheets.length; i++ ){
			String sheetName = sheets[i];
			xmlStringWriter.writeStartElement("sheet");
			xmlStringWriter.writeAttribute("name", sheetName );
			String fieldBuf = reader.readLine();
			index = fieldBuf.indexOf(':');
			if( index < 0 )return ;
			fieldBuf = fieldBuf.substring(index+1).trim();
			String[] fields = fieldBuf.split(" ");
			for( int j = 0; j < fields.length; j++ ){
				xmlStringWriter.writeStartElement("field");
				xmlStringWriter.writeAttribute("name", fields[j] );
				xmlStringWriter.writeAttribute("row", "0" );
				xmlStringWriter.writeAttribute("colum", "0" );
				xmlStringWriter.writeEndElement();
			}
			xmlStringWriter.writeEndElement();
		}
		
		xmlStringWriter.writeEndElement();
		xmlStringWriter.writeEndDocument();
		
		xmlStringWriter.flush();
		xmlStringWriter.close();
		String content = stringWriter.getBuffer().toString();
		
		File xml = new File("src/out.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(xml));
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
}
