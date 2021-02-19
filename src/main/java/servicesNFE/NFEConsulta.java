package servicesNFE;

import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import UtilsWs.*;
import org.json.*;



public class NFEConsulta  {
	
	  String pass = "";
	  String cnpj = "";
	  String path_pk = "";
	  String xml = "";
	  
	  //cnpj = 24326252000173

	  
	  public NFEConsulta(String cnpj, String path_pk, String pass) {
		  
		  this.path_pk = path_pk;
		  this.pass = pass;
		  this.cnpj = cnpj;
		  
		  
	  }
	  
	  public List<String> getNotas() {
		  
		  String soap_message =
				   "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap12:Header><nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NFeDistribuicaoDFe\"><versaoDados>1.00</versaoDados></nfeCabecMsg></soap12:Header><soap12:Body><nfeDistDFeInteresse xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NFeDistribuicaoDFe\"><nfeDadosMsg><distDFeInt versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><tpAmb>1</tpAmb><cUFAutor>41</cUFAutor><CNPJ>"
				   + cnpj +
				   "</CNPJ><distNSU><ultNSU>000000000000001</ultNSU></distNSU></distDFeInt></nfeDadosMsg></nfeDistDFeInteresse></soap12:Body></soap12:Envelope>";
  
			  
		  String url_ws = "https://www1.nfe.fazenda.gov.br/NFeDistribuicaoDFe/NFeDistribuicaoDFe.asmx?op=nfeDistDFeInteresse";

		  
	  String xml = "";
	  NfeWs nfeWs  = new NfeWs();
		 	  
	  try { 
		  
		  xml =  nfeWs.getNotas(url_ws, path_pk, pass, soap_message);
	  
	  } 
	  catch (Exception e) { // TODO Auto-generated catch block
	  e.printStackTrace(); } finally {
	  
	  //return result;
	  
	  }
	  
	  String[] xml2 = xml.split("</docZip>");
	  
	  List<String> xml4 = new ArrayList<String>();
	  
	  for (int x=1 ; x <= xml2.length - 2 ; x++) {
		  xml4.add(xml2[x].split(">")[1]);
	  }
	  
	  
	  List<String> xmlNFE = new ArrayList<String>();
	  
	  
	  for (int x= 0; x < xml4.size(); x ++) {
	  
	  GZIPInputStream gis = null; try { gis = new GZIPInputStream(new
	  ByteArrayInputStream(Base64.getDecoder().decode(xml4.get(x)))); } catch
	  (IOException e) {
	  
	  e.printStackTrace(); }
	  
	  BufferedReader bf = null;
	  
	  try { 
		  bf = new BufferedReader(new InputStreamReader(gis, "UTF-8")); 
		  }
	  
	  catch (UnsupportedEncodingException e) {
	  
		  e.printStackTrace(); 
		  }
	 
	  String outStr = ""; String line;
	  
	  try { 
		  while ((line=bf.readLine())!=null) { 
			  outStr += line; } 
		  } 
	  	catch (IOException e) {
	  		e.printStackTrace();
	  }
	  		
	  
	  try {  
		  JSONObject json = XML.toJSONObject(xml);   
		          String jsonString = json.toString(4);  
		          System.out.println(jsonString);  
		    
		  }catch (JSONException e) {  
		  // TODO: handle exception  
		  System.out.println(e.toString());  
		  }  
	  	
	  	JSONObject nfeJson = XML.toJSONObject(outStr);

		xmlNFE.add(nfeJson.toString());
	  
	  }
	 		 
	  
	  	
	  	return xmlNFE;
	  	
	  	
	  }

    
    
    
    

}