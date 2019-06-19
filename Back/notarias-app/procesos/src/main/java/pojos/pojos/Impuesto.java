package pojos.pojos;

import java.io.Serializable;

public class Impuesto extends ProcesoComun implements Serializable{
	
	private static final long serialVersionUID = 930527418664065240L;
	private String idactodoc;
	private String idacto;
	private String idmesacontrol;
	private String documentotipo;
	private String actonombre;
	private Boolean ispagoRequire;
	
	public Impuesto() {
		// TODO Auto-generated constructor stub
	}

	public String getIdactoDoc() {
		return idactodoc;
	}

	public void setIdactoDoc(String idactodoc) {
		this.idactodoc = idactodoc;
	}

	public String getIdacto() {
		return idacto;
	}

	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}

	public String getDocumentotipo() {
		return documentotipo;
	}

	public void setDocumentotipo(String documentotipo) {
		this.documentotipo = documentotipo;
	}

	public String getActonombre() {
		return actonombre;
	}

	public void setActonombre(String actonombre) {
		this.actonombre = actonombre;
	}

	public Boolean getIspagoRequire() {
		return ispagoRequire;
	}

	public void setIspagoRequire(Boolean ispagoRequire) {
		this.ispagoRequire = ispagoRequire;
	}

	public String getIdmesacontrol() {
		return idmesacontrol;
	}

	public void setIdmesacontrol(String idmesacontrol) {
		this.idmesacontrol = idmesacontrol;
	}

	public String getIdactodoc() {
		return idactodoc;
	}

	public void setIdactodoc(String idactodoc) {
		this.idactodoc = idactodoc;
	}

}
