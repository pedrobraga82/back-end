package com.tbp.av.model;


import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    String password;
    
    @Column(nullable = true)
    String senhacertificado;
    
	/*
	 * @Column(nullable = false) String salt;
	 * 
	 * @Column(nullable = false) String role;
	 */
    @Column
    String empresa;
    @Column
    String endereco;
    @Column
	public
    String cnpj;
    @Column
    String ie;
    @Column
    String token;
    @Column
    String salt;
    @Column 
    String role;
    
    @Lob
    @Column
    private byte[] arquivo;

    @OneToMany(mappedBy="user")
    private List<Nfe> nfe;
    
    
    
    public User() {

    }

    public User(String username,String password, String salt, String role,  String cnpj, String ie,String endereco,String empresa, String senhacertificado ) {
        this.username = username;
        this.password = password;
        this.cnpj = cnpj;
        this.ie = ie;
        this.endereco = endereco;
        this.empresa = empresa;
        this.salt = salt;
   		this.role = role;
   		this.senhacertificado = senhacertificado;
   	//	this.arquivo = arquivo;

    }
    
    
    
    
    
    

    public String getSenhacertificado() {
		return senhacertificado;
	}

	public void setSenhacertificado(String senhacertificado) {
		this.senhacertificado = senhacertificado;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
