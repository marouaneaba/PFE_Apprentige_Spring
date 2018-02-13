package com.lille1.PFE.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Variable;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryVariable;
import com.lille1.PFE.sax.SaxHandler;

@Service
public class ExerciceService {

	@Autowired
	private RepositoryExercice mRepositoryExercice;
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	@Autowired
	private RepositoryVariable mRepositoryVariable;

	
	// à partir HTML de saisie des varibale d'un enseignant, on récupérer un XML nettoyer des varibale 
	public List<Variable> SaveVaribale(String StringVariable){
		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString(StringVariable,"var");
		return mSaxHandler.getVariable();
	}

	// transformer un Iterable des exercices à une list des exercices
	public List<Exercice> convertIterableToList(Iterable<Exercice> iterable) {
		if (iterable instanceof List) {
			return (List<Exercice>) iterable;
		}
		List<Exercice> list = new ArrayList<>();
		if (iterable != null) {
			for (Exercice e : iterable) {
				list.add(e);
			}
		}
		return list;
	}

	// retourner tous les exercice enregistrer
	public List<Exercice> getAllExercices() {
		return convertIterableToList(mRepositoryExercice.findAll());
	}

	// supprimer un exercice à partir de son ID
	public void deleteExercice(Long id) {
		List<Variable> variables = mRepositoryExercice.findOne(id).getVariables();

		mRepositoryVariable.delete(variables);
		mRepositoryExercice.delete(mRepositoryExercice.findOne(id));
	}

	//récupérer un exercice à partir son ID
	public Exercice getExerciceById(Long id) {
		return mRepositoryExercice.findOne(id);
	}

	//ajouter un exercice
	public void addExerciceRepository(String nomExercice, String enonceExercice, String XMLSolution,
			String XMLSolutionNettoyer, List<Connaissance> connaissances, List<Variable> variables,
			Enseignant enseignant) {

		Exercice exercice = new Exercice(nomExercice, enonceExercice, XMLSolution, XMLSolutionNettoyer);
		mRepositoryExercice.save(exercice);


	}

	// liée un exercice à un enseignant
	public void addExerciceEnseigantRepository(String nomExercice, String enonceExercice, String XMLSolution,
			String XMLSolutionNettoyer, List<Connaissance> connaissances, List<Variable> variables, Personne personne) {

		Enseignant enseignantRechercher = mRepositoryEnseignant.findOne(personne.getIdEns());
		Exercice exercice = new Exercice(nomExercice, enonceExercice, XMLSolution, XMLSolutionNettoyer);
		enseignantRechercher.setExercices(exercice);
		mRepositoryExercice.save(exercice);
		exercice.setConnaissance(connaissances);
		mRepositoryConnaissance.save(connaissances);
		exercice.setVariables(null);
		exercice.setEnseignant(null);

	}

	// retourner un exercice à partir son ID
	public List<Exercice> getExerciceEnseignant(Long id) {
		return mRepositoryEnseignant.findOne(id).getExercices();
	}

	//enregistrer in exercice
	public void saveExercice(String nameExercice, String ennoncer_exercice, String variable, String codeBrouillon,
			String codeNetoyer, List<String> connaissancesSelected, Personne personne) {

		Enseignant enseignant = mRepositoryEnseignant.findOne(personne.getIdEns());
		Exercice exercice = new Exercice(nameExercice, ennoncer_exercice, codeBrouillon, codeNetoyer);

		System.out.println(codeBrouillon);
		List<Connaissance> connaissances = new ArrayList<>();
		for (int i = 0; i < connaissancesSelected.size(); i++) {
			if (connaissancesSelected.get(i) != null && !connaissancesSelected.get(i).equals(""))
				connaissances.add(mRepositoryConnaissance.findOne(Long.parseLong(connaissancesSelected.get(i))));
		}
		exercice.setConnaissance(connaissances);
		List<Variable> variables = SaveVaribale(variable); 
		System.out.println("++++++ la size  des varibale : "+variables.size());
		for(int i=0;i<variables.size();i++){
			System.out.println("mon objet varibale : "+variables.get(i));
		 }

		exercice.setVariables(variables);
		mRepositoryVariable.save(variables);

		enseignant.setExercices(exercice);
		mRepositoryExercice.save(exercice);
		System.out.println("exercice : "+exercice);

	}
	
	// comparer deux XML , solution d'un exercice fournie par l'enseignant , et la soulution saisie par l'étudiant 
	// pour savoir si l'etudiant à bien reponder sur l'exercice.
	public boolean explorer(Element current1,Element current2){
		
		System.out.println("1 : "+current1.getName());
		System.out.println("2 : "+current2.getName());
		
		List children1 = current1.getChildren();
		List children2 = current2.getChildren();
		
		System.out.println("size 1 : "+children1.size());
		System.out.println("size 2 : "+children2.size());
		
		Iterator iterator1 = children1.iterator();
		Iterator iterator2 = children2.iterator();
		
		int sizeAttribute1 = current1.getAttributes().size();
		int sizeAttribute2 = current2.getAttributes().size();
		
		if(children1.size() != children2.size() || !current1.getName().equals(current2.getName()) || sizeAttribute1 != sizeAttribute2){
			return false;
		}
		
		for(int i=0;i<sizeAttribute1;i++){
			if(!current1.getAttributes().get(i).getName().trim().equals(current2.getAttributes().get(i).getName().trim()) || 
					!current1.getAttributes().get(i).getValue().trim().equals(current2.getAttributes().get(i).getValue().trim()))
				return false;
		}
		
		while(iterator1.hasNext() && iterator2.hasNext()){
			Element child1 = (Element) iterator1.next();
			Element child2 = (Element) iterator2.next();
			boolean r = explorer(child1,child2);
			if(!r) return false;
		}
		
		return true;
		
	}
	
	
	public boolean ExerciceComparTo(String EtudiantSol,String EnseignantSol){
		
		SAXBuilder sxb = new SAXBuilder();
		//String code1 ="<doc><lire var='nb' ></lire><affectation var='N'  val1='0'  arith=''  val2='' ></affectation><affectation var='nb'  val1='0'  arith=''  val2='' ></affectation><tantque val1='nb'  arith='inf'  val2='0' ><lire var='nb' ></lire><if val1='nb'  arith='inf'  val2='0' ><affectation var='i'  val1='i'  arith='+'  val2='' ></affectation></if><else><affectation var='N'  val1='N'  arith='+'  val2='' ></affectation></else></tantque></doc>";
		//String code2 ="<doc><lire var='nb' ></lire><affectation var='N'  val1='0'  arith=''  val2='' ></affectation><affectation var='nb'  val1='0'  arith=''  val2='' ></affectation><tantque val1='nb'  arith='inf'  val2='0' ><lire var='nb' ></lire><if val1='nb'  arith='inf'  val2='0' ><affectation var='i'  val1='i'  arith='+'  val2='' ></affectation></if><else><affectation var='N'  val1='N'  arith='+'  val2='' ></affectation></else></tantque></doc>";
		Document document1 = null;
		Document document2 = null;
		boolean resultat = false;
		try {
			document1 = sxb.build(new ByteArrayInputStream(EtudiantSol.getBytes()));
			document2 = sxb.build(new ByteArrayInputStream(EnseignantSol.getBytes()));
			Element root1 = document1.getRootElement();
			Element root2 = document2.getRootElement();
			resultat = explorer(root1,root2);
			
		} catch (JDOMException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return resultat;
	}

}
