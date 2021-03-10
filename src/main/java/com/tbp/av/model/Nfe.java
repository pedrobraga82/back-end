package com.tbp.av.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Nfe {

	
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      Integer id;

      @JsonIgnore
	  @ManyToOne
	  @JoinColumn(name="cnpj", nullable=false)
	  private User user;
	
	  @Column
	  String chnfe;
	  
	  @Column
	  String ie;
	  
	  @Column
	  String tiponf;
	  
	  @Column
	  String nome;
	  
	  @Column
	  Float valor;

	  @Column
	  String cnpjremetente;
	  
	  @Temporal(TemporalType.DATE)

	  @Column
	  Date dataemissao;
	  
	  
	  
	  
	  
	  public Date getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}

	public String getCnpjremetente() {
		return cnpjremetente;
	}

	public void setCnpjremetente(String cnpjremetente) {
		this.cnpjremetente = cnpjremetente;
	}

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
	  
	  

	  
	  
}
