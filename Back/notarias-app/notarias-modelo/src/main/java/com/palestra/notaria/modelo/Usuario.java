package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbcfgm03")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idusuario;
	
	@Transient
	private String acceso;

	@Transient
	private String idsesionactual;

	private String cdusuario;

	private String dscorreo;

	private String dsmaterno;

	private String dsnombre;

	private String dspaterno;

	private String dsreferencia;

	private String dsrfc;

	private Timestamp dsultimoacceso;

	private String dsvalenc;

	private String idsesion;
	
	private String dsiniciales;
	
	private String dsnumabogado;
	
	private String dscurp;
	
//	private String dshuelladigital;

	@Column(columnDefinition="INT")
	private Boolean inestatus;

	@Column(columnDefinition="INT")
	private Boolean isactivo;

	@Column(columnDefinition="INT")
	private Boolean isactualizapwd;
	
	@Column(columnDefinition="TIMESTAMP")
	private String fchinicio;
	
	@Column(columnDefinition="TIMESTAMP")
	private String fchfin;

	private Timestamp tmstmp;

//	//bi-directional many-to-one association to TareaPendiente
//	@OneToMany(mappedBy="tbcfgm031")
//	private List<TareaPendiente> tbbsnm02s1;
//
//	//bi-directional many-to-one association to TareaPendiente
//	@OneToMany(mappedBy="tbcfgm032")
//	private List<TareaPendiente> tbbsnm02s2;
//
//	//bi-directional many-to-one association to BitacoraFirma
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<BitacoraFirma> tbbsnm03s;
//
//	//bi-directional many-to-one association to TareaAtendida
//	@OneToMany(mappedBy="tbcfgm031")
//	private List<TareaAtendida> tbbsnm06s1;
//
//	//bi-directional many-to-one association to TareaAtendida
//	@OneToMany(mappedBy="tbcfgm032")
//	private List<TareaAtendida> tbbsnm06s2;
//
//	//bi-directional many-to-one association to TarjetaAmarilla
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<TarjetaAmarilla> tbbsnm10s;
//
//	//bi-directional many-to-one association to BitacoraExpediente
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<BitacoraExpediente> tbbsnm20s;
//
//	//bi-directional many-to-one association to Escritura
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<Escritura> tbbsnm24s;
//
//	//bi-directional many-to-one association to Guardia
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<Guardia> tbbsnm25s;
//
//	//bi-directional many-to-one association to Testimonio
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<Testimonio> tbbsnm30s;
//
//	//bi-directional many-to-one association to Expediente
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<Expediente> tbbsnm32s;
//
//	//bi-directional many-to-one association to ActoDocumento
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<ActoDocumento> tbbsnm37s;
//
//	//bi-directional many-to-one association to Tramite
//	@OneToMany(mappedBy="tbcfgm03")
//	private List<Tramite> tbbsnm40s;


	//bi-directional many-to-one association to Rol
	
//	public String getDshuelladigital() {
//		return dshuelladigital;
//	}
//	public void setDshuelladigital(String dshuelladigital) {
//		this.dshuelladigital = dshuelladigital;
//	}
	
//	private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
	
	@ManyToOne
	@JoinColumn(name="idrol")
	private Rol rol;

    public Usuario() {
//    	templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    }

//    public DPFPTemplate getTemplate(DPFPFingerIndex finger) {
//        return templates.get(finger);
//    }
//
//    public void setTemplate(DPFPFingerIndex finger, DPFPTemplate template) {
//        templates.put(finger, template);
//    }
	public String getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getCdusuario() {
		return this.cdusuario;
	}

	public void setCdusuario(String cdusuario) {
		this.cdusuario = cdusuario;
	}

	public String getDscorreo() {
		return this.dscorreo;
	}

	public void setDscorreo(String dscorreo) {
		this.dscorreo = dscorreo;
	}

	public String getDsmaterno() {
		return this.dsmaterno;
	}

	public void setDsmaterno(String dsmaterno) {
		this.dsmaterno = dsmaterno;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDspaterno() {
		return this.dspaterno;
	}

	public void setDspaterno(String dspaterno) {
		this.dspaterno = dspaterno;
	}

	public String getDsreferencia() {
		return this.dsreferencia;
	}

	public void setDsreferencia(String dsreferencia) {
		this.dsreferencia = dsreferencia;
	}

	public String getDsrfc() {
		return this.dsrfc;
	}

	public void setDsrfc(String dsrfc) {
		this.dsrfc = dsrfc;
	}

	public Timestamp getDsultimoacceso() {
		return this.dsultimoacceso;
	}

	public void setDsultimoacceso(Timestamp timestamp) {
		this.dsultimoacceso = timestamp;
	}

	public String getDsvalenc() {
		return this.dsvalenc;
	}

	public void setDsvalenc(String dsvalenc) {
		this.dsvalenc = dsvalenc;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

//	public List<TareaPendiente> getTbbsnm02s1() {
//		return this.tbbsnm02s1;
//	}
//
//	public void setTbbsnm02s1(List<TareaPendiente> tbbsnm02s1) {
//		this.tbbsnm02s1 = tbbsnm02s1;
//	}
//	
//	public List<TareaPendiente> getTbbsnm02s2() {
//		return this.tbbsnm02s2;
//	}
//
//	public void setTbbsnm02s2(List<TareaPendiente> tbbsnm02s2) {
//		this.tbbsnm02s2 = tbbsnm02s2;
//	}
//	
//	public List<BitacoraFirma> getTbbsnm03s() {
//		return this.tbbsnm03s;
//	}
//
//	public void setTbbsnm03s(List<BitacoraFirma> tbbsnm03s) {
//		this.tbbsnm03s = tbbsnm03s;
//	}
//	
//	public List<TareaAtendida> getTbbsnm06s1() {
//		return this.tbbsnm06s1;
//	}
//
//	public void setTbbsnm06s1(List<TareaAtendida> tbbsnm06s1) {
//		this.tbbsnm06s1 = tbbsnm06s1;
//	}
//	
//	public List<TareaAtendida> getTbbsnm06s2() {
//		return this.tbbsnm06s2;
//	}
//
//	public void setTbbsnm06s2(List<TareaAtendida> tbbsnm06s2) {
//		this.tbbsnm06s2 = tbbsnm06s2;
//	}
//	
//	public List<TarjetaAmarilla> getTbbsnm10s() {
//		return this.tbbsnm10s;
//	}
//
//	public void setTbbsnm10s(List<TarjetaAmarilla> tbbsnm10s) {
//		this.tbbsnm10s = tbbsnm10s;
//	}
//	
//	public List<BitacoraExpediente> getTbbsnm20s() {
//		return this.tbbsnm20s;
//	}
//
//	public void setTbbsnm20s(List<BitacoraExpediente> tbbsnm20s) {
//		this.tbbsnm20s = tbbsnm20s;
//	}
//	
//	public List<Escritura> getTbbsnm24s() {
//		return this.tbbsnm24s;
//	}
//
//	public void setTbbsnm24s(List<Escritura> tbbsnm24s) {
//		this.tbbsnm24s = tbbsnm24s;
//	}
//	
//	public List<Guardia> getTbbsnm25s() {
//		return this.tbbsnm25s;
//	}
//
//	public void setTbbsnm25s(List<Guardia> tbbsnm25s) {
//		this.tbbsnm25s = tbbsnm25s;
//	}
//	
//	public List<Testimonio> getTbbsnm30s() {
//		return this.tbbsnm30s;
//	}
//
//	public void setTbbsnm30s(List<Testimonio> tbbsnm30s) {
//		this.tbbsnm30s = tbbsnm30s;
//	}
//	
//	public List<Expediente> getTbbsnm32s() {
//		return this.tbbsnm32s;
//	}
//
//	public void setTbbsnm32s(List<Expediente> tbbsnm32s) {
//		this.tbbsnm32s = tbbsnm32s;
//	}
//	
//	public List<ActoDocumento> getTbbsnm37s() {
//		return this.tbbsnm37s;
//	}
//
//	public void setTbbsnm37s(List<ActoDocumento> tbbsnm37s) {
//		this.tbbsnm37s = tbbsnm37s;
//	}
//	
//	public List<Tramite> getTbbsnm40s() {
//		return this.tbbsnm40s;
//	}
//
//	public void setTbbsnm40s(List<Tramite> tbbsnm40s) {
//		this.tbbsnm40s = tbbsnm40s;
//	}
	

	public Boolean getInestatus() {
		return inestatus;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public void setInestatus(Boolean inestatus) {
		this.inestatus = inestatus;
	}

	public Boolean getIsactivo() {
		return isactivo;
	}

	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}

	public Boolean getIsactualizapwd() {
		return isactualizapwd;
	}

	public void setIsactualizapwd(Boolean isactualizapwd) {
		this.isactualizapwd = isactualizapwd;
	}
	
	public String getAcceso() {
		return acceso;
	}
	
	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}
	
	public String getIdsesionactual() {
		return idsesionactual;
	}
	
	public void setIdsesionactual(String idsesionactual) {
		this.idsesionactual = idsesionactual;
	}

	public String getDsiniciales() {
		return dsiniciales;
	}

	public void setDsiniciales(String dsiniciales) {
		this.dsiniciales = dsiniciales;
	}

	public String getFchinicio() {
		return fchinicio;
	}

	public void setFchinicio(String fchinicio) {
		this.fchinicio = fchinicio;
	}

	public String getFchfin() {
		return fchfin;
	}

	public void setFchfin(String fchfin) {
		this.fchfin = fchfin;
	}

	public String getDscurp() {
		return dscurp;
	}
	
	public void setDscurp(String dscurp) {
		this.dscurp = dscurp;
	}

	public String getDsnumabogado() {
		return dsnumabogado;
	}

	public void setDsnumabogado(String dsnumabogado) {
		this.dsnumabogado = dsnumabogado;
	}
}