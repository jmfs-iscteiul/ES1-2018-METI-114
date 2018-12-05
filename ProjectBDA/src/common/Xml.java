package common;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

import mail.MailInfoStruct;

/**
 * Esta classe permite ler e escrever em ficheiros XML, sendo que usaremos esse ficheiro como uma espécie de base de dados para armazenar tokens dos serviços e permitir também o funcionamento offline.
 * 
 * @author darsa-iscteiul e rmcmc-iscteiul
 */
public class Xml {


	private File configFile = new File("config.xml");

	/**
	 * Esta função permite acrescentar serviços, caso estes não existam já ou acrescentar valores e atributos aos já existentes.
	 * @param servico	Serviço a que queremos associar o atributo e valor que vamos introduzir.
	 * @param atributo	Atributo a acrescentar/alterar.
	 * @param valor		Valor a introduzir.
	 */
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

	/**
	 * 
	 * Fornecendo os parâmetros abaixo indicados devolve o valor desejado.
	 * 
	 * @param servico Serviço que queremos consultar.
	 * @param atributo	Atributo cujo valor que queremos está associado.
	 * @return	Valor Valor desejado.
	 */
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

	/**
	 * Guarda a lista de notificações no ficheiro XML, o que permite o funcionamento offline.
	 * 
	 * @param lista	Lista de notificações a guardar.
	 */
	public void escreverDeVarias(ArrayList <standardInfoStruct> lista) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configFile);
			doc.getDocumentElement().normalize();

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/XML/*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			Element e = doc.createElement("Mensagens");
			for(int i = 0; i < nl.getLength(); i++) {
				if(nl.item(i).getNodeName().equals("Mensagens")) {
					doc.getDocumentElement().removeChild(nl.item(i));
					break;
				}
			}
			doc.getDocumentElement().appendChild(e);
			for (int i = 0; i< lista.size();i++) {
				Element e2 = doc.createElement("Mensagem" +i);
				e2.setAttribute("Data", lista.get(i).getDate().toString());
				e2.setAttribute("Author", lista.get(i).getAuthor());
				e2.setAttribute("Text", lista.get(i).getTitle());
				if (lista.get(i) instanceof MailInfoStruct) {
					e2.setAttribute("Subject", ((MailInfoStruct)lista.get(i)).getSubject());
					e2.setAttribute("To", ((MailInfoStruct)lista.get(i)).getTo());
					e2.setAttribute("CC", ((MailInfoStruct)lista.get(i)).getCc());
				} 
				e.appendChild(e2);
			}

			doc.getDocumentElement().normalize();

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(configFile);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * Permite recuperar a lista de notificações descarregadas mais recentemente.
	 * 
	 * @return Lista de notificações mais recentemente armazenada.
	 */
	public ArrayList <standardInfoStruct> lerDeVarias (){
		ArrayList <standardInfoStruct> lista = new  ArrayList <standardInfoStruct> ();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(configFile);
			doc.getDocumentElement().normalize();

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/XML/Mensagens/*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			for(int i = 0; i < nl.getLength(); i++) {
				Element e = (Element)nl.item(i);
				String teste = e.getAttribute("Data"); 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
				LocalDateTime ddd = LocalDateTime.parse(teste, formatter);
				Date d = Date.from(ddd.atZone(ZoneId.systemDefault()).toInstant());
				if (e.getAttribute("To").equals("")) {
					standardInfoStruct s = new standardInfoStruct(d, e.getAttribute("Author"), 
							e.getAttribute("Text"));
					lista.add(s);
				} else {
					standardInfoStruct s = new MailInfoStruct(d, e.getAttribute("Author"), 
							e.getAttribute("Text"),
							e.getAttribute("Subject"),
							e.getAttribute("To"),
							e.getAttribute("CC"));
					lista.add(s);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return lista;
	}
}
