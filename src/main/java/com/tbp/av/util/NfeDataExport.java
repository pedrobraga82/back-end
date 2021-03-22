package com.tbp.av.util;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resources;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import com.tbp.av.model.Nfe;
import com.tbp.av.model.NfeDTO;

import  org.apache.poi.hssf.usermodel.HSSFSheet;  
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFFont;
import  org.apache.poi.hssf.usermodel.HSSFRow;  


import UtilsWs.MediaTypeUtils;

public class NfeDataExport {

	private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private List<Nfe>  nfes;
    
	   @Autowired
	    private ServletContext servletContext;
     
    public NfeDataExport(List<Nfe> nfes2) {
    	
        this.nfes = nfes2;
        workbook = new HSSFWorkbook();
    }
 
    public String export() throws IOException {
        	
        sheet = workbook.createSheet("Nfes");   

        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 16);
        style.setFont(font);
        HSSFRow rowhead = sheet.createRow((short)0);  

         
        rowhead.createCell(0).setCellValue("Número NFE");  
 
        rowhead.createCell(1).setCellValue("Inscrição Estadual");       
        rowhead.createCell(2).setCellValue("Tipo NF");    
        rowhead.createCell(3).setCellValue("Valor");
        rowhead.createCell(4).setCellValue("CNPJ Remetente");
        rowhead.createCell(5).setCellValue("Nome");
    	
        
        int rowCount = 2;
        
        for (Nfe nfe : nfes) {
           
        	
            HSSFRow rowhead2 = sheet.createRow((short)rowCount++);  

            rowhead2.createCell(0).setCellValue( nfe.getChnfe());            
            rowhead2.createCell(1).setCellValue( nfe.getIe());
            rowhead2.createCell(2).setCellValue( nfe.getTiponf());
            rowhead2.createCell(3).setCellValue( nfe.getValor());
            rowhead2.createCell(4).setCellValue( nfe.getCnpjremetente());
            rowhead2.createCell(5).setCellValue( nfe.getNome());
            
        }
        
        
        
        
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String file = currentDateTime +  ".xls";
        
		String path = "src/main/resources/files/Planilha/" + file ;

        FileOutputStream fileOut = new FileOutputStream(path);  
        workbook.write(fileOut);  
        //closing the Stream  
        
  
        fileOut.close();  
        
        workbook.close();
        
        return path;
        
		/*
		 * org.springframework.http.MediaType mediaType =
		 * MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file);
		 * 
		 * File filew = new File(path); InputStreamResource resource = new
		 * InputStreamResource(new FileInputStream(filew));
		 * 
		 * 
		 * return ResponseEntity.ok()
		 * 
		 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filew)
		 * .contentType(mediaType) .contentLength(file.length()) // .body(resource);
		 */
        
    }
    
	
}
