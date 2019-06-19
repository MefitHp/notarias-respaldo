package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.EscrituraExterna;

public interface EscrituraExternaBo extends GenericBo<EscrituraExterna> {

	public EscrituraExterna guardarEscrituraExterna(EscrituraExterna escritura) throws NotariaException;

}
