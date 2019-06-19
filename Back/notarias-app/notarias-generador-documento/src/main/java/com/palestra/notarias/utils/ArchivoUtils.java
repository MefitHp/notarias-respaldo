package com.palestra.notarias.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;
import org.plutext.jaxb.svg11.G;

import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notarias.constantes.DirectorioLocal;

/**
 * Clase de utileria para el manejo de operaciones con archivos.
 * 
 * @author sofia
 * 
 */
public class ArchivoUtils {

	static Logger logger = Logger.getLogger(ArchivoUtils.class);

	/**
	 * Retorna el full path del archivo a procesar.
	 * 
	 * @param fileName
	 * @return Path completo del archivo.
	 */
	public static String createFullPath(String fileName) {
		try {
			File dir = new File(DirectorioLocal.FILE_PATH);
			if (!dir.exists() || !dir.isDirectory()) {
				logger.info("Create path:" + DirectorioLocal.FILE_PATH);
				FileUtils.forceMkdir(dir);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return DirectorioLocal.FILE_PATH + fileName;
	}
	
	public static String checkAndCreateDir(String path) throws NotariaException{
		File dir = new File(DirectorioLocal.FILE_PATH+path);
		try {
			if(!dir.exists() || !dir.isDirectory()){
				FileUtils.forceMkdir(dir);
			}
		} catch (Exception e) {
			throw new NotariaException("Ocurrió un error al crear el directorio");
		}

		return  dir.getPath();
	}
	
	public static String createFullPath(String fileName,String numExpediente) {
		File dir = null;
		try {
 			dir = new File(Constantes.EXPEDIENTES_HOME+File.separator+numExpediente);
			if (!dir.exists() || !dir.isDirectory()) {
				logger.info("Create path:" + DirectorioLocal.FILE_PATH);
				FileUtils.forceMkdir(dir);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dir +File.separator+ fileName;
	}
	

	/**
	 * Guarda la cadena de caracteres proporcionada en un archivo de texto.
	 * 
	 * @param text
	 *            Texto a almacenar en un archivo
	 * @param fileName
	 *            Nombre del archivo a crear
	 */
	public static void writeStringToFile(String text, String filePath) throws NotariaException{
		try {
			FileUtils.writeStringToFile(new File(filePath), text);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new NotariaException("Ocurrió una excepción al escribir el archivo, verifique que se tienen los permisos adecuado.");
		}
	}

	public static void writeReaderToFile(Reader reader, String filePath) {
		if (reader != null) {
			int data = 0;
			Writer writer = null;
			try {

				writer = new FileWriter(filePath);
				data = reader.read();
				while (data != -1) {
					// System.out.print((char) data);
					writer.write((char) data);
					data = reader.read();
				}

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Elimina un archivo del servidor.
	 * 
	 * @param fileName
	 *            Ruta del archivo a eliminar.
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile()) {
			if (FileUtils.deleteQuietly(file)) {
				logger.debug("Se ha eliminado el archivo: " + filePath);
				return true;
			} else {
				logger.debug("No se pudo eliminar el archivo: " + filePath);
				return false;
			}
		}
		return false;
	}

	/**
	 * Retorna una cadena con el contenido de un archivo dado.
	 * 
	 * @param filePath
	 *            Ruta del archivo a procesar
	 * @return String con el contenido del archivo en string
	 */
	public static String fileToString(String filePath) throws NotariaException {

		File file = new File(filePath);
		String fileString = null;

		try {
			fileString = FileUtils.readFileToString(file);
		} catch (IOException e) {
			logger.info(e.getStackTrace());
			throw new NotariaException("Ha ocurrido una excepción al extraer el texto del archivo, verifique los permisos del archivo.");
		}

		return fileString;

	}
	
	

	public static ArrayList<File> wordsInDir(String url)throws NotariaException{
		
		File[] respuesta = null;
		
		File file = new File(url);
		FilenameFilter fnfilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".doc") || name.endsWith(".docx")) {
						return true;
					}
						return false;
			}
		};
		
				
		if(file.isDirectory()){
			respuesta = file.listFiles(fnfilter);
		}else{
			throw new NotariaException("La ruta indicada no es un directorio");
		}
		
		ArrayList<File> filesArray  = new ArrayList<File>(Arrays.asList(respuesta)); 
		Collections.sort(filesArray);
		/*for(File f : filesArray){
			System.out.println("Archivo:"+f.getName());
			//System.out.println("Modificado el:"+new Date(f.lastModified()));
			
		}*/
		return filesArray;
	}
	
	public static List<File> getUltimasVersiones(List<File> filesArray){
		ArrayList<File> filesfiltered = new ArrayList<File>();
		ArrayList<File> tmpArray = new ArrayList<File>();
		for(File f: filesArray){
			String tmpstr =  f.getName().substring(0,f.getName().indexOf("."));
			if(tmpArray.size()<1){
		    	tmpArray.add(f);
		    	continue;
		    }

			String anteriorname = tmpArray.get(tmpArray.size() - 1).getName();
			anteriorname = anteriorname.substring(0,anteriorname.indexOf("."));
			int firstCharacter = findFirstCharacter(anteriorname);
			anteriorname =(anteriorname.length()>1)?anteriorname.substring(0,firstCharacter): anteriorname; 
			tmpstr =(tmpstr.length()>1)?tmpstr.substring(0,findFirstCharacter(tmpstr)): tmpstr; 
			
		    if(!anteriorname.equals(tmpstr)){
		    	filesfiltered.add(getUltimaVersion(tmpArray));
		    	tmpArray.clear();
		    	tmpArray.add(f);
		    	continue;
		    }else{
		    	tmpArray.add(f);
		    }
		    
		}
		filesfiltered.add(getUltimaVersion(tmpArray));
		return filesfiltered;
	}
	
	public static File getUltimaVersion (ArrayList<File> files){
		File respuesta = null;
		int tmpversion=0;
		if(files.size()==1){
			return files.get(0);
		}
		for(File f: files){
			int charpos = findFirstCharacter(f.getName());
			int version = Character.getNumericValue(f.getName().charAt(charpos));
			if(tmpversion < version){
				tmpversion = version;
				respuesta = f;
			}else{
				return respuesta;
			}
		}
		
		return respuesta;
		
	}
	
	public static File getUltimaVersionExpediente(String expediente,ArrayList<File> files){
		if(files.size()<1){
			return null;
		}else{
			List<File> versiones = getUltimasVersiones(files);
			for(File f:versiones){
				if(f.getName().contains(expediente)){
					return f;
				}
			}
		}
		
		return null;
		
	}
	
	
	public static int findFirstCharacter(String string){
		Pattern p = Pattern.compile("\\p{Alpha}");
		if(string.indexOf(".")>-1){
		    string = string.substring(0,string.indexOf("."));
		}
		Matcher m = p.matcher(string);
		if (m.find()) {
		   
			return m.start();
		}else{
			return 1;
		}
	}
	
	

}
