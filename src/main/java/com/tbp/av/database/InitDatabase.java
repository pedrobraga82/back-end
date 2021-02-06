package com.tbp.av.database;

import com.tbp.av.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class InitDatabase {

	byte[] arquivo;
	
    @Autowired
    public InitDatabase(UserService userService) {
        userService.create("admin", "admin", "USER","","","","",arquivo);
        userService.create("tomcat", "tomcat", "USER","","","","",arquivo);
    }


}
