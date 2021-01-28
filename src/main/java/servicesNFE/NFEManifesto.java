package servicesNFE;

import java.util.Date;
import java.util.TimeZone;
import java.io.BufferedReader;  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.security.InvalidAlgorithmParameterException;  
import java.security.KeyStore;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;  
import java.security.cert.X509Certificate;  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Enumeration;  
import java.util.List;  
  
import javax.xml.crypto.dsig.CanonicalizationMethod;  
import javax.xml.crypto.dsig.DigestMethod;  
import javax.xml.crypto.dsig.Reference;  
import javax.xml.crypto.dsig.SignatureMethod;  
import javax.xml.crypto.dsig.SignedInfo;  
import javax.xml.crypto.dsig.Transform;  
import javax.xml.crypto.dsig.XMLSignature;  
import javax.xml.crypto.dsig.XMLSignatureFactory;  
import javax.xml.crypto.dsig.dom.DOMSignContext;  
import javax.xml.crypto.dsig.keyinfo.KeyInfo;  
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;  
import javax.xml.crypto.dsig.keyinfo.X509Data;  
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;  
import javax.xml.crypto.dsig.spec.TransformParameterSpec;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerException;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.dom.DOMSource;  
import javax.xml.transform.stream.StreamResult;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.xml.sax.SAXException;  
import UtilsWs.NfeWs;

public class NFEManifesto  {

	
	  String pass = "";
	  String cnpj = "";
	  String path_pk = "";
	  String xml = "";
	  String nsu = "";
	    private static final String NFE = "NFe";  

	public NFEManifesto(String cnpj, String path_pk, String pass) {
		
		  this.path_pk = path_pk;
		  this.pass = pass;
		  this.cnpj = cnpj;
		  this.nsu = nsu;	
		  
	}
	
	
	public String CienciaOperacao() {
		
			
		  Date data = new Date();
	        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));

		  
		/*
		 * String soap_message =
		 * "<evento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">"+
		 * "<infEvento Id=\"ID2102104120101143607300014755001000119827185693834501\">"+
		 * "<cOrgao>41</cOrgao> " + "<tpAmb>1</tpAmb>" + "<CNPJ>24326252000173</CNPJ>" +
		 * "<chNFe>" + "41201011436073000147550010001198271856938345" + "</chNFe>" +
		 * "<dhEvento>" + data.getTime() + "</dhEvento>" + "<tpEvento>210210</tpEvento>"
		 * + "<nSeqEvento>1</nSeqEvento>" + "<verEvento>1.00</verEvento>" +
		 * "<detEvento versao=\"1.00\">" +
		 * "<descEvento>Ciencia da Operacao</descEvento>" + "</detEvento>" +
		 * "</infEvento>" + "</evento>";
		 */
		  
		  
		  String soap_message ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
		  		"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" + 
		  		"  <soap12:Body>\n" + 
		  		"    <nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4\">\n" + 
		  		"    \n" + 
		  		"    <evento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\"><infEvento Id=\"ID2102104120101143607300014755001000119827185693834501\"><cOrgao>41</cOrgao> <tpAmb>2</tpAmb><CNPJ>24326252000173</CNPJ><chNFe>41201011436073000147550010001198271856938345</chNFe><dhEvento>1610540321531</dhEvento><tpEvento>210210</tpEvento><nSeqEvento>1</nSeqEvento><verEvento>1.00</verEvento><detEvento versao=\"1.00\"><descEvento>Ciencia da Operacao</descEvento></detEvento></infEvento></evento>\n" + 
		  		"    \n" + 
		  		"    </nfeDadosMsg>\n" + 
		  		"  </soap12:Body>\n" + 
		  		"</soap12:Envelope>\n" + 
		  		"";
		  
		  
		  String url_ws = "https://www.nfe.fazenda.gov.br/NFeRecepcaoEvento4/NFeRecepcaoEvento4.asmx?op=nfeRecepcaoEventoNF"; 
		  		
		  
		  
		  String xml = "";
		  //NfeWs nfeWs  = new NfeWs();
			NFERecepcaoEvento nfeRecepcaoEvento = new NFERecepcaoEvento(); 	  
		
		  try {
		  
			  xml = nfeRecepcaoEvento.RecepcaoEvento(url_ws, path_pk, pass, soap_message);
		  
		  } catch (Exception e) { // TODO Auto-generated catch block
		  e.printStackTrace(); } finally {
		  
		  
		  }
		 		
		  return xml;

		
	}
	
	 private void assinarNFe(XMLSignatureFactory fac,  
	            ArrayList<Transform> transformList, PrivateKey privateKey,  
	            KeyInfo ki, Document document, int indexNFe) throws Exception {  
	  
	        NodeList elements = document.getElementsByTagName("infNFe");  
	        org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);  
	        String id = el.getAttribute("Id");  
	  
	        Reference ref = fac.newReference("#" + id,  
	                fac.newDigestMethod(DigestMethod.SHA1, null), transformList,  
	                null, null);  
	  
	        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(  
	                CanonicalizationMethod.INCLUSIVE,  
	                (C14NMethodParameterSpec) null), fac  
	                .newSignatureMethod(SignatureMethod.RSA_SHA1, null),  
	                Collections.singletonList(ref));  
	  
	        XMLSignature signature = fac.newXMLSignature(si, ki);  
	  
	        DOMSignContext dsc = new DOMSignContext(privateKey,   
	                document.getDocumentElement().getElementsByTagName(NFE).item(indexNFe));  
	        signature.sign(dsc);  
	    }  
	
	

}
