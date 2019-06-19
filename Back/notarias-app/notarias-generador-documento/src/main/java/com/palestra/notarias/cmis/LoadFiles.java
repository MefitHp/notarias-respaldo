package com.palestra.notarias.cmis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.log4j.Logger;

/***
 * Cargar archivos .doc de una carpeta local en un repositorio CMIS.
 * 
 * @author jpotts
 * 
 */
public class LoadFiles extends LocalBaseLoadFile {

	static Logger logger = Logger.getLogger(LoadFiles.class);

	/**
	 * Code assumes that every file is of the type below
	 */
	public static final String FILE_TYPE = "application/msword";

	/**
	 * Gets or creates a folder named folderName in the parentFolder.
	 * 
	 * @param cmisSession
	 * @param parentFolder
	 * @param folderName
	 * @return
	 */
	public Folder createFolder(Session cmisSession, Folder parentFolder,
			String targetFolder) {

		Folder subFolder = null;
		try {

			subFolder = (Folder) cmisSession.getObjectByPath(parentFolder
					.getPath() + "/" + targetFolder);
			logger.info("Ya existe folder. " + targetFolder);

		} catch (CmisObjectNotFoundException onfe) {
			Map<String, Object> props = new HashMap<String, Object>();
			props.put("cmis:objectTypeId", "cmis:folder");
			props.put("cmis:name", targetFolder);
			subFolder = parentFolder.createFolder(props);
			String subFolderId = subFolder.getId();
			logger.info("Folder creado exitosamente: " + subFolderId);
		}

		return subFolder;
	}

	/**
	 * Obtiene un objeto folder a partir de un path dado
	 * 
	 * @param cmisSession
	 * @param parentFolder
	 * @param targetFolder
	 * @return
	 */
	public Folder getFolder(Session cmisSession, String path) {
		Folder folder = null;
		try {
			folder = (Folder) cmisSession.getObjectByPath(path);
			logger.info("Existe folder. " + path);
			return folder;
		} catch (CmisObjectNotFoundException onfe) {
			onfe.printStackTrace();
			logger.info("No existe folder el path dado");
			return null;
		}
	}

	/**
	 * Use the CMIS API to create a document in a folder
	 * 
	 * @param cmisSession
	 * @param parentFolder
	 * @param file
	 * @param fileType
	 * @param props
	 * @return
	 * @throws FileNotFoundException
	 * 
	 */
	public Document createDocument(Session cmisSession, Folder parentFolder,
			File file, String fileType, Map<String, Object> props)
			throws FileNotFoundException {

		String fileName = file.getName();

		// create a map of properties if one wasn't passed in
		if (props == null) {
			props = new HashMap<String, Object>();
		}

		// Add the object type ID if it wasn't already
		if (props.get("cmis:objectTypeId") == null) {
			props.put("cmis:objectTypeId", "cmis:document");
		}

		// Add the name if it wasn't already
		if (props.get("cmis:name") == null) {
			props.put("cmis:name", fileName);
		}

		ContentStream contentStream = cmisSession.getObjectFactory()
				.createContentStream(fileName, file.length(), fileType,
						new FileInputStream(file));

		Document document = null;
		try {
			document = parentFolder.createDocument(props, contentStream, null);
			logger.info("Nuevo documento creado: " + document.getId());
		} catch (CmisContentAlreadyExistsException ccaee) {
			document = (Document) cmisSession.getObjectByPath(parentFolder
					.getPath() + "/" + fileName);
			logger.info("Ya existe el documento: " + fileName);
		}

		return document;
	}

	public Document getDocumentByPath(Session cmisSession, Folder parentFolder,
			String fileName) {
		Document documento = null;
		try {
			String pathFile = parentFolder.getPath() + "/" + fileName;
			logger.info("Path del archivo: " + pathFile);
			documento = (Document) cmisSession.getObjectByPath(pathFile);
		} catch (CmisObjectNotFoundException onf) {
			logger.info("No se encontro el archivo especificado");
		}
		return documento;
	}

	/**
	 * Genera una nueva version del documento con el contenido del archivo dado.
	 * 
	 * @param document
	 *            Documento a actualizar
	 * @param cmisSession
	 *            Session de cmis
	 * @param file
	 *            Archivo con el nuevo contenido
	 */
	public Document updateDocumento(Document document, Session cmisSession,
			File file) {

		Document updated = null;
		try {
			if (document
					.getAllowableActions()
					.getAllowableActions()
					.contains(
							org.apache.chemistry.opencmis.commons.enums.Action.CAN_CHECK_OUT)) {

				document.refresh();

				ObjectId idOfCheckedOutDocument = document.checkOut();
				Document pwc = (Document) cmisSession
						.getObject(idOfCheckedOutDocument);

				ContentStream contentStream;

				contentStream = cmisSession.getObjectFactory()
						.createContentStream(file.getName(), file.length(),
								LoadFiles.FILE_TYPE, new FileInputStream(file));
				ObjectId objectId = pwc.checkIn(false, null, contentStream,
						"Regeneracion del documento");
				updated = (Document) cmisSession.getObject(objectId);
				logger.info("La nueva version es:" + updated.getVersionLabel());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return updated;

	}

}
