package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Repository.RepositoryHistory;

@Service
public class HistoryService {

	@Autowired
	private RepositoryHistory mRepositoryHistory;
	
	
	public List<History> convertIterableToList(Iterable<History> iterable) {
		if (iterable instanceof List) {
			return (List<History>) iterable;
		}
		List<History> list = new ArrayList<>();
		if (iterable != null) {
			for (History e : iterable) {
				list.add(e);
			}
		}
		return list;
	}
	
	public List<History> getAllHistory(){
		return convertIterableToList(mRepositoryHistory.findAll());
	}
}
