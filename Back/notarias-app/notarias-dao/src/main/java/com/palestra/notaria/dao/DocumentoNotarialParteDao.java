package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.DocumentoNotarialParte;

public interface DocumentoNotarialParteDao extends GenericDao<DocumentoNotarialParte, Integer> {
	
	DocumentoNotarialParte findByOrden(int orden, DocumentoNotarialParcial documento) throws NotariaException;
	DocumentoNotarialParte findByReferencia(String referencia, DocumentoNotarialParcial documento) throws NotariaException;
	List<DocumentoNotarialParte> findByDocumento(DocumentoNotarialParcial documento) throws NotariaException;
	
}
