package com.tbp.av.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.tbp.av.model.Nfe;
import com.tbp.av.model.User;
import com.tbp.av.repository.NfeRepository;

@Component
public class NfeService {
	
	@Autowired
	NfeRepository nfeRepository;
	
	@Autowired
	UserService userService;
	
	public void SalvarNfe(Nfe nfe) {
		
		nfeRepository.save(nfe);
		
	}
	
	public List<Nfe> GetNfes() {
		
		return (List<Nfe>) nfeRepository.findAll();
		
	}
	
	
	public List<Nfe> GetNfesUser(String cnpj) {
		
		User user = userService.getByCnpj(cnpj);
		return (List<Nfe>) nfeRepository.findByUser(user);
	
	}
	
	public List<Nfe> GetNfesDate(String cnpj, String dataini, String datafim)  {
		
		Integer id = userService.getByCnpj(cnpj).getId();
		
		
		
		return nfeRepository.findAllWithCreationDateTimeBefore(dataini, datafim, id);
		
	}
	
	
}
