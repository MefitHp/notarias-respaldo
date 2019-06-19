package com.palestra.notaria.enums;

/**
 * Estatus del estado de un calendario cita.
 * @author sofia
 *
 */
public enum EnumCitaEstatus {
	
	PENDIENTE("PENDIENTE"),
	CANCELADA("CANCELADA"),
	FINALIZADA("FINALIZADA"),
	REPROGRAMADA("REPROGRAMADA");
    
	private final String text;
	
    private EnumCitaEstatus(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
