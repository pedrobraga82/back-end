package com.tbp.av.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbp.av.model.Nfe;
import com.tbp.av.model.NfeDTO;
import com.tbp.av.model.User;
import com.tbp.av.service.NfeService;
import com.tbp.av.service.UserService;
import com.tbp.av.util.GetFileCertificate;
import com.tbp.av.util.NfeDataExport;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import servicesNFE.*;




@RestController
public class nfecontroller {

	@Autowired
	UserService userService;

	@Autowired
	NfeService nfeService;
		
	@CrossOrigin(allowedHeaders = "*",  origins = "*")
	@GetMapping("/nfe/{cnpj}")
	List<String> one(@PathVariable String cnpj) throws IOException {
		
		
		
	String pass = userService.getByCnpj(cnpj).getSenhacertificado();
	
	  String path = new File(".").getCanonicalPath() + "/src/main/resources/files/"; 

	
	String path_pk = path + cnpj + ".pfx"; 
				
		NFEConsulta nfeConsulta = new NFEConsulta(cnpj, path_pk, pass);
		
		return nfeConsulta.getNotas();
		
	}
	
	
	@GetMapping(value="/nfe/exportexcel/{cnpj}/{dataini}/{datafim}")
	public String ExportExcel(@PathVariable  String cnpj,
		    		@PathVariable  String dataini,
		    		@PathVariable  String datafim) throws ParseException, IOException  {	
		
		List<Nfe> nfes = (List<Nfe>) nfeService.GetNfesDate(cnpj, dataini, datafim);
		
		NfeDataExport excelExporter = new NfeDataExport(nfes);
	
		return excelExporter.export();
	

	}
	
	
	
    @PostMapping(value = "/cadnfe",consumes = "application/json", produces = "application/json")
    public void SaveNfe(@RequestBody String nfe ) throws JsonProcessingException, IOException, ParseException {

    	final ObjectMapper objectMapper = new ObjectMapper();
    	NfeDTO nfes = objectMapper.readValue(nfe, NfeDTO.class);

	
		  Nfe nf = new Nfe();
		  
		  nf.setChnfe(nfes.getChnfe()); 
		  nf.setIe(nfes.getIe());
		  nf.setNome(nfes.getNome()); 
		  nf.setTiponf(nfes.getTiponf());
		  nf.setValor(nfes.getValor());
		  nf.setUser(userService.getByCnpj(nfes.getCnpj()));
		  nf.setCnpjremetente(nfes.getCnpjremetente());
		  nf.setDataemissao(nfes.getDataemissao());

		  nfeService.SalvarNfe(nf);
		 
    }
    
    
    @GetMapping(value="nfelist")
    public List<Nfe> getNfes() {
    	
    	return nfeService.GetNfes();
    	
    }
    
    @GetMapping(value="/nfelist/{cnpj}")
    public List<Nfe> getNfesCnpj(@PathVariable  String cnpj) {
    	
    	return nfeService.GetNfesUser(cnpj);
    	
    }
    
    
    @GetMapping(value="nfelist/{cnpj}/{dataini}/{datafim}")
    public List<Nfe> getNfesUser(@PathVariable  String cnpj,
    		@PathVariable  String dataini,
    		@PathVariable  String datafim) throws ParseException  {
      	
    	return nfeService.GetNfesDate(cnpj, dataini, datafim);

    	
    	
    }
    
        
    
    
}

/*

@CrossOrigin(allowedHeaders = "*",  origins = "*")
@GetMapping("/nfe/manifesto/{cnpj}")
String Manifesto(@PathVariable String cnpj) {
	
	
	String path_pk = "/home/pedrobraga/Documents/Projetos/NFView/Arquivos/flavia.pfx"; 
	String pass = "1234"; 
		
	//NFEManifesto nfeManifesto = new NFEManifesto(cnpj, path_pk, pass);
	
	String nfejson = "{\"nfeProc\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"protNFe\":{\"versao\":4,\"infProt\":{\"nProt\":141200168279573,\"digVal\":\"tY7ldZSA5S6fg06Rbty7LxpeVN4=\",\"verAplic\":\"PR-v4_6_5\",\"dhRecbto\":\"2020-09-09T10:52:35-03:00\",\"Id\":\"ID141200168279573\",\"chNFe\":41200911436073000147550010001097551155882799,\"xMotivo\":\"Autorizado o uso da NF-e\",\"tpAmb\":1,\"cStat\":100}},\"NFe\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"infNFe\":{\"infAdic\":{\"infCpl\":\"Codigo Boleto: 6003123372|- \\\\nNFe ref. Parcela 43 com Venc. em 05/09/2020 \\\\n \\\\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\\\n \\\\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\\\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078\"},\"infRespTec\":{\"fone\":41991820810,\"CNPJ\":\"07504505000132\",\"xContato\":\"Ricardo Acras\",\"email\":\"contato@acras.com.br\"},\"det\":{\"nItem\":1,\"prod\":{\"cEAN\":\"SEM GTIN\",\"cProd\":\"001\",\"qCom\":1,\"cEANTrib\":\"SEM GTIN\",\"vUnTrib\":273.48,\"cBenef\":\"PR800001\",\"qTrib\":1,\"vProd\":273.48,\"xProd\":\"Boletim INFORMATIVO COMPLETO, Periodo de 09/2020\",\"vUnCom\":273.48,\"indTot\":1,\"uTrib\":\"UNID\",\"NCM\":49029000,\"uCom\":\"UNID\",\"CFOP\":5101},\"imposto\":{\"vTotTrib\":11.49,\"ICMS\":{\"ICMS40\":{\"orig\":0,\"CST\":41}},\"COFINS\":{\"COFINSAliq\":{\"vCOFINS\":8.2,\"CST\":\"01\",\"vBC\":273.48,\"pCOFINS\":3}},\"PIS\":{\"PISAliq\":{\"vPIS\":1.78,\"CST\":\"01\",\"vBC\":273.48,\"pPIS\":0.65}}}},\"total\":{\"ICMSTot\":{\"vCOFINS\":8.2,\"vBCST\":0,\"vICMSDeson\":0,\"vProd\":273.48,\"vSeg\":0,\"vFCP\":0,\"vFCPST\":0,\"vNF\":273.48,\"vTotTrib\":11.49,\"vPIS\":1.78,\"vIPIDevol\":0,\"vBC\":0,\"vST\":0,\"vICMS\":0,\"vII\":0,\"vFCPSTRet\":0,\"vDesc\":0,\"vOutro\":0,\"vIPI\":0,\"vFrete\":0}},\"pag\":{\"detPag\":{\"vPag\":273.48,\"tPag\":99}},\"Id\":\"NFe41200911436073000147550010001097551155882799\",\"ide\":{\"tpNF\":1,\"mod\":55,\"indPres\":9,\"tpImp\":1,\"nNF\":109755,\"cMunFG\":4106902,\"procEmi\":0,\"finNFe\":1,\"dhEmi\":\"2020-09-09T00:00:00-03:00\",\"tpAmb\":1,\"indFinal\":1,\"dhSaiEnt\":\"2020-09-09T00:00:00-03:00\",\"idDest\":1,\"tpEmis\":1,\"cDV\":9,\"cUF\":41,\"serie\":1,\"natOp\":\"VENDAS\",\"cNF\":15588279,\"verProc\":\"0.1.0\"},\"emit\":{\"xNome\":\"Econet Publicacoes Periodicas Ltda\",\"CRT\":3,\"xFant\":\"Econet Publicacoes Periodicas Ltda\",\"CNPJ\":11436073000147,\"enderEmit\":{\"fone\":4130168006,\"UF\":\"PR\",\"xPais\":\"Brasil\",\"cPais\":1058,\"xLgr\":\"Rua Gago Coutinho\",\"xMun\":\"CURITIBA\",\"nro\":553,\"cMun\":4106902,\"xBairro\":\"BACACHERI\",\"CEP\":82510230},\"IE\":9083324829},\"dest\":{\"xNome\":\"FLAVIA LEMES DE TOLEDO LEITE - ME\",\"CNPJ\":24326252000173,\"enderDest\":{\"UF\":\"PR\",\"xPais\":\"BRASIL\",\"cPais\":1058,\"xLgr\":\"R DEP  BENEDITO LUCIO MACHADO 584 SALA A\",\"xMun\":\"SANTO ANTONIO DA PLATINA\",\"nro\":0,\"cMun\":4124103,\"xBairro\":\"CENTRO\",\"CEP\":86430000},\"indIEDest\":9,\"email\":\"flavialtl@gmail.com\"},\"versao\":4,\"transp\":{\"modFrete\":9}},\"Signature\":{\"xmlns\":\"http://www.w3.org/2000/09/xmldsig#\",\"SignatureValue\":\"qklTTEAcdRmooYawNIvyn5xoFhLDvGnQFFk4PasVCmuooAmVWt1kmDOegeC89sQ8l6VXdzN6L07kNfHLBEVC3VlfeKgShXWUFJT/L2Pw1WDbz614jbnJ9FlLKEx3B1lrHoWCFCb7JpqosM4HQcy28jkTMmbq4lHvYn7GpkV4dGwT74vxOz1E4ImzvuIjzVKe53YmJoYM2pOE17v+ybPNp3tEeVrvGY+WRAPQ6kF0hdBaDiDV4VEhci/El+dulXs53g1LxqFcedMgjcDYVDvaUBsgKcT1R3HRcMWxXCaCHiyRKqxtrmCK5hU1FwGzT81/y5HDwziXvDESJhaYHrDrrw==\",\"KeyInfo\":{\"X509Data\":{\"X509Certificate\":\"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\"}},\"SignedInfo\":{\"Reference\":{\"Transforms\":{\"Transform\":[{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"},{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"}]},\"DigestMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#sha1\"},\"DigestValue\":\"tY7ldZSA5S6fg06Rbty7LxpeVN4=\",\"URI\":\"#NFe41200911436073000147550010001097551155882799\"},\"CanonicalizationMethod\":{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"},\"SignatureMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"}}}},\"versao\":4}},{\"nfeProc\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"protNFe\":{\"versao\":4,\"infProt\":{\"nProt\":141200189657552,\"digVal\":\"a8Db2D54tqLJr8JCt2BxEa2JHl4=\",\"verAplic\":\"PR-v4_6_7\",\"dhRecbto\":\"2020-10-06T11:38:43-03:00\",\"Id\":\"ID141200189657552\",\"chNFe\":41201011436073000147550010001198271856938345,\"xMotivo\":\"Autorizado o uso da NF-e\",\"tpAmb\":1,\"cStat\":100}},\"NFe\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"infNFe\":{\"infAdic\":{\"infCpl\":\"Codigo Boleto: 6003153552|- \\\\nNFe ref. Parcela 44 com Venc. em 05/10/2020 \\\\n \\\\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\\\n \\\\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\\\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078\"},\"infRespTec\":{\"fone\":41991820810,\"CNPJ\":\"07504505000132\",\"xContato\":\"Ricardo Acras\",\"email\":\"contato@acras.com.br\"},\"det\":{\"nItem\":1,\"prod\":{\"cEAN\":\"SEM GTIN\",\"cProd\":\"001\",\"qCom\":1,\"cEANTrib\":\"SEM GTIN\",\"vUnTrib\":273.48,\"cBenef\":\"PR800001\",\"qTrib\":1,\"vProd\":273.48,\"xProd\":\"Boletim INFORMATIVO COMPLETO, Periodo de 10/2020\",\"vUnCom\":273.48,\"indTot\":1,\"uTrib\":\"UNID\",\"NCM\":49029000,\"uCom\":\"UNID\",\"CFOP\":5101},\"imposto\":{\"vTotTrib\":11.49,\"ICMS\":{\"ICMS40\":{\"orig\":0,\"CST\":41}},\"COFINS\":{\"COFINSAliq\":{\"vCOFINS\":8.2,\"CST\":\"01\",\"vBC\":273.48,\"pCOFINS\":3}},\"PIS\":{\"PISAliq\":{\"vPIS\":1.78,\"CST\":\"01\",\"vBC\":273.48,\"pPIS\":0.65}}}},\"total\":{\"ICMSTot\":{\"vCOFINS\":8.2,\"vBCST\":0,\"vICMSDeson\":0,\"vProd\":273.48,\"vSeg\":0,\"vFCP\":0,\"vFCPST\":0,\"vNF\":273.48,\"vTotTrib\":11.49,\"vPIS\":1.78,\"vIPIDevol\":0,\"vBC\":0,\"vST\":0,\"vICMS\":0,\"vII\":0,\"vFCPSTRet\":0,\"vDesc\":0,\"vOutro\":0,\"vIPI\":0,\"vFrete\":0}},\"pag\":{\"detPag\":{\"vPag\":273.48,\"tPag\":99}},\"Id\":\"NFe41201011436073000147550010001198271856938345\",\"ide\":{\"tpNF\":1,\"mod\":55,\"indPres\":9,\"tpImp\":1,\"nNF\":119827,\"cMunFG\":4106902,\"procEmi\":0,\"finNFe\":1,\"dhEmi\":\"2020-10-06T00:00:00-03:00\",\"tpAmb\":1,\"indFinal\":1,\"dhSaiEnt\":\"2020-10-06T00:00:00-03:00\",\"idDest\":1,\"tpEmis\":1,\"cDV\":5,\"cUF\":41,\"serie\":1,\"natOp\":\"VENDAS\",\"cNF\":85693834,\"verProc\":\"0.1.0\"},\"emit\":{\"xNome\":\"Econet Publicacoes Periodicas Ltda\",\"CRT\":3,\"xFant\":\"Econet Publicacoes Periodicas Ltda\",\"CNPJ\":11436073000147,\"enderEmit\":{\"fone\":4130168006,\"UF\":\"PR\",\"xPais\":\"Brasil\",\"cPais\":1058,\"xLgr\":\"Rua Gago Coutinho\",\"xMun\":\"CURITIBA\",\"nro\":553,\"cMun\":4106902,\"xBairro\":\"BACACHERI\",\"CEP\":82510230},\"IE\":9083324829},\"dest\":{\"xNome\":\"FLAVIA LEMES DE TOLEDO LEITE - ME\",\"CNPJ\":24326252000173,\"enderDest\":{\"UF\":\"PR\",\"xPais\":\"BRASIL\",\"cPais\":1058,\"xLgr\":\"R DEP  BENEDITO LUCIO MACHADO 584 SALA A\",\"xMun\":\"SANTO ANTONIO DA PLATINA\",\"nro\":0,\"cMun\":4124103,\"xBairro\":\"CENTRO\",\"CEP\":86430000},\"indIEDest\":9,\"email\":\"flavialtl@gmail.com\"},\"versao\":4,\"transp\":{\"modFrete\":9}},\"Signature\":{\"xmlns\":\"http://www.w3.org/2000/09/xmldsig#\",\"SignatureValue\":\"JHabJvPygC1rCFHt+OeTewoJTnfa+YBCEkOMq0MGH1okU8f5i4lDAaghPnxs1Iooz2eqefn0cBjOdfTaW85GCY/O3HRgk/CVbqOcROi19ptWuqGscts+eNqVcq2ALNHHNGYBKVw/6ExqTm5mWw8lj4GOgveaWx2c/cjJCPSo4sPO1sWV37laDg+ZlqCFRm3bLPq9kQsnEh7M0sP3c6iGhsDuVvmmOemX9vtcJ/seAWs2DPxR0GsNNGTyZD/t/E0MncA9ompHKY0e4CRuCggkfAkEo6OZbvqEiPIN+Nzd+yua8VKmNhACFwzuZBYv2OywVIpcy1qtD5/QVnTm0teN0w==\",\"KeyInfo\":{\"X509Data\":{\"X509Certificate\":\"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\"}},\"SignedInfo\":{\"Reference\":{\"Transforms\":{\"Transform\":[{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"},{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"}]},\"DigestMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#sha1\"},\"DigestValue\":\"a8Db2D54tqLJr8JCt2BxEa2JHl4=\",\"URI\":\"#NFe41201011436073000147550010001198271856938345\"},\"CanonicalizationMethod\":{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"},\"SignatureMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"}}}},\"versao\":4}},{\"nfeProc\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"protNFe\":{\"versao\":4,\"infProt\":{\"nProt\":141200211227929,\"digVal\":\"9XTJhdPJSMG8fjLr4i4ac+IkLdc=\",\"verAplic\":\"PR-v4_6_10\",\"dhRecbto\":\"2020-11-04T11:01:06-03:00\",\"Id\":\"ID141200211227929\",\"chNFe\":41201111436073000147550010001302281868451548,\"xMotivo\":\"Autorizado o uso da NF-e\",\"tpAmb\":1,\"cStat\":100}},\"NFe\":{\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"infNFe\":{\"infAdic\":{\"infCpl\":\"Codigo Boleto: 6003184421|- \\\\nNFe ref. Parcela 45 com Venc. em 05/11/2020 \\\\n \\\\nNao Incidencia de ICMS-Art. 3o Inciso I, alinea B do Decreto 7871/2017 RICMS/PR \\\\n \\\\nProduto arangido pela Imunidade Tributaria conforme Acordao de Repercussao Geral DJe-195 do STF de 31/08/2017 . \\\\n| Trib. aprox. R$: 11,49 Federal e  0,00 Estadual Fonte: IBPT/empresometro.com.br D26078\"},\"infRespTec\":{\"fone\":41991820810,\"CNPJ\":\"07504505000132\",\"xContato\":\"Ricardo Acras\",\"email\":\"contato@acras.com.br\"},\"det\":{\"nItem\":1,\"prod\":{\"cEAN\":\"SEM GTIN\",\"cProd\":\"001\",\"qCom\":1,\"cEANTrib\":\"SEM GTIN\",\"vUnTrib\":273.48,\"cBenef\":\"PR800001\",\"qTrib\":1,\"vProd\":273.48,\"xProd\":\"Boletim INFORMATIVO COMPLETO, Periodo de 11/2020\",\"vUnCom\":273.48,\"indTot\":1,\"uTrib\":\"UNID\",\"NCM\":49029000,\"uCom\":\"UNID\",\"CFOP\":5101},\"imposto\":{\"vTotTrib\":11.49,\"ICMS\":{\"ICMS40\":{\"orig\":0,\"CST\":41}},\"COFINS\":{\"COFINSAliq\":{\"vCOFINS\":8.2,\"CST\":\"01\",\"vBC\":273.48,\"pCOFINS\":3}},\"PIS\":{\"PISAliq\":{\"vPIS\":1.78,\"CST\":\"01\",\"vBC\":273.48,\"pPIS\":0.65}}}},\"total\":{\"ICMSTot\":{\"vCOFINS\":8.2,\"vBCST\":0,\"vICMSDeson\":0,\"vProd\":273.48,\"vSeg\":0,\"vFCP\":0,\"vFCPST\":0,\"vNF\":273.48,\"vTotTrib\":11.49,\"vPIS\":1.78,\"vIPIDevol\":0,\"vBC\":0,\"vST\":0,\"vICMS\":0,\"vII\":0,\"vFCPSTRet\":0,\"vDesc\":0,\"vOutro\":0,\"vIPI\":0,\"vFrete\":0}},\"pag\":{\"detPag\":{\"vPag\":273.48,\"tPag\":99}},\"Id\":\"NFe41201111436073000147550010001302281868451548\",\"ide\":{\"tpNF\":1,\"mod\":55,\"indPres\":9,\"tpImp\":1,\"nNF\":130228,\"cMunFG\":4106902,\"procEmi\":0,\"finNFe\":1,\"dhEmi\":\"2020-11-04T00:00:00-03:00\",\"tpAmb\":1,\"indFinal\":1,\"dhSaiEnt\":\"2020-11-04T00:00:00-03:00\",\"idDest\":1,\"tpEmis\":1,\"cDV\":8,\"cUF\":41,\"serie\":1,\"natOp\":\"VENDAS\",\"cNF\":86845154,\"verProc\":\"0.1.0\"},\"emit\":{\"xNome\":\"Econet Publicacoes Periodicas Ltda\",\"CRT\":3,\"xFant\":\"Econet Publicacoes Periodicas Ltda\",\"CNPJ\":11436073000147,\"enderEmit\":{\"fone\":4130168006,\"UF\":\"PR\",\"xPais\":\"Brasil\",\"cPais\":1058,\"xLgr\":\"Rua Gago Coutinho\",\"xMun\":\"CURITIBA\",\"nro\":553,\"cMun\":4106902,\"xBairro\":\"BACACHERI\",\"CEP\":82510230},\"IE\":9083324829},\"dest\":{\"xNome\":\"FLAVIA LEMES DE TOLEDO LEITE - ME\",\"CNPJ\":24326252000173,\"enderDest\":{\"UF\":\"PR\",\"xPais\":\"BRASIL\",\"cPais\":1058,\"xLgr\":\"R DEP  BENEDITO LUCIO MACHADO 584 SALA A\",\"xMun\":\"SANTO ANTONIO DA PLATINA\",\"nro\":0,\"cMun\":4124103,\"xBairro\":\"CENTRO\",\"CEP\":86430000},\"indIEDest\":9,\"email\":\"flavialtl@gmail.com\"},\"versao\":4,\"transp\":{\"modFrete\":9}},\"Signature\":{\"xmlns\":\"http://www.w3.org/2000/09/xmldsig#\",\"SignatureValue\":\"jfoHEqGzAMeDT+BozdSlzcIX/Zs4q3vWNxfzzhwzT43I1wuH/AM4n6rVPnBT17YEmKSDuj8YR1z8ZZc3lHN/4kbpI/1PUaejSqnkNlyin0T4rYvu2/eMdZSuGIFGx8mH8xYpU+7XcbIKliMHloPrtIXITAs6hSbcxOOi+zGWzbgiT5zmDM6jfgzINYgARHglDlFqnYZrwbjaKjLKxnMMdwsQUl7jtUYKpjI5RO4MkRizjUWer8/hbVfE3cr38OsKnAn7Z5So+LziqV8RLfG58Ej8jqUtOPujM6+ppIDb1TIiW7mycyBr9/3SIiGZMva0M8G00tPfouGx+NO5qHEfYw==\",\"KeyInfo\":{\"X509Data\":{\"X509Certificate\":\"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\"}},\"SignedInfo\":{\"Reference\":{\"Transforms\":{\"Transform\":[{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"},{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"}]},\"DigestMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#sha1\"},\"DigestValue\":\"9XTJhdPJSMG8fjLr4i4ac+IkLdc=\",\"URI\":\"#NFe41201111436073000147550010001302281868451548\"},\"CanonicalizationMethod\":{\"Algorithm\":\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"},\"SignatureMethod\":{\"Algorithm\":\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"}}}},\"versao\":4}},{\"resNFe\":{\"nProt\":141200239119392,\"tpNF\":1,\"digVal\":\"RRYthKp4Qz+gEq6LUhYxwfRJTOw=\",\"xmlns:xsi\":\"http://www.w3.org/2001/XMLSchema-instance\",\"chNFe\":41201211436073000147550010001425711047416300,\"dhEmi\":\"2020-12-08T00:00:00-03:00\",\"vNF\":273.48,\"xmlns:xsd\":\"http://www.w3.org/2001/XMLSchema\",\"xNome\":\"Econet Publicacoes Periodicas Ltda\",\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"dhRecbto\":\"2020-12-08T11:05:40-03:00\",\"CNPJ\":11436073000147,\"cSitNFe\":1,\"IE\":9083324829,\"versao\":1}},{\"resNFe\":{\"nProt\":141210002381755,\"tpNF\":1,\"digVal\":\"ZcyYjGHlefv4UKxjyhGwoN6lOEY=\",\"xmlns:xsi\":\"http://www.w3.org/2001/XMLSchema-instance\",\"chNFe\":41210111436073000147550010001530801418591160,\"dhEmi\":\"2021-01-06T00:00:00-03:00\",\"vNF\":273.48,\"xmlns:xsd\":\"http://www.w3.org/2001/XMLSchema\",\"xNome\":\"Econet Publicacoes Periodicas Ltda\",\"xmlns\":\"http://www.portalfiscal.inf.br/nfe\",\"dhRecbto\":\"2021-01-06T11:10:40-03:00\",\"CNPJ\":11436073000147,\"cSitNFe\":1,\"IE\":9083324829,\"versao\":1}}";
  	
	return nfejson;
	//return nfeManifesto.CienciaOperacao();
	

}
	
*/
