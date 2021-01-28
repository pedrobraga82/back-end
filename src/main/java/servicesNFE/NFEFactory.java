package servicesNFE;

import java.util.List;

public class NFEFactory {
	
	String cnpj = "";
    String soap_message = "";
    String url_ws = "";
    String path_pk = "";
    String pass = "";
    String xml = "";
	  
	  //cnpj = 24326252000173
	  
	public NFEFactory(String soap_message, String url_ws, String path_pk, String pass) {
		  
		  this.soap_message = soap_message;
		  this.url_ws = url_ws;
		  this.path_pk = path_pk;
		  this.pass = pass;
		  
		  
	  }
	
	
	 
	public List<String> getDadosWs(String operacao) {
		
		
		if (operacao == "getNotas") {
			
		}
		
		
		
		
		
		return null;
		
		
		
		
		
	}
	

}
