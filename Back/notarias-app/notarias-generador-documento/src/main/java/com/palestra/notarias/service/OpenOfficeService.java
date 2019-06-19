package com.palestra.notarias.service;

import java.io.File;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.palestra.notarias.utils.AlfrescoPropsUtils;

/**
 * Helper para convertir documentos de un formato a otro. Se utiza jod converter
 * para llevar a cabo las converiones de formato.
 * 
 * @author sofia
 * 
 */
public class OpenOfficeService {

	static Logger logger = Logger.getLogger(OpenOfficeService.class);

	OfficeManager officeManager = null;

	/**
	 * Convierte un archivo de un formato origen a otro destino.
	 * 
	 * Las conversiones pueden ser: - HTML to DOC - HTML to PDF
	 * 
	 * @param sourceFile
	 *            Ruta del archivo origen
	 * @param targetFile
	 *            Ruta del archivo destino
	 */
	public boolean convertFile(String sourceFile, String targetFile) {

		try {
			//String officeHome = AlfrescoPropsUtils.getPropertyValue("openoffice.home");
			String officeHome = "/usr/lib/libreoffice";
			//String officeHome = "/usr/lib64/libreoffice";
			officeManager = new DefaultOfficeManagerConfiguration().setOfficeHome(officeHome).buildOfficeManager();
			officeManager.start();
			OfficeDocumentConverter oConverter = new OfficeDocumentConverter(officeManager);
			long start = System.currentTimeMillis();
			oConverter.convert(new File(sourceFile), new File(targetFile));
			logger.info("Se grenero el archivo archivo en: " + (System.currentTimeMillis() - start) + "ms");
			File file  =new File(targetFile);
			file.setWritable(true,false);
			file.setReadable(true, false);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		} 
		finally {
			if (officeManager != null) {
				officeManager.stop();
			}
		}
	}
}
