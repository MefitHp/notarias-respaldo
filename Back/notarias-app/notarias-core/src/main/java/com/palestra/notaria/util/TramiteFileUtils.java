package com.palestra.notaria.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.DocumentosOriginalesBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;

public class TramiteFileUtils {
	
	
	public static String obtieneRutaFolderByActoDocExp(String idactodoc){
		try{
			ActoDocumentoBo actoDocumentoBo = new ActoDocumentoBoImpl();
			TramiteBo tramiteBo = new TramiteBoImpl();
			
			/** obtener ruta donde se colocara el archivo **/
			String idtramite = actoDocumentoBo
					.obtenerIdTramitePorDocumento(idactodoc);
			
			Tramite tramite = new Tramite();
			tramite.setIdtramite(idtramite);
			tramite = tramiteBo.buscarPorIdCompleto(tramite);
			
			//File dirExp = obtieneCarpetaExpediente(idtramite);
			String tramiteFolder = Constantes.EXPEDIENTES_HOME + File.separator
					+ tramite.getDsdirectorio();
			
			
			File dir = new File(tramiteFolder);
			//dirExp.setReadable(true);
			dir.setReadable(true);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
				System.out.println("=========> Se creo foler:" + tramiteFolder);
			}
			return tramiteFolder;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String obtenRutaFolderByActDoc(String idactdoc){
		try{
			ActoDocumentoBo actoDocumentoBo = new ActoDocumentoBoImpl();
			/** obtener ruta donde se colocara el archivo **/
			String idtramite = actoDocumentoBo
					.obtenerIdTramitePorDocumento(idactdoc);
			
			if (idtramite == null) {
				System.out.println(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
				return null;
			}
			
			//File dirExp = obtieneCarpetaExpediente(idtramite);
			String tramiteFolder = Constantes.EXPEDIENTES_HOME + File.separator
					+ idtramite;
			
			
			File dir = new File(tramiteFolder);
			//dirExp.setReadable(true);
			dir.setReadable(true);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
				System.out.println("=========> Se creo foler:" + tramiteFolder);
			}
			return tramiteFolder;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String obtenRutaFolderByOriginalId(String idoriginal){
		try{
			DocumentosOriginalesBo originalesBo = new DocumentosOriginalesBoImpl();
			/** obtener ruta donde se colocara el archivo **/
			String dsruta = originalesBo.obtenerTramitePorOriginal(idoriginal);
			if (dsruta == null) {
				System.out.println(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
				return null;
			}
			String tramiteFolder = Constantes.EXPEDIENTES_HOME + File.separator
					+ dsruta;
			File dir = new File(tramiteFolder);
			dir.setReadable(true);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
				System.out.println("=========> Se creo foler:" + tramiteFolder);
			}
			return tramiteFolder;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static String getYear(){
		Date datetime = new Date();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yy");
		return sdf2.format(datetime);
	}
	
	public static String creaCarpetaExpediente(Tramite tramite) throws NotariaException,IOException{
		String year = getYear();
		StringBuilder numexp = new StringBuilder(Constantes.EXPEDIENTES_HOME+File.separator);
		Usuario user = tramite.getAbogado();
		
		numexp.append(year);
		numexp.append(File.separator);
		numexp.append(user.getDsnumabogado());
		numexp.append(File.separator);		
		System.out.println("Numero de expediente:"+numexp.toString());
		File dir = new File(numexp.toString());
		dir.setReadable(true);
		if (!dir.exists() || !dir.isDirectory()) {
			FileUtils.forceMkdir(dir);
			System.out.println("=========> Se creo nuevo folder:" + numexp);
		}
		
		return numexp.toString();

	}
	
	public static String obtieneCarpetaExpediente(String idactdoc) throws NotariaException, IOException{
		String year = getYear();
		StringBuilder numexp = new StringBuilder(Constantes.EXPEDIENTES_HOME+File.separator);
		TramiteBo tramiteBo = new TramiteBoImpl();
		ActoDocumentoBo actoDocumentoBo = new ActoDocumentoBoImpl();
		String idtramite = actoDocumentoBo.obtenerIdTramitePorDocumento(idactdoc);
		Tramite tramite = new Tramite();
		tramite.setIdtramite(idtramite);
		tramite = tramiteBo.buscarPorIdCompleto(tramite);
		Usuario user = tramite.getAbogado();
		
		numexp.append(year);
		numexp.append(File.separator);
		numexp.append(user.getDsnumabogado());
		numexp.append(File.separator);
		File dir = new File(numexp.toString());
		dir.setReadable(true);
		if (!dir.exists() || !dir.isDirectory()) {
			FileUtils.forceMkdir(dir);
			System.out.println("=========> Se creo nuevo folder:" + numexp);
		}
		numexp.append(dir.listFiles().length);
		numexp.append(File.separator);
		dir = new File(numexp.toString());
		dir.setReadable(true);
		if (!dir.exists() || !dir.isDirectory()) {
			FileUtils.forceMkdir(dir);
			System.out.println("=========> Se creo nuevo folder:" + numexp);
		}
		
		return numexp.toString();
		
	}
	
	public static String obtenRutaFolderByExpediente(String idexpediente){
		try{
			//ExpedienteBo expedienteBo = new ExpedienteBoImpl();
			//Expediente exp = new Expediente();
			Date fecha = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			//exp.setIdexpediente(idexpediente);
			
			//exp = expedienteBo.buscarPorIdCompleto(exp);
			/** obtener ruta donde se colocara el archivo **/
			
			/*if (exp == null) {
				System.out.println(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
				return null;
			}*/
			System.out.println(cal.get(Calendar.YEAR));
			return "";
			/*
			String ExpFolder = Constantes.EXPEDIENTES_HOME + File.separator
					+ idtramite;
			File dir = new File(ExpFolder);
			dir.setReadable(true);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
				System.out.println("=========> Se creo foler:" + tramiteFolder);
			}
			return ExpFolder;*/
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	

}
