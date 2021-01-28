package servicesNFE;

public abstract class NFEOps {
	
	
	  String pass = "";
	  String cnpj = "";
	  String path_pk = "";
	  String xml = "";
	  
	  
	  public NFEOps(String cnpj, String path_pk, String pass) {
		  
		  this.path_pk = path_pk;
		  this.pass = pass;
		  this.cnpj = cnpj;
	
	  }	  
	

}
