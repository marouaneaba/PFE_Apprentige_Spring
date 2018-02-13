package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Repository.RepositoryHistory;

@Service
public class HistoryService {

	@Autowired
	private RepositoryHistory mRepositoryHistory;
	@Autowired
	private ExerciceService mExerciceService;
	
	public List<History> convertIterableToList(Iterable<History> mRepositoryHistory) {
		if (mRepositoryHistory instanceof List) {
			return (List<History>) mRepositoryHistory;
		}
		List<History> list = new ArrayList<>();
		if (mRepositoryHistory != null) {
			for (History e : mRepositoryHistory) {
				list.add(e);
			}
		}
		return list;
	}
	
	public List<History> getAllHistory(){
		return convertIterableToList(mRepositoryHistory.findAll());
	}
	
	@Transactional
	public void setNULLExerciceHistory(Long idEx){
		
		Exercice exercice = mExerciceService.getExerciceById(idEx);
		List<History> history = mRepositoryHistory.findByExercice(exercice);
		mRepositoryHistory.delete(history);		
	}
}
