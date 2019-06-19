package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;

public class PlantillaDocNotarialEnvio extends GenericEnvio {

	PlantillaDocumentoNotarial plantilla=null;
	
	ArrayList<PlantillaDocumentoNotarial> publicadosList=null;
	ArrayList<PlantillaDocumentoNotarial> noPublicadosList=null;
	
	public ArrayList<PlantillaDocumentoNotarial> getNoPublicadosList() {
		return noPublicadosList;
	}
	
	public void setNoPublicadosList(
			ArrayList<PlantillaDocumentoNotarial> noPublicadosList) {
		this.noPublicadosList = noPublicadosList;
	}

	public PlantillaDocumentoNotarial getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(PlantillaDocumentoNotarial plantilla) {
		this.plantilla = plantilla;
	}

	public ArrayList<PlantillaDocumentoNotarial> getPublicadosList() {
		return publicadosList;
	}
	
	public void setPublicadosList(
			ArrayList<PlantillaDocumentoNotarial> publicadosList) {
		this.publicadosList = publicadosList;
	}
	
}
