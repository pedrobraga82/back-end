package com.tbp.av.service;

import com.tbp.av.model.User;
import com.tbp.av.model.factory.UserFactory;
import com.tbp.av.repository.UserRepository;
import com.tbp.av.security.jwt.JwtService;
import com.tbp.av.support.DateGenerator;
import com.tbp.av.support.StringSupport;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class UserService {

	 @Autowired
	    UserRepository userRepository;
	    @Autowired
	    ShaPasswordEncoder shaPasswordEncoder;
	    @Autowired
	    StringSupport stringSupport;
	    @Autowired
	    UserFactory userFactory;
	    @Autowired
	    JwtService jwtService;
	    @Autowired
	    DateGenerator dateGenerator;
	    
	    public List<User> getUsers() {
	    	
	    	return (List<User>) userRepository.findAll();
	    	
	    	
	    }
	    
	    
	    public User getByCnpj(String cnpj) {
	    	
	    		return userRepository.findByCnpj(cnpj);
	    	
	    }
	    
	    public User UpdateUser(User user, Integer id) {
	    	
	    	
	    	User usuario = userRepository.findOne(id);
	    	
	    	
	    	if (usuario !=null) {
		        String salt = stringSupport.generate();

    			usuario.setEmpresa(user.getEmpresa());
    			usuario.setCnpj(user.getCnpj());
    			usuario.setEndereco(user.getEndereco());
    			usuario.setIe(user.getIe());
    			usuario.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), salt));
    			usuario.setCnpj(user.getCnpj());
    			usuario.setSenhacertificado(user.getSenhacertificado());
    			
    			
	    	}
	    	else {
	    		usuario = null;
	    	}
	    	
	    	return userRepository.save(usuario);
	    	
	    	
	    }
	    	

	    public void create(String username, String password, String role, String cnpj, String ie,String endereco,String empresa, String senhacertificado ) {
	        String salt = stringSupport.generate();
	        User u = userFactory.create(username, shaPasswordEncoder.encodePassword(password, salt), salt, role,  cnpj, ie,endereco, empresa, senhacertificado );
	        userRepository.save(u);
	    }

	    public User isLoginValid(String username, String pass)  {
	        if(!StringUtils.hasText(username) || !StringUtils.hasText(pass)) {
	            return null;
	        }
	        String password = new String(Base64.decodeBase64(pass.getBytes()));
	        User u = userRepository.findByUsername(username);
	        if(u == null) {
	            return null;
	        }
	        if(!u.getPassword().equals(shaPasswordEncoder.encodePassword(password, u.getSalt()))) {
	            return null;
	        }
	        return u;
	    }

	    public User findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }

	    public User createUserToken(String username, String secret) {
	        String token = jwtService.createToken(username, secret, dateGenerator.getExpirationDate());
	        User u = userRepository.findByUsername(username);
	        u.setToken(token);
	        return userRepository.save(u);
	    }

	    public User validateUser(String token, String secret) {
	        String username = jwtService.getUsername(token, secret);
	        if (username != null ) {
	            User user = userRepository.findByUsername(username);
	            if (user != null && token.equals(user.getToken()) && jwtService.isValid(token, secret)) {
	                return user;
	            }
	        }
	        return null;
	    }

	    public User getById(Integer id) {
	    	
	    	return userRepository.findOne(id);
	    }
	
	
}
