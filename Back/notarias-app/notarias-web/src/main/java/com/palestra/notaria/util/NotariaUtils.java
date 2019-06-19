package com.palestra.notaria.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.palestra.notaria.bo.ManejoSesionBo;
import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.bo.impl.ManejoSesionBoImpl;
import com.palestra.notaria.bo.impl.UsuarioGrupoTrabajoBoImp;
import com.palestra.notaria.envio.GenericEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.ManejoSesion;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class NotariaUtils {

	public static boolean isSesionValida(String idsesion, String idusuario){
		ManejoSesion ms = null;
		ManejoSesionBo manejoSesionBo = new ManejoSesionBoImpl();
		
		try {
			ms = manejoSesionBo.findBySesionAndUser(idsesion, idusuario);
			ms.setIdsesion(idsesion);
			ms.setIdusuario(idusuario);
		} catch (NotariaException e) {
			e.printStackTrace();
		}
		if (ms != null) {
			if (ms.getIslogout())
				return false;
			else
				return true;
		} else
			return false;
	}
	
	public static GrupoTrabajo getGrupoXUsiario(String idusuario) throws NotariaException{
		UsuarioGrupoTrabajoBo gpotrabBo = new UsuarioGrupoTrabajoBoImp();
		try {
			UsuarioGrupoTrabajo usrgpo = gpotrabBo.buscarXusuario(idusuario);
			if(usrgpo!=null){
				return usrgpo.getGrupoTrabajo();
			}
		} catch (NotariaException e) {
			throw new NotariaException("Algo malo ocurrio al obtener el grupo de trabajo del usuario");
		}
		
		return null;
	}

	public static String getUsrBpm(String idusuario,String rol) throws NotariaException{
		
		switch (rol) {
		case "asis":
			UsuarioGrupoTrabajoBo gpotrabBo = new UsuarioGrupoTrabajoBoImp();

			try {
				UsuarioGrupoTrabajo usrgpo = gpotrabBo.buscarXusuario(idusuario);
				if(usrgpo!=null){
					return usrgpo.getGrupoTrabajo().getResponsable().getCdusuario();
				}
			} catch (NotariaException e) {
				throw new NotariaException("Algo malo ocurrio al obtener el grupo de trabajo del usuario");
			}
			break;
		default:			
			break;
		}
		
		return rol;
		
	}
	
	/***
	 * 
	 * Convierte un arreglo de bytes a String usando valores hexadecimales
	 * @param digest
	 *            arreglo de bytes a convertir
	 * 
	 * @return String creado a partir de <code>digest</code>
	 */
	private static String toHexadecimal(byte[] digest) {

		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}
	
	public static int numberDirectory(String dirPath) {
	    File f = new File(dirPath);
	    File[] files = f.listFiles();
	    int count = 0;
	    if (files != null)
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];

	        if (file.isDirectory()) {   
	             count++; 
	        }
	    }
	    return count;
	}

	/***
	 * 
	 * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
	 * 
	 * @param message
	 *            texto a encriptar
	 *            algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1,
	 *            SHA-256, SHA-384, SHA-512
	 * 
	 * @return mensaje encriptado
	 */
	public static String getStringMessageDigest(String message) {
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Error creando Digest");
		}
		return toHexadecimal(digest);
	}
	
	/***
	 * 
	 * Guarda un archivo en un ruta especifica
	 * 

	 * 
	 * @return boolean indicando exito o fracaso
	 */
	public static boolean writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
		boolean b = false;
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			b = true;
		} catch (IOException e) {

			e.printStackTrace();
			return b;
		}
		return b;
	}
	
	public static boolean moverDirectorio(String viejaLocacion,
			String nuevaLocacion) {
		boolean b = false;
		try {
			InputStream in = new FileInputStream(viejaLocacion);
			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(nuevaLocacion));
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			in.close();
			File ruta = new File( viejaLocacion ); 
			ruta.delete();
			b = true;
		} catch (IOException e) {

			e.printStackTrace();
			return b;
		}
		return b;
	}
	
	public static ResponseBuilder obtenerArchivo(String archivo,String realPath){
		
		ResponseBuilder response=null;
		try {
			String nombreArchivo = archivo.replace(realPath + File.separator + "uploaded", "");
			response=  Response.ok(new FileInputStream(archivo)).type("application/png");
			response.header("Content-Disposition","attachment; filename="+nombreArchivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
	
	public static boolean eliminarArchivo(String archivo){
		boolean b = false;
		try{
			File file = new File( archivo ); 
			file.delete();
			b = true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return b;
	}
	
	/**
	 * Analiza los requeridos de usuario, idusuario y idsesionactual
	 * 
	 * @param request
	 * @return true si faltan requeridos, falso de otra manera
	 */
	public static boolean faltanRequeridosUsuario(GenericEnvio request){
		if(request.getUsuario() == null
				|| request.getUsuario().getIdusuario() == null
				|| request.getUsuario().getIdusuario().isEmpty()
				|| request.getUsuario().getIdsesionactual() == null
				|| request.getUsuario().getIdsesionactual().isEmpty()){
			return true;
		}
		return false;
	}
	
	public static boolean guardarArchivoServidor(String rutaFolder,
			String nombreArchivo, InputStream uploadedInputStream) {
		try {
			File dir = new File(rutaFolder);
			if (!dir.exists() || !dir.isDirectory()) {
				System.out
						.println("============> Se creo folder:" + rutaFolder);
				FileUtils.forceMkdir(dir);
			}
			String uploadedFileLocation = rutaFolder + File.separator
					+ nombreArchivo;
			boolean b = NotariaUtils.writeToFile(uploadedInputStream,
					uploadedFileLocation);
			return b;
		} catch (IOException e) {
			uploadedInputStream = null;
			e.printStackTrace();
			return false;
		}
	}
	
//	public static String getUdis() throws SAXException, IOException, ParserConfigurationException{
//		DgieWSPort asdf = new DgieWSPortProxy();
////		System.out.println("UDIS ********************* "+asdf.udisBanxico());
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		Document document = builder.parse(new InputSource(new StringReader(asdf.udisBanxico())));
//		Element rootElement = document.getDocumentElement();
////		String valor = notariautil.getString("bm:Obs",rootElement);
//		NodeList list = rootElement.getElementsByTagName("bm:Obs");
//		Node nodo = list.item(0).getAttributes().getNamedItem("OBS_VALUE");
//		String valor = nodo.getNodeValue();
//		System.out.println("VALOR ****** "+valor);
//		return valor;
//	}
	
	public static void recuperaDoc(String ruta, HttpServletResponse response)
			throws IOException, FileNotFoundException {
		File archivo = new File(ruta);
		response.setContentLength((int) archivo.length());
		if(ruta.contains(".docx")){
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			int inicio =ruta.lastIndexOf("/");
			String file=ruta.substring(inicio+1);
			response.setHeader("Content-Disposition","attachment;filename="+file);
		}
		FileInputStream in = new FileInputStream(archivo);
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[Integer.valueOf(String.valueOf(archivo.length()))];
		int len = 0;
		while ((len = in.read(buf)) >= 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
	public static String recuperaDoctoString(String ruta) throws IOException, FileNotFoundException{
		File archivo = new File(ruta);
		FileInputStream in = new FileInputStream(archivo);
		return IOUtils.toString(in, "UTF-8"); 
	}
	
	public static void recuperaDocWord(String ruta,HttpServletResponse response,String nombre) throws IOException{
		response.reset();
		response.resetBuffer();
		response.setContentType("application/msword");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
		InputStream in = new FileInputStream(ruta);
		OutputStream out = response.getOutputStream();
		IOUtils.copy(in, out);

		out.flush();
		out.close();
		in.close();
		response.flushBuffer();
	}
	
	/**
	 * @author Víctor Espinosa
	 * @param file Archivo mediante el cual se obtendrán los datos de salida
	 * @param lines Lineas que se desean obtener del archivo
	 * @return String que devuelve los resultados obtenidos
	 * 
	 */
	public static String tail( File file, int lines) {
	    java.io.RandomAccessFile fileHandler = null;
	    try {
	        fileHandler = 
	            new java.io.RandomAccessFile( file, "r" );
	        long fileLength = fileHandler.length() - 1;
	        StringBuilder sb = new StringBuilder();
	        int line = 0;

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	             if( readByte == 0xA ) {
	                if (filePointer < fileLength) {
	                    line = line + 1;
	                }
	            } else if( readByte == 0xD ) {
	                if (filePointer < fileLength-1) {
	                    line = line + 1;
	                }
	            }
	            if (line >= lines) {
	                break;
	            }
	            sb.append( ( char ) readByte );
	        }

	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    }
	    finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	            }
	    }
	}
	
	/**
	 * Función que elimina acentos y caracteres especiales de
	 * una cadena de texto.
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String removeSpecials(String input) {
	    // Cadena de caracteres original a sustituir.
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }//for i
	    return output;
	}
	
	
	public static String getProperties(String propiedad)throws NotariaException{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "notaria.properties";
			
			
    		input = NotariaUtils.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            throw new NotariaException("Lo siento, no encontré el archivo: " + filename);
    		}

    		//load a properties file from class path, inside static method
    		prop.load(input);

			// get the property value and print it out
			return prop.getProperty(propiedad);
			

		} catch (IOException ex) {
			ex.printStackTrace();
			
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
		
	}
	
// METODO PARA OBTENER VALORES DE UN TAG PERO NO ATRIBUTOS
//	protected String getString(String tagName, Element element) {
//        NodeList list = element.getElementsByTagName(tagName);
//        if (list != null && list.getLength() > 0) {
//            NodeList subList = list.item(0).getChildNodes();
//
//            if (subList != null && subList.getLength() > 0) {
//                return subList.item(0).getNodeValue();
//            }
//        }
//
//        return null;
//    }
}
