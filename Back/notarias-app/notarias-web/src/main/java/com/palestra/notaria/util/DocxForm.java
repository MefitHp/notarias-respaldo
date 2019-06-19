package com.palestra.notaria.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.ibm.icu.text.SimpleDateFormat;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class DocxForm {

	public String replaceFields(String fileName, Map<String, String> valores,
			String newFile, List<FormatoPDFDetalle> detalleList) throws NotariaException, IOException{
		List<String> camposEnString = new ArrayList<String>();
		for(String key:valores.keySet()){
			System.out.printf("->>>>> %s = %s %n",key, valores.get(key));
		}
		XWPFDocument doc;
		FileOutputStream out=null;
		try {
			doc = new XWPFDocument(OPCPackage.open(fileName));
		
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						for(FormatoPDFDetalle campoFormato:detalleList){
							if (text != null && text.contains("$"+campoFormato.getDscampo())) {
								String valor = getValor(campoFormato.getDsvariable(), valores);
								text = text.replace("$"+campoFormato.getDscampo(), valor);
								r.setText(text, 0);
							}
						}
					}
				}
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			String hoy = dateFormat.format(new Date());
			newFile = newFile.replace(".docx", hoy+".docx");
			
			System.out.println("=====> docxDocument antes de guardar en "+newFile);
			
			File file = new File(newFile);
			if(file.exists()){
				System.out.println("=====> docxDocument existe en "+newFile);
				System.out.print("=====> docxDocument eliminar a "+newFile);
				if(file.delete()){
					System.out.println("\t\t[Ok]");
					file = new File(newFile);
				}else{
					System.out.println("\t\t[Wrong]");
					throw new IOException("=====> No logré eliminar el archivo "+newFile);
				}								
			}else{
				System.out.print("=====> docxDocument no existe en "+newFile);
			}
			out = new FileOutputStream(newFile);
			doc.write(out);
			return newFile;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new NotariaException("Formato de archivo invalido, error:"+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NotariaException("Error en archivo:"+e.getMessage());
		}finally{
			out.close();
		}
		
	}
	
	private String getValor(String llave, Map<String, String> valores){
		for(String key:valores.keySet()){
			if(llave.contains(key)){
				if(valores.get(key)!=null){
					System.out.printf("llave: %s - key: %s - value %s%n", llave, key,valores.get(key));
					return valores.get(key);
				}else{
					return "";
				}
			}
		}
		return "";
	}
}
