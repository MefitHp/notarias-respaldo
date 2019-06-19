package com.palestra.notaria.bo;
import java.util.List;

import com.palestra.notaria.dato.DatoAvisoDecena;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.Escritura;

public interface AvisoDecenaFoliosBo extends GenericBo<AvisoDecena> {
	
	List<Escritura> obtenerNoPaso(Long numeroLibro) throws NotariaException;
	List<Escritura> obtenerPasadas(Long numeroLibro) throws NotariaException;
	AvisoDecena obtenerXnumeroLibro(Long numeroLibro) throws NotariaException;
	void cierraDecena(Long numeroLibro) throws NotariaException;
	void abreDecena() throws NotariaException;
	List<DatoAvisoDecena> getAll() throws NotariaException;
	
}
