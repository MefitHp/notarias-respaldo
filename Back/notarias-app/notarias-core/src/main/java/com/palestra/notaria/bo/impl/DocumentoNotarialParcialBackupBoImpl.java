package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoNotarialParcialBackupBo;
import com.palestra.notaria.bo.DocumentoNotarialParcialBo;
import com.palestra.notaria.dao.DocumentoNotarialParcialBackupDao;
import com.palestra.notaria.dao.impl.DocumentoNotarialParcialBackupDaoImpl;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.DocumentoNotarialParcialBackup;
import com.palestra.notaria.modelo.Escritura;

public class DocumentoNotarialParcialBackupBoImpl extends
		GenericBoImpl<DocumentoNotarialParcialBackup> implements
		DocumentoNotarialParcialBackupBo {
	
	private DocumentoNotarialParcialBackupDao docBackupDao;
	
	private DocumentoNotarialParcialBo docNotarialParcialBo;
	
	public DocumentoNotarialParcialBackupBoImpl(){
		this.docBackupDao = new DocumentoNotarialParcialBackupDaoImpl();
		super.dao = this.docBackupDao;
		this.docNotarialParcialBo = new DocumentoNotarialParcialBoImpl();
	}
	
	@Override
	public boolean makeBackupDocNotParciales(Escritura escritura) {
		try{
			List<DocumentoNotarialParcial> docNotParcialList = getDocNotarialParcialBo().getByEscritura(escritura);
			for(int i=0;i<docNotParcialList.size();i++){
				DocumentoNotarialParcialBackup docBackup = new DocumentoNotarialParcialBackup();
				docBackup.setIdescritura(docNotParcialList.get(i).getEscritura().getIdescritura());
	//			TODO: PROBAR COMO SE COMPORTA CON UN CAST DE Object a java.sql.Date
				docBackup.setFecha((java.sql.Date)docNotParcialList.get(i).getFecha().clone());
				docBackup.setIddocnotpar(docNotParcialList.get(i).getIddocnotpar());
				docBackup.setIdsesion(docNotParcialList.get(i).getIdsesion());
				docBackup.setIscerrado(docNotParcialList.get(i).getIscerrado());
				docBackup.setTmstmp(docNotParcialList.get(i).getTmstmp());
				docBackup.setTxtdoc(docNotParcialList.get(i).getTxtdoc());
				docBackup.setVersion(docNotParcialList.get(i).getVersion());
				getDocBackupDao().save(docBackup);
				getDocNotarialParcialBo().delete(docNotParcialList.get(i));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public DocumentoNotarialParcialBo getDocNotarialParcialBo() {
		return docNotarialParcialBo;
	}
	
	public DocumentoNotarialParcialBackupDao getDocBackupDao() {
		return docBackupDao;
	}
}
