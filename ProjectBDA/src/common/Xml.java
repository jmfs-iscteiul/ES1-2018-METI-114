package common;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml {


	private File configFile = new File("C:/Users/Diogo/Desktop/config.xml");

	public Xml() {
		// TODO Auto-generated constructor stub
	}


	public void escreverXML(String servico, String atributo, String valor) {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configFile);
			doc.getDocumentElement().normalize();

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/XML/*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			boolean encontrado = false;
			for(int i = 0; i < nl.getLength(); i++) {
				System.out.println(nl.item(i).getNodeName());
				if(nl.item(i).getNodeName().equals(servico)) {
					((Element)nl.item(i)).setAttribute(atributo, valor);
					encontrado = true;
					break;
				}
			}
			
			if(!encontrado) {
				Element newElement = doc.createElement(servico);
				newElement.setAttribute(atributo, valor);
				doc.getDocumentElement().appendChild(newElement);
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(configFile);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}

	}

	public String leituraXML(String servico, String atributo) {
		
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(configFile);
			doc.getDocumentElement().normalize();

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/XML/*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			for(int i = 0; i < nl.getLength(); i++) {
				System.out.println(nl.item(i).getNodeName());
				if(nl.item(i).getNodeName().equals(servico)) {
					String teste = ((Element)nl.item(i)).getAttribute(atributo);
					return teste;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
		public static void main(String[] args){
		/*try {	
			File inputFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//			Document doc = dBuilder.parse(inputFile);
			Document doc = dBuilder.newDocument();
			//			doc.getDocumentElement().normalize();         
			//			System.out.println("\n----- Search the XML document with xpath queries -----");  
			//			// Query 1 
			//			System.out.println("Query 1: ");
			//			XPathFactory xpathFactory = XPathFactory.newInstance();
			//			XPath xpath = xpathFactory.newXPath();
			//			XPathExpression expr = xpath.compile("/XML/Service/@*");
			//			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			//			for (int i = 0; i < nl.getLength(); i++) {
			//				System.out.print(nl.item(i).getNodeName()  + ": ");
			//				System.out.println(nl.item(i).getFirstChild().getNodeValue());
			//			}
			//			// Query 2
			//			System.out.println("\nQuery 2: ");         
			//			expr = xpath.compile("/XML/Paths/docPath");
			//			String str = (String) expr.evaluate(doc, XPathConstants.STRING);
			//
			//			System.out.println("docPath: " + str);

			// Adding new element Service with attributes to the XML document (root node)

			Element newElement1 = doc.createElement("Mail");
			newElement1.setAttribute("Account", "darsa@iscte-iul.pt");


			Element newElement2 = doc.createElement("Facebook");
			newElement2.setAttribute("User", "ricas1997@gmail.com"); 
			newElement2.setAttribute("Token", "EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");

			Element newElement3 = doc.createElement("Twitter");
			newElement3.setAttribute("User", "ES1");


			// Add new nodes to XML document (root element)
			//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			Element topo = doc.createElement("XML");
			doc.appendChild(topo);

			Node n = (Node) topo;
			n.appendChild(newElement1);
			n.appendChild(newElement2);
			n.appendChild(newElement3);

			// Save XML document
			System.out.println("\nSave XML document.");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new File("C:/Users/Diogo/Desktop/config.xml"));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		} catch (Exception e) { e.printStackTrace(); }*/
		
		Xml teste = new Xml();
//		teste.escreverXML("Twitter", "User", "Teste");
		System.out.println("Final: " + teste.leituraXML("Twitter", "User"));
	}


}
