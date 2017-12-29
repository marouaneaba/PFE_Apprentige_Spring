package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Admin;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Repository.RepositoryAdmin;

@Service
public class AdminService {

	
	@Autowired
	private RepositoryAdmin mRepositoryAdmin;
	
	
	public List<Admin> convertIterableToList(Iterable<Admin> iterable) {
        if (iterable instanceof List) {
            return (List<Admin>) iterable;
        }
        List<Admin> list = new ArrayList<>();
        if (iterable != null) {
            for (Admin e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
	
	public List<Admin> getAllAdmin(){
		
		return convertIterableToList(mRepositoryAdmin.findAll());
	}
	
	public Admin getAdminById(Long id){
		System.out.println("update service debut : ");
		return mRepositoryAdmin.findOne(id);
	}
	
	@Transactional
	public void updateAdmin(Long id,String pseudo,String password,String email){
		mRepositoryAdmin.updateConnaissanceById(id, pseudo, password, email);
	}
	
	@Transactional
	public void deleteAdmin(Long id){
		mRepositoryAdmin.delete(id);
	}
	
	
	
}
