package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;

public interface ControlFoliosBo extends GenericBo<ControlFolios> {


	ControlFolios getFolios() throws NotariaException;

	ControlFolios getUltimo() throws NotariaException;

}
