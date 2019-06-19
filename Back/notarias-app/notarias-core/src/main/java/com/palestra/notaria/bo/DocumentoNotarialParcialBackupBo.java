package com.palestra.notaria.bo;

import com.palestra.notaria.modelo.DocumentoNotarialParcialBackup;
import com.palestra.notaria.modelo.Escritura;

public interface DocumentoNotarialParcialBackupBo extends
		GenericBo<DocumentoNotarialParcialBackup> {

	public boolean makeBackupDocNotParciales(Escritura escritura);
}
