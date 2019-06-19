package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The persistent class for the tbcfgm90 database table.
 * 
 */
@Entity
@Table(name="tbcfgm90")
@XmlRootElement(name="Catalogo")
public class Catalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcatalogo;

	private String dsnombre;

	private String idsesion;

	@XmlTransient
	private Timestamp tmstmp;

//	//bi-directional many-to-one association to ElementoCatalogo
//	@OneToMany(mappedBy="tbcfgm90")
//	private List<ElementoCatalogo> tbcfgm91s;

    public Catalogo() {
    }
    
    @XmlElement(name="idcatalogo")
	public String getIdcatalogo() {
		return this.idcatalogo;
	}

	public void setIdcatalogo(String idcatalogo) {
		this.idcatalogo = idcatalogo;
	}

	@XmlElement(name="dsnombre")
	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	@XmlElement(name="idsesion")
	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	@XmlElement(name="tmstmp")
	@XmlJavaTypeAdapter(value=TimestampAdapter.class)
	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

//	public List<ElementoCatalogo> getTbcfgm91s() {
//		return this.tbcfgm91s;
//	}
//
//	public void setTbcfgm91s(List<ElementoCatalogo> tbcfgm91s) {
//		this.tbcfgm91s = tbcfgm91s;
//	}
	
}