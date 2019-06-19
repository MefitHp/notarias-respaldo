package com.palestra.notaria.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import com.ibm.icu.text.SimpleDateFormat;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class PDFForms {   //extends AbstractExample 

	public PDFForms() {}

	/***
	 * fillFields rellena los campos de un archivo tipo formulario PDF y se genera una copia con los datos cargados.
	 * @param fileName Nombre del archivo origen o formato de de donde se tomaran las variables o campos a rellenar
	 * @param valores Mapa de campo-valor, que se usarn para rellenar el formulario
	 * @param newFile Es el nuevo archivo que se genera con el formulario ya relleno
	 * @return java.lang.String cadena que contiene la ruta del nuevo archivo generado
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public String fillFields(String fileName, Map<String, String> valores,
			String newFile, List<FormatoPDFDetalle> detalleList) throws IOException, COSVisitorException {
		PDDocument pdfDocument = null;
		try {
			//System.out.println("filename "+fileName);
			//System.out.println("newFile "+newFile);
			pdfDocument = PDDocument.load(fileName);
			PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			
			for(String key:valores.keySet()){
				System.out.printf("->>>>> %s = %s %n",key, valores.get(key));
			}
			for (FormatoPDFDetalle campo : detalleList) {
				//System.out.printf("Variable: %s-%s%n",campo.getDscampo(),campo.getDsvariable());
				PDField field = acroForm.getField(campo.getDscampo());
				if (field != null) {
					//System.out.printf("VALORES>>>>>>%s",valores);
					//System.out.printf("->>>>> VARIABLE %s = %s%n",campo.getDsvariable(), getValor(campo.getDsvariable(),valores));
					String valor = getValor(campo.getDsvariable(), valores);
					field.setValue(valor);
				} else {
					System.out
							.printf("gonotarias::: >>> No field found with name: %s <<<%n",
									campo.getDscampo());
				}			
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			String hoy = dateFormat.format(new Date());
			newFile = newFile.replace(".pdf", hoy+".pdf");
			
			System.out.println("=====> pdfDocument antes de guardar en "+newFile);
			
			File file = new File(newFile);
			if(file.exists()){
				System.out.println("=====> pdfDocument existe en "+newFile);
				System.out.print("=====> pdfDocument eliminar a "+newFile);
				if(file.delete()){
					System.out.println("\t\t[Ok]");
					file = new File(newFile);
				}else{
					System.out.println("\t\t[Wrong]");
					throw new IOException("=====> No logré generar el archivo "+newFile);
				}								
			}else{
				System.out.print("=====> pdfDocument no existe en "+newFile);
			}
			FileOutputStream fOut = new FileOutputStream(file);  			 
			pdfDocument.save(fOut);						
			System.out.println("=====> pdfDocument despues de guardar");
			return newFile;
		}catch(IOException | COSVisitorException e){
			e.printStackTrace();
			return null;
		} finally {
			if (pdfDocument != null) {
				System.out.println("pdfDocument "+ pdfDocument);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				System.out.println("pdfDocument "+ dateFormat.format(new Date(pdfDocument.getDocumentInformation().getCreationDate().getTimeInMillis())));
				pdfDocument.close();
			}
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
	
	/***
	 * close
	 * @param pdf archivo pdf que se requiere cerrar.
	 * @throws IOException
	 */
	public void close(PDDocument pdf) throws IOException {
		if (pdf != null) {
			pdf.close();
		}
	}

}
