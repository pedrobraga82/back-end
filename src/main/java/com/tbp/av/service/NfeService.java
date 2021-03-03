package com.tbp.av.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbp.av.model.Nfe;
import com.tbp.av.repository.NfeRepository;

@Component
public class NfeService {
	
	@Autowired
	NfeRepository nfeRepository;
	
	public void SalvarNfe(Nfe nfe) {
		
		nfeRepository.save(nfe);
		
	}
	
	public List<Nfe> GetNfes() {
		
		return (List<Nfe>) nfeRepository.findAll();
		
	}
}
