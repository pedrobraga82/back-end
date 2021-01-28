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

public  class NFECom {
	
	  String soap_message = "";
	  String url_ws = "";
      String path_pk = "";
      String pass = "";
      
      
	  String xml = "";
	  
	  //cnpj = 24326252000173

	  
	  public NFECom(String soap_message, String url_ws, String path_pk, String pass) {
		  
		  this.soap_message = soap_message;
		  this.url_ws = url_ws;
		  this.path_pk = path_pk;
		  this.pass = pass;
		  
		  
	  }
	  
	  public List<String> getNotas() {
		  
		  
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
	  
	  for (int x=1 ; x <= xml2.length - 2 ; x++) { xml4.add(xml2[x].split(">")[1]);
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
	  
	  		xmlNFE.add(outStr);
	  
	  }
	 		 
	  	String xmlnfe = "<nfeProc versao=\"4.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"><infNFe Id=\"NFe41200911436073000147550010001097551155882799\" versao=\"4.00\"><ide><cUF>41</cUF><cNF>15588279</cNF><natOp>VENDAS</natOp><mod>55</mod><serie>1</serie><nNF>109755</nNF><dhEmi>2020-09-09T00:00:00-03:00</dhEmi><dhSaiEnt>2020-09-09T00:00:00-03:00</dhSaiEnt><tpNF>1</tpNF><idDest>1</idDest><cMunFG>4106902</cMunFG><tpImp>1</tpImp><tpEmis>1</tpEmis><cDV>9</cDV><tpAmb>1</tpAmb><finNFe>1</finNFe><indFinal>1</indFinal><indPres>9</indPres><procEmi>0</procEmi><verProc>0.1.0</verProc></ide><emit><CNPJ>11436073000147</CNPJ><xNome>Econet Publicacoes Periodicas Ltda</xNome><xFant>Econet Publicacoes Periodicas Ltda</xFant><enderEmit><xLgr>Rua Gago Coutinho</xLgr><nro>553</nro><xBairro>BACACHERI</xBairro><cMun>4106902</cMun><xMun>CURITIBA</xMun><UF>PR</UF><CEP>82510230</CEP><cPais>1058</cPais><xPais>Brasil</xPais><fone>4130168006</fone></enderEmit><IE>9083324829</IE><CRT>3</CRT></emit><dest><CNPJ>24326252000173</CNPJ><xNome>FLAVIA LEMES DE TOLEDO LEITE - ME</xNome><enderDest><xLgr>R DEP  BENEDITO LUCIO MACHADO 584 SALA A</xLgr><nro>0</nro><xBairro>CENTRO</xBairro><cMun>4124103</cMun><xMun>SANTO ANTONIO DA PLATINA</xMun><UF>PR</UF><CEP>86430000</CEP><cPais>1058</cPais><xPais>BRASIL</xPais></enderDest><indIEDest>9</indIEDest><email>flavialtl@gmail.com</email></dest><det nItem=\"1\"><prod><cProd>001</cProd><cEAN>SEM GTIN</cEAN><xProd>Boletim INFORMATIVO COMPLETO, Periodo de 09/2020</xProd><NCM>49029000</NCM><cBenef>PR800001</cBenef><CFOP>5101</CFOP><uCom>UNID</uCom><qCom>1.0000</qCom><vUnCom>273.4800000000</vUnCom><vProd>273.48</vProd><cEANTrib>SEM GTIN</cEANTrib><uTrib>UNID</uTrib><qTrib>1.0000</qTrib><vUnTrib>273.4800000000</vUnTrib><indTot>1</indTot></prod><imposto><vTotTrib>11.49</vTotTrib><ICMS><ICMS40><orig>0</orig><CST>41</CST></ICMS40></ICMS><PIS><PISAliq><CST>01</CST><vBC>273.48</vBC><pPIS>0.6500</pPIS><vPIS>1.78</vPIS></PISAliq></PIS><COFINS><COFINSAliq><CST>01</CST><vBC>273.48</vBC><pCOFINS>3.0000</pCOFINS><vCOFINS>8.20</vCOFINS></COFINSAliq></COFINS></imposto></det><total><ICMSTot><vBC>0</vBC><vICMS>0</vICMS><vICMSDeson>0</vICMSDeson><vFCP>0</vFCP><vBCST>0</vBCST><vST>0</vST><vFCPST>0</vFCPST><vFCPSTRet>0</vFCPSTRet><vProd>273.48</vProd><vFrete>0</vFrete><vSeg>0</vSeg><vDesc>0</vDesc><vII>0</vII><vIPI>0</vIPI><vIPIDevol>0</vIPIDevol><vPIS>1.78</vPIS><vCOFINS>8.20</vCOFINS><vOutro>0</vOutro><vNF>273.48</vNF><vTotTrib>11.49</vTotTrib></ICMSTot></total><transp><modFrete>9</modFrete></transp><pag><detPag><tPag>99</tPag><vPag>273.48</vPag></detPag></pag><infAdic><infCpl>Codigo Boleto: 6003123372|- \\nNFe ref. Parcela 43 com Venc. em 05/09/2020 \\n \\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\n \\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078</infCpl></infAdic><infRespTec><CNPJ>07504505000132</CNPJ><xContato>Ricardo Acras</xContato><email>contato@acras.com.br</email><fone>41991820810</fone></infRespTec></infNFe><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"#NFe41200911436073000147550010001097551155882799\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>tY7ldZSA5S6fg06Rbty7LxpeVN4=</DigestValue></Reference></SignedInfo><SignatureValue>qklTTEAcdRmooYawNIvyn5xoFhLDvGnQFFk4PasVCmuooAmVWt1kmDOegeC89sQ8l6VXdzN6L07kNfHLBEVC3VlfeKgShXWUFJT/L2Pw1WDbz614jbnJ9FlLKEx3B1lrHoWCFCb7JpqosM4HQcy28jkTMmbq4lHvYn7GpkV4dGwT74vxOz1E4ImzvuIjzVKe53YmJoYM2pOE17v+ybPNp3tEeVrvGY+WRAPQ6kF0hdBaDiDV4VEhci/El+dulXs53g1LxqFcedMgjcDYVDvaUBsgKcT1R3HRcMWxXCaCHiyRKqxtrmCK5hU1FwGzT81/y5HDwziXvDESJhaYHrDrrw==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIH/DCCBeSgAwIBAgIQBM/PYClpEZnYo2Q5SYH8UzANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJCUjETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRwwGgYDVQQDExNBQyBDZXJ0aXNpZ24gUkZCIEc1MB4XDTIwMDIwNjEyNTgxOFoXDTIxMDIwNTEyNTgxOFowgecxCzAJBgNVBAYTAkJSMRMwEQYDVQQKDApJQ1AtQnJhc2lsMQswCQYDVQQIDAJQUjERMA8GA1UEBwwIQ3VyaXRpYmExNjA0BgNVBAsMLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFsIGRvIEJyYXNpbCAtIFJGQjEWMBQGA1UECwwNUkZCIGUtQ05QSiBBMTEXMBUGA1UECwwOMjkxOTY1NTAwMDAxMDAxOjA4BgNVBAMMMUVDT05FVCBQVUJMSUNBQ09FUyBQRVJJT0RJQ0FTIExUREE6MTE0MzYwNzMwMDAxNDcwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCtz0MBYd8AyzME4mArdc9O6ORwVYXoZ1CC33SN+nUTk0T0hJypvIk2ZQLOKu/ZWE3FhlX/AgahMHQobjKThtOobBwV3ITFSHrOaB/Hs460m9cHIAi/obuT/yrkCuLh6e1IUb4QTO8sRbeHbUy4b5UH/l7fVdYx1fOFgU+zBwIB/fnNGrTITKtQ8DTPFC5yXDlWCUFpxwunTDmGXsRt/QnAwTLkg/LKeMqWteVdvRnVHDny7Za4/7leo4bWfErgaglIKMtfQwPE1iogpA6Ue+kr9jKYO6WwdM/lgsRb74dpdoseVsCOhiYpId/WLLZ39brm084mQFwaxIFC8HaZnY6ZAgMBAAGjggMQMIIDDDCBvwYDVR0RBIG3MIG0oD4GBWBMAQMEoDUEMzE1MDMxOTg3MDYxMzQxMDE5MDcwMDAwMDAwMDAwMDAwMDAwMDA5MDQ5NDg0OFNFU1BQUqAaBgVgTAEDAqARBA9NQVJDRUxPIEdBUkJPU0GgGQYFYEwBAwOgEAQOMTE0MzYwNzMwMDAxNDegFwYFYEwBAwegDgQMMDAwMDAwMDAwMDAwgSJjb250YWJpbGlkYWRlQGVjb25ldGVkaXRvcmEuY29tLmJyMAkGA1UdEwQCMAAwHwYDVR0jBBgwFoAUU31/nb7RYdAgutqf44mnE3NYzUIwfwYDVR0gBHgwdjB0BgZgTAECAQwwajBoBggrBgEFBQcCARZcaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9kcGMvQUNfQ2VydGlzaWduX1JGQi9EUENfQUNfQ2VydGlzaWduX1JGQi5wZGYwgbwGA1UdHwSBtDCBsTBXoFWgU4ZRaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9sY3IvQUNDZXJ0aXNpZ25SRkJHNS9MYXRlc3RDUkwuY3JsMFagVKBShlBodHRwOi8vaWNwLWJyYXNpbC5vdXRyYWxjci5jb20uYnIvcmVwb3NpdG9yaW8vbGNyL0FDQ2VydGlzaWduUkZCRzUvTGF0ZXN0Q1JMLmNybDAOBgNVHQ8BAf8EBAMCBeAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIGsBggrBgEFBQcBAQSBnzCBnDBfBggrBgEFBQcwAoZTaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9jZXJ0aWZpY2Fkb3MvQUNfQ2VydGlzaWduX1JGQl9HNS5wN2MwOQYIKwYBBQUHMAGGLWh0dHA6Ly9vY3NwLWFjLWNlcnRpc2lnbi1yZmIuY2VydGlzaWduLmNvbS5icjANBgkqhkiG9w0BAQsFAAOCAgEAdWZMNippVFch7of4gwHtzhcsuGAqnGpil29cJENfIAME1rxMaaIr5OtVz9+HojfDahK6Sh6XpwoBWqboDQkYVLwOBj3USE5KDhvx1lbCd4vmdZBbm3RA4ydyC/AbpYb4HCv0uho9CVqNXvds/NBJl2WiOKwtcl2DqicPtGSedMCgZT+cpue4KHgthrctm4vr/kTmq0ZxUeTb9bCRBFwszSJJno6RGHIzetaf/oKnSa9jfy6cvqswhcl5/E2eIbY8Q+zpmRbOFqXyzWNsBOBvkDIpK2TbFq07hIBRrGWkkx9cSO05lzVQNauSCE60yRbbSD7OKyUajp22x2iypSlFVZ2F0Lcry0u6BGHawV7P3nnndsQpwsic+oWAhTCncKkzb6FpQCSTG56PeF+nbSyuoNlucMpiJwwqV+P7im5vPtCMQw3i6dH/UzePd/PyUgfKJKaAZMkmBcQ7KH1fawGJM9tMlGhw90RNRBIE9nNt8/E5T+mhbKVZph/ymbo0ZKci/RCut4xj21os/b5IhgVxMPb5QJrrQPH5PC/lGSI0bFBkBW4eVJBde9xhArdIlyc1DdyK2mCoSJoB03p7SywAZGQaYl3WQLnai24Mo9e6OAFNMDlS0jQcz4r86VEY52RKdx+r384N7dBzscYNqoNDj0+6wxz9oD2ZIQP86GnAdQg=</X509Certificate></X509Data></KeyInfo></Signature></NFe><protNFe versao=\"4.00\"><infProt Id=\"ID141200168279573\"><tpAmb>1</tpAmb><verAplic>PR-v4_6_5</verAplic><chNFe>41200911436073000147550010001097551155882799</chNFe><dhRecbto>2020-09-09T10:52:35-03:00</dhRecbto><nProt>141200168279573</nProt><digVal>tY7ldZSA5S6fg06Rbty7LxpeVN4=</digVal><cStat>100</cStat><xMotivo>Autorizado o uso da NF-e</xMotivo></infProt></protNFe></nfeProc>,<nfeProc versao=\"4.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"><infNFe Id=\"NFe41201011436073000147550010001198271856938345\" versao=\"4.00\"><ide><cUF>41</cUF><cNF>85693834</cNF><natOp>VENDAS</natOp><mod>55</mod><serie>1</serie><nNF>119827</nNF><dhEmi>2020-10-06T00:00:00-03:00</dhEmi><dhSaiEnt>2020-10-06T00:00:00-03:00</dhSaiEnt><tpNF>1</tpNF><idDest>1</idDest><cMunFG>4106902</cMunFG><tpImp>1</tpImp><tpEmis>1</tpEmis><cDV>5</cDV><tpAmb>1</tpAmb><finNFe>1</finNFe><indFinal>1</indFinal><indPres>9</indPres><procEmi>0</procEmi><verProc>0.1.0</verProc></ide><emit><CNPJ>11436073000147</CNPJ><xNome>Econet Publicacoes Periodicas Ltda</xNome><xFant>Econet Publicacoes Periodicas Ltda</xFant><enderEmit><xLgr>Rua Gago Coutinho</xLgr><nro>553</nro><xBairro>BACACHERI</xBairro><cMun>4106902</cMun><xMun>CURITIBA</xMun><UF>PR</UF><CEP>82510230</CEP><cPais>1058</cPais><xPais>Brasil</xPais><fone>4130168006</fone></enderEmit><IE>9083324829</IE><CRT>3</CRT></emit><dest><CNPJ>24326252000173</CNPJ><xNome>FLAVIA LEMES DE TOLEDO LEITE - ME</xNome><enderDest><xLgr>R DEP  BENEDITO LUCIO MACHADO 584 SALA A</xLgr><nro>0</nro><xBairro>CENTRO</xBairro><cMun>4124103</cMun><xMun>SANTO ANTONIO DA PLATINA</xMun><UF>PR</UF><CEP>86430000</CEP><cPais>1058</cPais><xPais>BRASIL</xPais></enderDest><indIEDest>9</indIEDest><email>flavialtl@gmail.com</email></dest><det nItem=\"1\"><prod><cProd>001</cProd><cEAN>SEM GTIN</cEAN><xProd>Boletim INFORMATIVO COMPLETO, Periodo de 10/2020</xProd><NCM>49029000</NCM><cBenef>PR800001</cBenef><CFOP>5101</CFOP><uCom>UNID</uCom><qCom>1.0000</qCom><vUnCom>273.4800000000</vUnCom><vProd>273.48</vProd><cEANTrib>SEM GTIN</cEANTrib><uTrib>UNID</uTrib><qTrib>1.0000</qTrib><vUnTrib>273.4800000000</vUnTrib><indTot>1</indTot></prod><imposto><vTotTrib>11.49</vTotTrib><ICMS><ICMS40><orig>0</orig><CST>41</CST></ICMS40></ICMS><PIS><PISAliq><CST>01</CST><vBC>273.48</vBC><pPIS>0.6500</pPIS><vPIS>1.78</vPIS></PISAliq></PIS><COFINS><COFINSAliq><CST>01</CST><vBC>273.48</vBC><pCOFINS>3.0000</pCOFINS><vCOFINS>8.20</vCOFINS></COFINSAliq></COFINS></imposto></det><total><ICMSTot><vBC>0</vBC><vICMS>0</vICMS><vICMSDeson>0</vICMSDeson><vFCP>0</vFCP><vBCST>0</vBCST><vST>0</vST><vFCPST>0</vFCPST><vFCPSTRet>0</vFCPSTRet><vProd>273.48</vProd><vFrete>0</vFrete><vSeg>0</vSeg><vDesc>0</vDesc><vII>0</vII><vIPI>0</vIPI><vIPIDevol>0</vIPIDevol><vPIS>1.78</vPIS><vCOFINS>8.20</vCOFINS><vOutro>0</vOutro><vNF>273.48</vNF><vTotTrib>11.49</vTotTrib></ICMSTot></total><transp><modFrete>9</modFrete></transp><pag><detPag><tPag>99</tPag><vPag>273.48</vPag></detPag></pag><infAdic><infCpl>Codigo Boleto: 6003153552|- \\nNFe ref. Parcela 44 com Venc. em 05/10/2020 \\n \\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\n \\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078</infCpl></infAdic><infRespTec><CNPJ>07504505000132</CNPJ><xContato>Ricardo Acras</xContato><email>contato@acras.com.br</email><fone>41991820810</fone></infRespTec></infNFe><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"#NFe41201011436073000147550010001198271856938345\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>a8Db2D54tqLJr8JCt2BxEa2JHl4=</DigestValue></Reference></SignedInfo><SignatureValue>JHabJvPygC1rCFHt+OeTewoJTnfa+YBCEkOMq0MGH1okU8f5i4lDAaghPnxs1Iooz2eqefn0cBjOdfTaW85GCY/O3HRgk/CVbqOcROi19ptWuqGscts+eNqVcq2ALNHHNGYBKVw/6ExqTm5mWw8lj4GOgveaWx2c/cjJCPSo4sPO1sWV37laDg+ZlqCFRm3bLPq9kQsnEh7M0sP3c6iGhsDuVvmmOemX9vtcJ/seAWs2DPxR0GsNNGTyZD/t/E0MncA9ompHKY0e4CRuCggkfAkEo6OZbvqEiPIN+Nzd+yua8VKmNhACFwzuZBYv2OywVIpcy1qtD5/QVnTm0teN0w==</SignatureValue><KeyInfo><X509Data><X509Certificate>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</X509Certificate></X509Data></KeyInfo></Signature></NFe><protNFe versao=\"4.00\"><infProt Id=\"ID141200189657552\"><tpAmb>1</tpAmb><verAplic>PR-v4_6_7</verAplic><chNFe>41201011436073000147550010001198271856938345</chNFe><dhRecbto>2020-10-06T11:38:43-03:00</dhRecbto><nProt>141200189657552</nProt><digVal>a8Db2D54tqLJr8JCt2BxEa2JHl4=</digVal><cStat>100</cStat><xMotivo>Autorizado o uso da NF-e</xMotivo></infProt></protNFe></nfeProc>,<nfeProc versao=\"4.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"><infNFe Id=\"NFe41201111436073000147550010001302281868451548\" versao=\"4.00\"><ide><cUF>41</cUF><cNF>86845154</cNF><natOp>VENDAS</natOp><mod>55</mod><serie>1</serie><nNF>130228</nNF><dhEmi>2020-11-04T00:00:00-03:00</dhEmi><dhSaiEnt>2020-11-04T00:00:00-03:00</dhSaiEnt><tpNF>1</tpNF><idDest>1</idDest><cMunFG>4106902</cMunFG><tpImp>1</tpImp><tpEmis>1</tpEmis><cDV>8</cDV><tpAmb>1</tpAmb><finNFe>1</finNFe><indFinal>1</indFinal><indPres>9</indPres><procEmi>0</procEmi><verProc>0.1.0</verProc></ide><emit><CNPJ>11436073000147</CNPJ><xNome>Econet Publicacoes Periodicas Ltda</xNome><xFant>Econet Publicacoes Periodicas Ltda</xFant><enderEmit><xLgr>Rua Gago Coutinho</xLgr><nro>553</nro><xBairro>BACACHERI</xBairro><cMun>4106902</cMun><xMun>CURITIBA</xMun><UF>PR</UF><CEP>82510230</CEP><cPais>1058</cPais><xPais>Brasil</xPais><fone>4130168006</fone></enderEmit><IE>9083324829</IE><CRT>3</CRT></emit><dest><CNPJ>24326252000173</CNPJ><xNome>FLAVIA LEMES DE TOLEDO LEITE - ME</xNome><enderDest><xLgr>R DEP  BENEDITO LUCIO MACHADO 584 SALA A</xLgr><nro>0</nro><xBairro>CENTRO</xBairro><cMun>4124103</cMun><xMun>SANTO ANTONIO DA PLATINA</xMun><UF>PR</UF><CEP>86430000</CEP><cPais>1058</cPais><xPais>BRASIL</xPais></enderDest><indIEDest>9</indIEDest><email>flavialtl@gmail.com</email></dest><det nItem=\"1\"><prod><cProd>001</cProd><cEAN>SEM GTIN</cEAN><xProd>Boletim INFORMATIVO COMPLETO, Periodo de 11/2020</xProd><NCM>49029000</NCM><cBenef>PR800001</cBenef><CFOP>5101</CFOP><uCom>UNID</uCom><qCom>1.0000</qCom><vUnCom>273.4800000000</vUnCom><vProd>273.48</vProd><cEANTrib>SEM GTIN</cEANTrib><uTrib>UNID</uTrib><qTrib>1.0000</qTrib><vUnTrib>273.4800000000</vUnTrib><indTot>1</indTot></prod><imposto><vTotTrib>11.49</vTotTrib><ICMS><ICMS40><orig>0</orig><CST>41</CST></ICMS40></ICMS><PIS><PISAliq><CST>01</CST><vBC>273.48</vBC><pPIS>0.6500</pPIS><vPIS>1.78</vPIS></PISAliq></PIS><COFINS><COFINSAliq><CST>01</CST><vBC>273.48</vBC><pCOFINS>3.0000</pCOFINS><vCOFINS>8.20</vCOFINS></COFINSAliq></COFINS></imposto></det><total><ICMSTot><vBC>0</vBC><vICMS>0</vICMS><vICMSDeson>0</vICMSDeson><vFCP>0</vFCP><vBCST>0</vBCST><vST>0</vST><vFCPST>0</vFCPST><vFCPSTRet>0</vFCPSTRet><vProd>273.48</vProd><vFrete>0</vFrete><vSeg>0</vSeg><vDesc>0</vDesc><vII>0</vII><vIPI>0</vIPI><vIPIDevol>0</vIPIDevol><vPIS>1.78</vPIS><vCOFINS>8.20</vCOFINS><vOutro>0</vOutro><vNF>273.48</vNF><vTotTrib>11.49</vTotTrib></ICMSTot></total><transp><modFrete>9</modFrete></transp><pag><detPag><tPag>99</tPag><vPag>273.48</vPag></detPag></pag><infAdic><infCpl>Codigo Boleto: 6003184421|- \\nNFe ref. Parcela 45 com Venc. em 05/11/2020 \\n \\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\n \\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078</infCpl></infAdic><infRespTec><CNPJ>07504505000132</CNPJ><xContato>Ricardo Acras</xContato><email>contato@acras.com.br</email><fone>41991820810</fone></infRespTec></infNFe><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"#NFe41201111436073000147550010001302281868451548\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>9XTJhdPJSMG8fjLr4i4ac+IkLdc=</DigestValue></Reference></SignedInfo><SignatureValue>jfoHEqGzAMeDT+BozdSlzcIX/Zs4q3vWNxfzzhwzT43I1wuH/AM4n6rVPnBT17YEmKSDuj8YR1z8ZZc3lHN/4kbpI/1PUaejSqnkNlyin0T4rYvu2/eMdZSuGIFGx8mH8xYpU+7XcbIKliMHloPrtIXITAs6hSbcxOOi+zGWzbgiT5zmDM6jfgzINYgARHglDlFqnYZrwbjaKjLKxnMMdwsQUl7jtUYKpjI5RO4MkRizjUWer8/hbVfE3cr38OsKnAn7Z5So+LziqV8RLfG58Ej8jqUtOPujM6+ppIDb1TIiW7mycyBr9/3SIiGZMva0M8G00tPfouGx+NO5qHEfYw==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIH/DCCBeSgAwIBAgIQBM/PYClpEZnYo2Q5SYH8UzANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJCUjETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRwwGgYDVQQDExNBQyBDZXJ0aXNpZ24gUkZCIEc1MB4XDTIwMDIwNjEyNTgxOFoXDTIxMDIwNTEyNTgxOFowgecxCzAJBgNVBAYTAkJSMRMwEQYDVQQKDApJQ1AtQnJhc2lsMQswCQYDVQQIDAJQUjERMA8GA1UEBwwIQ3VyaXRpYmExNjA0BgNVBAsMLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFsIGRvIEJyYXNpbCAtIFJGQjEWMBQGA1UECwwNUkZCIGUtQ05QSiBBMTEXMBUGA1UECwwOMjkxOTY1NTAwMDAxMDAxOjA4BgNVBAMMMUVDT05FVCBQVUJMSUNBQ09FUyBQRVJJT0RJQ0FTIExUREE6MTE0MzYwNzMwMDAxNDcwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCtz0MBYd8AyzME4mArdc9O6ORwVYXoZ1CC33SN+nUTk0T0hJypvIk2ZQLOKu/ZWE3FhlX/AgahMHQobjKThtOobBwV3ITFSHrOaB/Hs460m9cHIAi/obuT/yrkCuLh6e1IUb4QTO8sRbeHbUy4b5UH/l7fVdYx1fOFgU+zBwIB/fnNGrTITKtQ8DTPFC5yXDlWCUFpxwunTDmGXsRt/QnAwTLkg/LKeMqWteVdvRnVHDny7Za4/7leo4bWfErgaglIKMtfQwPE1iogpA6Ue+kr9jKYO6WwdM/lgsRb74dpdoseVsCOhiYpId/WLLZ39brm084mQFwaxIFC8HaZnY6ZAgMBAAGjggMQMIIDDDCBvwYDVR0RBIG3MIG0oD4GBWBMAQMEoDUEMzE1MDMxOTg3MDYxMzQxMDE5MDcwMDAwMDAwMDAwMDAwMDAwMDA5MDQ5NDg0OFNFU1BQUqAaBgVgTAEDAqARBA9NQVJDRUxPIEdBUkJPU0GgGQYFYEwBAwOgEAQOMTE0MzYwNzMwMDAxNDegFwYFYEwBAwegDgQMMDAwMDAwMDAwMDAwgSJjb250YWJpbGlkYWRlQGVjb25ldGVkaXRvcmEuY29tLmJyMAkGA1UdEwQCMAAwHwYDVR0jBBgwFoAUU31/nb7RYdAgutqf44mnE3NYzUIwfwYDVR0gBHgwdjB0BgZgTAECAQwwajBoBggrBgEFBQcCARZcaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9kcGMvQUNfQ2VydGlzaWduX1JGQi9EUENfQUNfQ2VydGlzaWduX1JGQi5wZGYwgbwGA1UdHwSBtDCBsTBXoFWgU4ZRaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9sY3IvQUNDZXJ0aXNpZ25SRkJHNS9MYXRlc3RDUkwuY3JsMFagVKBShlBodHRwOi8vaWNwLWJyYXNpbC5vdXRyYWxjci5jb20uYnIvcmVwb3NpdG9yaW8vbGNyL0FDQ2VydGlzaWduUkZCRzUvTGF0ZXN0Q1JMLmNybDAOBgNVHQ8BAf8EBAMCBeAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIGsBggrBgEFBQcBAQSBnzCBnDBfBggrBgEFBQcwAoZTaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9jZXJ0aWZpY2Fkb3MvQUNfQ2VydGlzaWduX1JGQl9HNS5wN2MwOQYIKwYBBQUHMAGGLWh0dHA6Ly9vY3NwLWFjLWNlcnRpc2lnbi1yZmIuY2VydGlzaWduLmNvbS5icjANBgkqhkiG9w0BAQsFAAOCAgEAdWZMNippVFch7of4gwHtzhcsuGAqnGpil29cJENfIAME1rxMaaIr5OtVz9+HojfDahK6Sh6XpwoBWqboDQkYVLwOBj3USE5KDhvx1lbCd4vmdZBbm3RA4ydyC/AbpYb4HCv0uho9CVqNXvds/NBJl2WiOKwtcl2DqicPtGSedMCgZT+cpue4KHgthrctm4vr/kTmq0ZxUeTb9bCRBFwszSJJno6RGHIzetaf/oKnSa9jfy6cvqswhcl5/E2eIbY8Q+zpmRbOFqXyzWNsBOBvkDIpK2TbFq07hIBRrGWkkx9cSO05lzVQNauSCE60yRbbSD7OKyUajp22x2iypSlFVZ2F0Lcry0u6BGHawV7P3nnndsQpwsic+oWAhTCncKkzb6FpQCSTG56PeF+nbSyuoNlucMpiJwwqV+P7im5vPtCMQw3i6dH/UzePd/PyUgfKJKaAZMkmBcQ7KH1fawGJM9tMlGhw90RNRBIE9nNt8/E5T+mhbKVZph/ymbo0ZKci/RCut4xj21os/b5IhgVxMPb5QJrrQPH5PC/lGSI0bFBkBW4eVJBde9xhArdIlyc1DdyK2mCoSJoB03p7SywAZGQaYl3WQLnai24Mo9e6OAFNMDlS0jQcz4r86VEY52RKdx+r384N7dBzscYNqoNDj0+6wxz9oD2ZIQP86GnAdQg=</X509Certificate></X509Data></KeyInfo></Signature></NFe><protNFe versao=\"4.00\"><infProt Id=\"ID141200211227929\"><tpAmb>1</tpAmb><verAplic>PR-v4_6_10</verAplic><chNFe>41201111436073000147550010001302281868451548</chNFe><dhRecbto>2020-11-04T11:01:06-03:00</dhRecbto><nProt>141200211227929</nProt><digVal>9XTJhdPJSMG8fjLr4i4ac+IkLdc=</digVal><cStat>100</cStat><xMotivo>Autorizado o uso da NF-e</xMotivo></infProt></protNFe></nfeProc>";

      	return xmlNFE;
	  }

}











