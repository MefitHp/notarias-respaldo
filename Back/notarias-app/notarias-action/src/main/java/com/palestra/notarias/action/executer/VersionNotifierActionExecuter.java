package com.palestra.notarias.action.executer;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.web.app.servlet.DownloadContentServlet;
import org.apache.log4j.Logger;

import com.palestra.notarias.action.service.DataService;
import com.palestra.notarias.action.util.DocumentoUtils;

public class VersionNotifierActionExecuter extends ActionExecuterAbstractBase {
	static Logger logger = Logger.getLogger(VersionNotifierActionExecuter.class);
	public static final String NAME = "version-notifier";
	private NodeService nodeService;
	private DataService notariaDataService;
	private VersionService versionService;
	private AuthenticationService authenticationService;
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setDataService(DataService dataService) {
		this.notariaDataService = dataService;
	}
	
	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}
	
	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void executeImpl(Action ruleAction, NodeRef sourceNode) {
		logger.info("Executo la accion ");
		
		if (!this.nodeService.exists(sourceNode)) {
			return;
		}
		String fileName = (String) this.nodeService.getProperty(sourceNode,
				ContentModel.PROP_NAME);
		
		Version currentVersion = this.versionService.getCurrentVersion(sourceNode);
		String versionURL = DownloadContentServlet.generateBrowserURL(sourceNode,fileName);
		String nodeId = sourceNode.getId(); 
		String numEscritura = DocumentoUtils.getEscrituraId(fileName);		
		String idEscritura = notariaDataService.getIdByEscrituraNumero(numEscritura);
		if(idEscritura==null){
			logger.info("No se pudo obtener el numero de escritura");
			return;
		}
		logger.info("Archivo actualizado: " + fileName  + " version: " + currentVersion.getVersionLabel() + " escritura: " + idEscritura);
		//TODO: hacer el match entre usuario de alfresco y la aplicacion de notarias
		logger.info("Current user: " + authenticationService.getCurrentUserName());
		
		notariaDataService.saveDocumentVersion(idEscritura, versionURL, fileName, currentVersion.getVersionLabel(), nodeId);

	}

	protected void addParameterDefinitions(List<ParameterDefinition> arg0) {
	}
	
}