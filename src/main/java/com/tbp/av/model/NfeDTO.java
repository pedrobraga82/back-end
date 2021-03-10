package com.tbp.av.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NfeDTO {
	
	
    Integer id;
    private User user;
	  String chnfe;
	  String ie;
	  String tiponf;
	  String nome;
	  Float valor;
	  String cnpjremetente;
	  String cnpj;
	  String datarecto;
	  
	  @Temporal(TemporalType.DATE)
	  Date dataemissao;

	  
	  
	  
	  public Date getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}

	public String getDatarecto() {
		return datarecto;
	}

	public void setDatarecto(String datarecto) {
		this.datarecto = datarecto;
	}

	public String getCnpj() { return cnpj; }

	  public void setCnpj(String cnpj) { this.cnpj = cnpj; }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getChnfe() {
		return chnfe;
	}

	public void setChnfe(String chnfe) {
		this.chnfe = chnfe;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getTiponf() {
		return tiponf;
	}

	public void setTiponf(String tiponf) {
		this.tiponf = tiponf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getCnpjremetente() {
		return cnpjremetente;
	}

	public void setCnpjremetente(String cnpjremetente) {
		this.cnpjremetente = cnpjremetente;
	}
	 
	

}
