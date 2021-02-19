package com.tbp.av.util;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

public class GetFileCertificate {
	
	@Autowired
	ResourceLoader resourceLoader;
	
	private String cnpj;
	
	
	public org.springframework.core.io.Resource loadEmployeesWithResourceLoader(String cnpj) {
		
	    return resourceLoader.getResource("src/main/resources/files/"+ cnpj +"/" + cnpj + ".pfx");
	}
	
	public GetFileCertificate(String cnpj) {
		super();
		this.cnpj = cnpj;
	}




	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}




	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}




	public String getCnpj() {
		return cnpj;
	}




	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}




	
	

}
