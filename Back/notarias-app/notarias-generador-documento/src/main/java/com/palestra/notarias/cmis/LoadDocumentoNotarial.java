package com.palestra.notarias.cmis;

import java.io.File;
import java.io.IOException;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.NotariaBo;
import com.palestra.notaria.bo.impl.NotariaBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notarias.helper.BitacoraVersionesEscrituraHelper;

/**
 * Sube a alfresco el documento notarial generado.
 * 
 * @author sofia
 * 
 */
public class LoadDocumentoNotarial {

	private static Logger logger = Logger.getLogger(LoadFiles.class);

	private LoadFiles loadFiles = new LoadFiles();

	private BitacoraVersionesEscrituraHelper bitacoraVersionesEscrituraHelper = new BitacoraVersionesEscrituraHelper();

	/**
	 * Sube el documento notarial generado a la carpeta de su expediente
	 * 
	 * @param fileSourcePath
	 *            Ruta del archivo a subir
	 * @param idEscritura
	 * @param numExpediente
	 * @param inicialesNotario
	 * @return
	 * @throws IOException
	 * @throws NotariaException 
	 */
	public Document loadDocumentoNotarial(String fileSourcePath,
			String idEscritura, String numExpediente, String inicialesNotario)
			throws IOException, NotariaException {

		NotariaBo notariaBo = new NotariaBoImpl();
		/**
		 * se obtiene el numero de la notaria para acceder al su sitio y ahi
		 * colocar el archivo
		 **/
		String numNotaria = notariaBo
				.obtenerNumNotariaByInicialesNotario(inicialesNotario);
		if (numNotaria == null || numNotaria.isEmpty()) {
			return null;
		}
		Session cmisSession = null;
		Document documento = null;
		Folder sitioNotariaFolder = null;
		Folder notarioFolder = null;
		Folder expedienteFolder = null;

		try {
			cmisSession = loadFiles.getCmisSession();

			/** path de la carpeta del sitio donde se depositaran los archivos **/
			/** Ejemplo path: /Sitios/notaria98/documentLibrary/escrituras/ **/
			//Nota: esta ruta ya deberia estar creada, a nivel de escrituras se coloca el custom-action.
			String siteDocumentLibraryPath = LocalBaseLoadFile.FOLDER_PATH
					+ LocalBaseLoadFile.NOTARIA_SITE + numNotaria + LocalBaseLoadFile.DOCUMENT_LIBRARY;
			
			/** Referencia del folder de la ruta dada **/
			sitioNotariaFolder = loadFiles.getFolder(cmisSession,
					siteDocumentLibraryPath);

			/** crear u obtener el folder con las iniciales del notario **/
			notarioFolder = loadFiles.createFolder(cmisSession,
					sitioNotariaFolder, inicialesNotario);

			/** crear u obtener el folder del expediente **/
			expedienteFolder = loadFiles.createFolder(cmisSession,
					notarioFolder, numExpediente);

		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}

		File file = new File(fileSourcePath);

		if (file.isFile()) {
			documento = loadFiles.getDocumentByPath(cmisSession,
					expedienteFolder, file.getName());
			if (documento != null) {
				logger.info("Ya existe el documento. Actualizarlo");
				/** Si ya existe el documento, crear nueva version **/
				documento = loadFiles.updateDocumento(documento, cmisSession,
						file);
				// NOTA: el action del update en alfresco notifica la nueva
				// version.
			} else {
				/** Crear nuevo documento **/
				logger.info("No existe documento. Crearlo");
				documento = loadFiles.createDocument(cmisSession,
						expedienteFolder, file, LoadFiles.FILE_TYPE, null);
				if (documento == null) {
					logger.info("No se pudo insertar el documento en alfresco ");
					return null;
				}
				String documentoPath = expedienteFolder.getPath() + "/"
						+ file.getName();
				/**
				 * getId() retorna idNodo;1.0, por lo que se toma solamente el
				 * id
				 **/
				String alfrescoDocumentId = StringUtils.substringBefore(
						documento.getId(), ";");

				/** Notifica que se ha generado un documento **/
				bitacoraVersionesEscrituraHelper.registraBitacora(idEscritura,
						documentoPath, file.getName(),
						documento.getVersionLabel(), alfrescoDocumentId);

			}

		}
		return documento;
	}

}
