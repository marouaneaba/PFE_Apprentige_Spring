package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Repository.RepositoryConnaissance;


@Service
public class ConnaissanceService {

	
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	
	
	public List<Connaissance> convertIterableToList(Iterable<Connaissance> iterable) {
        if (iterable instanceof List) {
            return (List<Connaissance>) iterable;
        }
        List<Connaissance> list = new ArrayList<>();
        if (iterable != null) {
            for (Connaissance e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
	
	public List<Connaissance> getAllConnaissance(){
		
		return convertIterableToList(mRepositoryConnaissance.findAll());
	}
	
	public void supprimerConnaissances(Long id){
		mRepositoryConnaissance.delete(id);
	}
	
	public Connaissance getConnaissances(Long id){
		return mRepositoryConnaissance.findOne(id);
	}
	
	@Transactional
	public void updateConnaissance(Long id,String nom,String ordre){
		mRepositoryConnaissance.updateConnaissanceById(id,nom,ordre);
	}
	
	@Transactional
	public void updateConnaissanceToTrue(Long id){
		mRepositoryConnaissance.updateConnaissanceById(id);
	}
}
