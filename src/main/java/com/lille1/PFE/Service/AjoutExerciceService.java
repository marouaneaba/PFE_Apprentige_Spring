package com.lille1.PFE.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Variable;


@Service
public class AjoutExerciceService  {

	SAXBuilder sxb = new SAXBuilder();
	
	
	
	public List<Variable> SaveVaribale(String value){
		value = "<doc>"+value+"</doc>";
		List<Variable> varibales = new ArrayList<>();
		
		Document document = null;
		String[] variables = null;
		String[] types = null;
	      try
	      {
	     
	         document = sxb.build(new ByteArrayInputStream(value.getBytes()));
	      }
	      catch(Exception e){}

	      Element racine =document.getRootElement();

		   List listEtudiants = racine.getChildren("li");
		   Iterator index = listEtudiants.iterator();
		   while(index.hasNext())
		   {
			  
		      Element courant = (Element)index.next();
		      List<Element> nomVariableElement = courant.getChildren();
		      
		      variables = nomVariableElement.get(0).getText().trim().split("\n");
		      types = nomVariableElement.get(2).getText().trim().split("\n");
		   }
		   for(int i=0;i<variables.length;i++){
			   varibales.add(new Variable(variables[i],types[i]));
		    	  System.out.println(variables[i]);
		    	  System.out.println(types[i]);
		   }
		   
		   return varibales;
	}
	
	
	public void SaveCode(String code){
		code = "<doc>"+code+"</doc>";
		
		Document document = null;
		
	      try
	      {
	     
	         document = sxb.build(new ByteArrayInputStream(code.getBytes()));
	      }
	      catch(Exception e){
	    	  System.out.println(e);
	      }

	      Element racine =document.getRootElement();

		   List li = racine.getChildren("li");
		   Iterator index = li.iterator();
		   while(index.hasNext())
		   {
			  
		      Element courant = (Element)index.next();
		      List child = courant.getChildren();
		      Iterator index2 = child.iterator();
		      while(index2.hasNext()){
		    	  Element courantLI = (Element)index2.next();
		    	  XML_file(courantLI);
		    	  //System.out.println(courantLI.getName());
		    	  //System.out.println(courantLI.getAttributes().get(0).getName());
		      }
		      //System.out.println(courant.getName());
		      
		   }
		
		//System.out.println(code);
	}
	
	public void XML_file(Element element){
		//classes.get(0).getChild("attribut");
		Element racine=new Element("doc");
	    Document document=new Document(racine);
		switch(element.getName()){
		case "if":
				
				Element i_f =  new Element("if");
				String val1 = element.getAttributeValue("val1");
				String arith = element.getAttributeValue("arith");
				String val2 = element.getAttributeValue("val2");
				Element ifCondition = new Element("if");
				Attribute variable = new Attribute("variable",""+val1);
				Attribute condition = new Attribute("condition",""+arith);
				Attribute valeurOrVariable = new Attribute("val",""+val2);
				ifCondition.setAttribute(variable);
				ifCondition.setAttribute(condition);
				ifCondition.setAttribute(valeurOrVariable);
				
				System.out.println("val1 : "+val1+" ,arith : "+arith+" ,val2 : "+val2);
				System.out.println(element.getName());
			break;
		case "pour":
				System.out.println("pour");
				System.out.println(element.getName());
			break;
		case "tantque":
				System.out.println("tantque");
				System.out.println(element.getName());
			break;
		case "lire":
				System.out.println("lire");
				System.out.println(element.getName());
			break;
		case "afficher":
				System.out.println("afficher");
				System.out.println(element.getName());
			break;
		case "affectation":
				System.out.println("aff");
				System.out.println(element.getName());
			break;
		}
	}
	
	public String XMLCode(String value){
		value = "<doc>"+value+"</doc>";
		return null;
	}
	
	public void afficheALL(String val)
	{
		val = "<doc>"+val+"</doc>";
		Document document = null;
		
	      try
	      {
	     
	         document = sxb.build(new ByteArrayInputStream(val.getBytes()));
	      }
	      catch(Exception e){}

	      Element racine =document.getRootElement();

		   List listEtudiants = racine.getChildren("li");
		   Iterator i = listEtudiants.iterator();
		   while(i.hasNext())
		   {
		      Element courant = (Element)i.next();

		      System.out.println(courant.getChild("h5").getText());
		   }
	}

}
