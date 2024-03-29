package com.lille1.PFE.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import com.lille1.PFE.Entity.*;
import java.util.*;
import java.io.StringReader;


public class SaxHandler extends DefaultHandler {

	private static String result = "";
	private static String type = "";
	private static boolean rec = false;
	private static String nomVariable ="";
	private static boolean write = false;
	private static List<Variable> varibales = new ArrayList<Variable>();

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de se lancer
	 *             dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		
	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * 
	 * @throws SAXException
	 *             en cas de probleme quelquonque ne permettant pas de
	 *             considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml
	 * ouvrante.
	 * 
	 * @param nameSpaceURI
	 *            l'url de l'espace de nommage.
	 * @param localName
	 *            le nom local de la balise.
	 * @param rawName
	 *            nom de la balise en version 1.0
	 *            <code>nameSpaceURI + ":" + localName</code>
	 * @throws SAXException
	 *             si la balise ne correspond pas a ce qui est attendu, comme
	 *             par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs)
			throws SAXException {

		
		if(type.equals("code")){
			if(!localName.equals("li") && !localName.equals("span") && !localName.equals("ul") && write == false){
				result =result+ "<"+localName;
			
				for (int index = 0; index < attributs.getLength(); index++,result = result+" ") { // on parcourt la liste des attributs
					result =result + " "+attributs.getLocalName(index)+"='"+attributs.getValue(index)+"'";
				}
				result=result+">";
			}
		}else if(type.equals("var")){

			if(localName.equals("li")){
				this.rec = true;
				this.nomVariable = "";
			}else if(localName.equals("span")){
				this.rec = false;
			}
		}
	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {

		
		if(type.equals("code")){
			if(localName.equals("lire") || localName.equals("affectation") || localName.equals("afficher") || localName.equals("doc"))
				result =result+ "</"+localName+">";
			else if(write && !localName.equals("li") && !localName.equals("ul") && !localName.equals("span")){
				result =result+ "</"+localName+">";
				write = false; 
				System.out.println("***************************************write : "+write);
			}
		}else if(type.equals("var")){
			if(localName.equals("li")){
				this.rec = false;
			}else if(localName.equals("span")){
				this.rec = true;
			}
		}
		
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres
	 * (entre deux balises).
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param length
	 *            le nombre de caracteres a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();
		 if(this.rec){
			 this.nomVariable = this.nomVariable +s+",";
			 String[] tab = this.nomVariable.split(",");
			 if(tab.length == 2){
				 this.varibales.add(new Variable(tab[0],tab[1]));
			 }
		 }
			if(s.startsWith("FIN_")){
				write = true;
				System.out.println("***************************************write : "+write);
			}
	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au
	 * sens de XML. C'est a dire que cet evenement est envoye pour plusieurs
	 * espaces se succedant, les tabulations, et les retours chariot se
	 * succedants ainsi que toute combinaison de ces trois types d'occurrence.
	 * 
	 * @param ch
	 *            les caracteres proprement dits.
	 * @param start
	 *            le rang du premier caractere a traiter effectivement.
	 * @param length
	 *            le nombre de caracteres a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {


	}

	/**
	 * Rencontre une instruction de traitement.
	 * 
	 * @param target
	 *            la cible de l'instruction de traitement.
	 * @param data
	 *            les valeurs associees a cette cible. En general, elle se
	 *            presente sous la forme d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public void processingInstruction(String target, String data) throws SAXException {

	}

	public String parserString(String chaine, String type) {
	
		this.type = type;
		chaine = "<doc>" + chaine + "</doc>";
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new SaxHandler());
			saxReader.parse(new InputSource(new StringReader(chaine)));
		} catch (Exception t) {
			t.printStackTrace();
		}
		return result;
	}

	public void setResult(String chaine) {
		this.result = chaine;
		this.type = chaine;
		this.rec = false;
		this.write = false;
		this.varibales.clear();
	}
	
	public List<Variable> getVariable(){
		return this.varibales;
	}

}
