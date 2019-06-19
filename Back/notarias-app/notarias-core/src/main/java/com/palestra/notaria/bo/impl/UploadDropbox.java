package com.palestra.notaria.bo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.teamlog.PaperDocTrashedDetails;
import com.palestra.notaria.exceptions.NotariaException;

public class UploadDropbox {
	
	private static final long CHUNKED_UPLOAD_CHUNK_SIZE = 8L << 20; // 8MiB
    private static final int CHUNKED_UPLOAD_MAX_ATTEMPTS = 5;
    private static FileMetadata metadata=null;
    /**
     * Uploads a file in a single request. This approach is preferred for small files since it
     * eliminates unnecessary round-trips to the servers.
     *
     * @param dbxClient Dropbox user authenticated client
     * @param localFIle local file to upload
     * @param dropboxPath Where to upload the file to within Dropbox
     */
     private static void uploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
        try (InputStream in = new FileInputStream(localFile)) {
            metadata = dbxClient.files().uploadBuilder(dropboxPath)
                .withMode(WriteMode.ADD)
                .withClientModified(new Date(localFile.lastModified()))
                .uploadAndFinish(in);
            System.out.println(metadata.toStringMultiline());
        } catch (UploadErrorException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (DbxException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error reading from file \"" + localFile + "\": " + ex.getMessage());
            System.exit(1);
        }
    }
    
	public static String uploadToDropboxAndRetrieveLink(String localPath, String dropboxPath, String pathProperties) throws IOException, NotariaException {

		 // Only display important log messages.
//        Logger.getLogger("").setLevel(Level.WARNING);
		
//		  DESARROLLO
//        String argAuthFile = "/home/yomero/Back/notarias-app/dropbox.properties";
//		  PRODUCCION
//		  String argAuthFile = "/devpal/notaria/.config/dropbox.properties";
//        String localPath = "/home/yomero/Pictures/coche2.png";
//        String dropboxPath = "/cochenuevo.png";

        // Read auth info file.
        DbxAuthInfo authInfo=null;
        try {
            authInfo = DbxAuthInfo.Reader.readFromFile(pathProperties);
        } catch (JsonReader.FileLoadException ex) {
            ex.printStackTrace();            
        }

        String pathError = DbxPathV2.findError(dropboxPath);
        if (pathError != null) {
            throw new NotariaException("El destino de carga del archivo es inválido");
        }

        File localFile = new File(localPath);
        if (!localFile.exists()) {
            throw new NotariaException("El archivo a cargar no existe");
        }

        if (!localFile.isFile()) {
            throw new NotariaException("El origen especificado no es un archivo válido");
        }


        // Create a DbxClientV2, which is what you use to make API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file");
        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, authInfo.getAccessToken(), authInfo.getHost());

        // upload the file with simple upload API if it is small enough, otherwise use chunked
        // upload API for better performance. Arbitrarily chose 2 times our chunk size as the
        // deciding factor. This should really depend on your network.
        if (localFile.length() <= (2 * CHUNKED_UPLOAD_CHUNK_SIZE)) {
            uploadFile(dbxClient, localFile, dropboxPath);
        } 
        
        SharedLinkMetadata sharedLinkMetadata=null;
		try {
			sharedLinkMetadata = dbxClient.sharing().createSharedLinkWithSettings(metadata.getPathDisplay());
		} catch (CreateSharedLinkWithSettingsErrorException e) {
			e.printStackTrace();
			throw new NotariaException("No se pudo obtener el link del archivo cargado");
		} catch (DbxException e) {
			e.printStackTrace();
			throw new NotariaException("No se pudo obtener el link del archivo cargado");
		}
        return sharedLinkMetadata.getUrl();
//        else {
//            chunkedUploadFile(dbxClient, localFile, dropboxPath);
//        }
    }
}
