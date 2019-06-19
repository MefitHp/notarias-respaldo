package com.palestra.notaria.bo;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.DocumentoNotarialParte;
import com.palestra.notaria.modelo.PDNBloqueTexto;
import com.palestra.notaria.modelo.PDNToken;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;


public interface DocumentoNotarialParteBo extends GenericBo<DocumentoNotarialParte> {
	
	List<DocumentoNotarialParte> partes(DocumentoNotarialParcial documento) throws NotariaException;
	DocumentoNotarialParte orden(DocumentoNotarialParcial documento, int orden) throws NotariaException;
	DocumentoNotarialParte referencia(DocumentoNotarialParcial documento, String referencia) throws NotariaException;
	List<DocumentoNotarialParte> registroInicial(PlantillaDocumentoNotarial plantilla, DocumentoNotarialParcial documento) throws NotariaException;
	DocumentoNotarialParte guarda(DocumentoNotarialParte parte) throws NotariaException;
	List<DocumentoNotarialParte> parteConColindancias(DocumentoNotarialParcial documento, int orden) throws NotariaException;
	List<DocumentoNotarialParte> parteConColindancias(DocumentoNotarialParcial documento, String referencia) throws NotariaException;
	List<DocumentoNotarialParte> obtienePartes(List<PDNBloqueTexto> textos,
			List<PDNToken> tokens) throws NotariaException;
	List<DocumentoNotarialParte> partesTexto(DocumentoNotarialParcial documento)
			throws NotariaException;
	List<DocumentoNotarialParte> partesVariable(
			DocumentoNotarialParcial documento) throws NotariaException;
	DocumentoNotarialParte actualiza(DocumentoNotarialParte documento)
			throws NotariaException;
}
