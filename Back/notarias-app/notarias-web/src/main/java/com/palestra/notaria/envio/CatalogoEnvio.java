package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;

public class CatalogoEnvio extends GenericEnvio {
	
	ElementoCatalogo elementoCatalogo = null;
	
	Catalogo catalogo=null;
	
	ArrayList<Catalogo> tipoCatList =null;
	
	public ArrayList<Catalogo> getTipoCatList() {
		return tipoCatList;
	}
	
	public void setTipoCatList(ArrayList<Catalogo> tipoCatList) {
		this.tipoCatList = tipoCatList;
	}
	
	public Catalogo getCatalogo() {
		return catalogo;
	}
	
	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	ArrayList<ElementoCatalogo> catalogoList = null;
	
	public ArrayList<ElementoCatalogo> getCatalogoList() {
		return catalogoList;
	}
	
	public void setCatalogoList(ArrayList<ElementoCatalogo> catalogoList) {
		this.catalogoList = catalogoList;
	}
	
	public ElementoCatalogo getElementoCatalogo() {
		return elementoCatalogo;
	}
	
	public void setElementoCatalogo(ElementoCatalogo elementoCatalogo) {
		this.elementoCatalogo = elementoCatalogo;
	}

}
