package com.tbp.av.database;

import com.tbp.av.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class InitDatabase {

	byte[] arquivo;
	
    @Autowired
    public InitDatabase(UserService userService) {
		/*
		 * userService.create("admin", "admin",
		 * "USER","31312312","24326252000173","","","1234");
		 * userService.create("tomcat", "tomcat",
		 * "USER","432423423","879121225","","","1234"); userService.create("ftl",
		 * "admin", "USER","24326252000173","879121225","","","1234");
		 */    
    	
//    	userService.create("admin","Nf3@View2021#", "ADMIN","","","","admin empresa","");
    	
//	    public void create(String username, String password, String role, String cnpj, String ie,String endereco,String empresa, String senhacertificado ) {

    	
    	
    	
    	
    }


}
