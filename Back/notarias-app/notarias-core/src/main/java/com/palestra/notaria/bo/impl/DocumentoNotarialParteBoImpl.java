package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.DocumentoNotarialParcialBo;
import com.palestra.notaria.bo.DocumentoNotarialParteBo;
import com.palestra.notaria.bo.PlantillaDocumentoNotarialBo;
import com.palestra.notaria.dao.DocumentoNotarialParteDao;
import com.palestra.notaria.dao.DocumentoNotarialParteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.DocumentoNotarialParte;
import com.palestra.notaria.modelo.PDNBloqueTexto;
import com.palestra.notaria.modelo.PDNToken;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;

public class DocumentoNotarialParteBoImpl extends GenericBoImpl<DocumentoNotarialParte> implements
		DocumentoNotarialParteBo {

	
	private DocumentoNotarialParcial validaDocumentoNotarialParcial(DocumentoNotarialParcial documento) throws NotariaException {
		DocumentoNotarialParcialBo bo = new DocumentoNotarialParcialBoImpl();
		documento = bo.findById(documento.getIddocnotpar());
		return documento;
	}
	
	@Override
	public List<DocumentoNotarialParte> partes(DocumentoNotarialParcial documento) throws NotariaException {
		//verificamos que el documento exista en la unidad de persistencia:
		try{
			documento = validaDocumentoNotarialParcial(documento);
			DocumentoNotarialParteDao dao = new DocumentoNotarialParteDaoImpl();
			List<DocumentoNotarialParte> partes = dao.findByDocumento(documento);
			return partes;
		}catch(NullPointerException e){
			throw new NotariaException("No se localizo el documento en la unidad de persistencia. Imposible continuar.");
		} 
	}

	@Override
	public List<DocumentoNotarialParte> partesTexto(DocumentoNotarialParcial documento) throws NotariaException{
		List<DocumentoNotarialParte> partes = partes(documento);
		List<DocumentoNotarialParte> partesTexto = new ArrayList<DocumentoNotarialParte>();
		if(partes.size()>0){
			String referenciaCero = partes.get(0).getReferencia();
			boolean iniciaVariable = (referenciaCero.indexOf("${")>0 && referenciaCero.indexOf("}", referenciaCero.indexOf("${"))>0);		
			int xParte = iniciaVariable?1:0;
			for(;xParte<partes.size();xParte++){
				partesTexto.add(partes.get(xParte));
			}
			return partesTexto;
		}else{
			return new ArrayList<DocumentoNotarialParte>();
		}
	}
	
	@Override
	public List<DocumentoNotarialParte> partesVariable(DocumentoNotarialParcial documento) throws NotariaException{
		List<DocumentoNotarialParte> partes = partes(documento);
		List<DocumentoNotarialParte> partesVariable = new ArrayList<DocumentoNotarialParte>();
		if(partes.size()>0){
			String referenciaCero = partes.get(0).getReferencia();
			boolean iniciaVariable = (referenciaCero.indexOf("${")>0 && referenciaCero.indexOf("}", referenciaCero.indexOf("${"))>0);		
			int xParte = iniciaVariable?0:1;
			for(;xParte<partes.size();xParte++){
				partesVariable.add(partes.get(xParte));
			}
			return partesVariable;
		}else{
			return new ArrayList<DocumentoNotarialParte>();
		}
	}
	
	@Override
	public DocumentoNotarialParte orden(DocumentoNotarialParcial documento, int orden) throws NotariaException {
		try{
			documento = validaDocumentoNotarialParcial(documento);
			DocumentoNotarialParteDao dao = new DocumentoNotarialParteDaoImpl();
			DocumentoNotarialParte parte = dao.findByOrden(orden, documento);
			return parte;
		} catch(NullPointerException e){
			throw new NotariaException("No se localizo el documento en la unidad de persistencia. Imposible continuar.");
		}
	}

	@Override
	public DocumentoNotarialParte referencia(DocumentoNotarialParcial documento, String referencia) throws NotariaException {
		try{
			documento = validaDocumentoNotarialParcial(documento);
			DocumentoNotarialParteDao dao = new DocumentoNotarialParteDaoImpl();
			DocumentoNotarialParte parte = dao.findByReferencia(referencia, documento);
			return parte;
		} catch(NullPointerException e){
			throw new NotariaException("No se localizo el documento en la unidad de persistencia. Imposible continuar.");
		}
	}

	@Override
	public List<DocumentoNotarialParte> registroInicial(PlantillaDocumentoNotarial plantilla, DocumentoNotarialParcial documento) throws NotariaException {
		List<DocumentoNotarialParte> partes = new ArrayList<DocumentoNotarialParte>();
		//de la plantilla se obtiene los registros de PDNToken y PDNBloqueTexto
		/*
		PlantillaDocumentoNotarialBo bo = new PlantillaDocumentoNotarialBoImpl();
		
		List<PDNBloqueTexto> textos = bo.listaBloqueTexto(plantilla);
		List<PDNToken> tokens = bo.listaToken(plantilla);
		partes = obtienePartes(textos, tokens);
		DocumentoNotarialParteBo dnpBo = new DocumentoNotarialParteBoImpl();
		for(DocumentoNotarialParte parte:partes){
			parte.setDocumento(documento);
			dnpBo.guarda(parte);
		}
		*/
		return partes;		
		
	}
@Override
public List<DocumentoNotarialParte> obtienePartes(List<PDNBloqueTexto> textos, List<PDNToken> tokens) throws NotariaException {
	List<DocumentoNotarialParte> partes = new ArrayList<DocumentoNotarialParte>();
	if(textos.size()+tokens.size()>0){	
		boolean iniciaTexto = (textos.size()>tokens.size());
		boolean iniciaVariable = (textos.size()<tokens.size());
		if(!iniciaTexto && !iniciaVariable){
			iniciaTexto = (textos.get(0).getPosicion()==0);
			iniciaVariable = (tokens.get(0).getPosicion()==0);
		}
		int parteActual = 0;
		if(iniciaTexto){
			while(parteActual<tokens.size()){
				DocumentoNotarialParte parte = generaParte(textos.get(parteActual));
				//parte.setOrden(parteActual);
				partes.add(parte);
				parte = generaParte(tokens.get(parteActual));
				//parte.setOrden(partes.size());
				partes.add(parte);			
				parteActual++;
			}
			if(parteActual<textos.size()){
				DocumentoNotarialParte parte = generaParte(textos.get(parteActual));
				//parte.setOrden(partes.size());
				partes.add(parte);
			}
		} else if(iniciaVariable) {
			while(parteActual<textos.size()){
				DocumentoNotarialParte parte = generaParte(tokens.get(parteActual));
				//parte.setOrden(parteActual);
				partes.add(parte);
				parte = generaParte(textos.get(parteActual));
				//parte.setOrden(partes.size());
				partes.add(parte);			
				parteActual++;
			}				
			if(parteActual<tokens.size()){
				DocumentoNotarialParte parte = generaParte(tokens.get(parteActual));
				//parte.setOrden(partes.size());
				partes.add(parte);
			}			
		} else {
			throw new NotariaException("Hay incongruencia en los bloques que componen la plantilla, no se logro determinar el inicio. Imposible registrar los bloques.");
		}
		return partes;
	}else{
		throw new NotariaException("No se ha localizado partes del documento notarial, al parecer no se identifico contenido valido.");
	}
}

	private DocumentoNotarialParte generaParte(PDNToken token){
		DocumentoNotarialParte parte = new DocumentoNotarialParte();
		parte.setReferencia(token.getNombreVariable());
		parte.setValor("");
		parte.setOrden(token.getPosicion());
		return parte;
	}
	
	private DocumentoNotarialParte generaParte(PDNBloqueTexto btexto){
		DocumentoNotarialParte parte = new DocumentoNotarialParte();
		parte.setReferencia(String.valueOf(btexto.getPosicion()));
		parte.setValor(btexto.getBloqueTexto());
		parte.setOrden(btexto.getPosicion());
		return parte;
	}
	
	@Override
	public DocumentoNotarialParte guarda(DocumentoNotarialParte parte)
			throws NotariaException {
		DocumentoNotarialParteDao dao = new DocumentoNotarialParteDaoImpl();
		return dao.save(parte);
	}

	@Override
	public List<DocumentoNotarialParte> parteConColindancias(
			DocumentoNotarialParcial documento, int orden)
			throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentoNotarialParte> parteConColindancias(
			DocumentoNotarialParcial documento, String referencia)
			throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoNotarialParte actualiza(DocumentoNotarialParte documento) throws NotariaException {
		// validar los cambios de partes antes de actualizar el valor.
		DocumentoNotarialParteDao dao = new DocumentoNotarialParteDaoImpl();
		return dao.update(documento);		
	}

}
