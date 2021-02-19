package UtilsWs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NfeWs {
	

	
	public String getNotas(String url_ws, String path_pk, String pass, String soap_message) throws Exception{


	    // Aqui vc carrega sua chave privada
	    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
	    FileInputStream fis = new FileInputStream(new File(path_pk));
	    ks.load(fis, pass.toCharArray());
	    fis.close();

	    URL url = new URL(url_ws);

	    // Com isso não será checado de o certificado do site é válido ou não
	    TrustManager[] trustAllCerts = new TrustManager[] { 
	        new X509TrustManager() {
	            @Override
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	            @Override
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	            @Override
	            public X509Certificate[] getAcceptedIssuers() {return null;}
	        }
	    };

	    // Aqui vc cria o gerenciador de chave que vai ser chamado mais a baixo
	    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    kmf.init(ks, pass.toCharArray());

	    // Cria-se o contexto da requisição, onde vc vai ignorar qualquer erro do certificado da URL (CA não reconhecida pelo java por exemplo)
	    // e também adiciona a chave privada no contexto, necesária para consumir o ws
	    SSLContext sslContext = SSLContext.getInstance("SSL");
	    sslContext.init(kmf.getKeyManagers(), trustAllCerts, new SecureRandom());

	    // define que vc vai usar o contexto em HttpsURLConnection 
	    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	    
	    HttpsURLConnection uc = (HttpsURLConnection) url.openConnection(); 
	  
	  uc.setDoOutput(true);
	  uc.setRequestMethod("POST");
	  uc.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
	  OutputStream wr = uc.getOutputStream();
	  wr.write(soap_message.getBytes());
	  wr.flush(); 
	  wr.close();
	  
	  BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream())); 
	  String inputLine; 
	  StringBuffer  response = new StringBuffer(); 
	  while ((inputLine = in.readLine()) != null) {
		  
		  response.append(inputLine); 
	
	  } 
	  
	  in.close();
	  
	  
	  return response.toString();
	 

	  
	}

	
	
	

}