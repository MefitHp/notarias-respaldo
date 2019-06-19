package com.palestra.notarias.cmis;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

public interface BaseLoadFile {

	public Session getCmisSession() throws Exception;
	
	public Folder getParentFolder(Session cmisSession) throws Exception;
	
	public String getObjectTypeId();
	
}
