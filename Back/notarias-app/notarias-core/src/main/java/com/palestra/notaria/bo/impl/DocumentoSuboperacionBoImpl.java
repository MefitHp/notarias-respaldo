package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.DocumentoSuboperacionBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.SuboperacionBo;
import com.palestra.notaria.dao.DocumentoSuboperacionDao;
import com.palestra.notaria.dao.impl.DocumentoSuboperacionDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;

public class DocumentoSuboperacionBoImpl extends
		GenericBoImpl<DocumentoSuboperacion> implements
		DocumentoSuboperacionBo {

	private DocumentoSuboperacionDao docDao;
	
	public DocumentoSuboperacionBoImpl(){
		docDao = new DocumentoSuboperacionDaoImpl();
		super.dao = this.docDao;
	}
	
	@Override
	public List<DocumentoSuboperacion> listarPreviosPorSubopAndLocalidad(String idlocalidad,String idsuboperacion) throws NotariaException{
		return this.docDao.listarPreviosPorSubopAndLocalidad(idlocalidad, idsuboperacion);
	}
	
	@Override
	public List<DocumentoSuboperacion> listarPosterioresPorSubopAndLocalidad(String idlocalidad,String idsuboperacion) throws NotariaException{
		return this.docDao.listarPosterioresPorSubopAndLocalidad(idlocalidad, idsuboperacion);
	}

	@Override
	public DocumentoSuboperacion encuentraDocumentoSubOperacion(
			String idsuboperacion, String idlocalidad, String iddocumento,
			String idformato) throws NotariaException {
		return this.docDao.getUnique(idsuboperacion, idlocalidad, iddocumento, idformato);
	}
	
	@Override
	public boolean delete(DocumentoSuboperacion documentoSuboperacion) {
		try{
			return docDao.delete(documentoSuboperacion.getIdentificador());
		}catch(NotariaException e){
			e.printStackTrace(System.out);
			// se ha tenido que hacer así porque el Generic no maneja la excepción y habría que cambiarlo.
			return false;
		}
	}

	@Override
	public List<DocumentoSuboperacion> listarPosterioresPorEscritura(String idescritura,String idlocalidad) throws NotariaException {
		// TODO Auto-generated method stub
		
		List<DocumentoSuboperacion> docsub = new ArrayList<DocumentoSuboperacion>();
		List<EscrituraActo> listaescActo = getEsctrituraActos(idescritura);
		for(EscrituraActo esact:listaescActo){
			ActoBo actoBo = new ActoBoImpl();
			Acto acto = actoBo.buscarPorIdCompleto(esact.getActo().getIdacto());
			List<DocumentoSuboperacion> tmpdocsub = this.listarPosterioresPorSubopAndLocalidad(idlocalidad,acto.getSuboperacion().getIdsuboperacion());
			if(tmpdocsub.size()>0){
				docsub.addAll(tmpdocsub);
			}
		}
		
		return docsub;
	}
	
	
	
	public List<EscrituraActo> getEsctrituraActos(String idescritura) throws NotariaException {
		EscrituraActoBo escActoBo = new EscrituraActoBoImpl();
		EscrituraActo escacto = new EscrituraActo();
		Escritura esc = new Escritura();
		esc.setIdescritura(idescritura);
		escacto.setEscritura(esc);
		return escActoBo.buscarPorEscrituraIdCompleto(escacto);
	}
	
}
