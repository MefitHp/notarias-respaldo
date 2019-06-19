package com.palestra.notaria.envio;

import java.util.List;
import java.io.Serializable;

import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.modelo.EnumEstatus;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.MesaCtrl;;

public class MesaCtlEnvio extends GenericEnvio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3801512978508008497L;
	
	private List<MesaCtrl> documentos;
	
	private EnumStatusDoc[] estatusDoc;

	private MesaCtrl documento;
	
	private Boolean ispagorequire;
	
	private Escritura escritura;
	
	private Boolean cancelanopaso;
	

	public List<MesaCtrl> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<MesaCtrl> documentos) {
		this.documentos = documentos;
	}

	public MesaCtrl getDocumento() {
		return documento;
	}

	public void setDocumento(MesaCtrl documento) {
		this.documento = documento;
	}

	public EnumStatusDoc[] getEstatusDoc() {
		return estatusDoc;
	}

	public void setEstatusDoc(EnumStatusDoc[] estatusdocs) {
		this.estatusDoc = estatusdocs;
	}

	public Boolean getIspagorequire() {
		return ispagorequire;
	}

	public void setIspagorequire(Boolean ispagorequire) {
		this.ispagorequire = ispagorequire;
	}

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

	public Boolean getCancelanopaso() {
		return cancelanopaso;
	}

	public void setCancelanopaso(Boolean cancelanopaso) {
		this.cancelanopaso = cancelanopaso;
	}

	
	
	

}
