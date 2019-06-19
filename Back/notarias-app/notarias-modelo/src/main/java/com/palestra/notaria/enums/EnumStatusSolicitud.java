package com.palestra.notaria.enums;

public enum EnumStatusSolicitud {

	OK("OK"),
	WARNING("WARNING"),
	DEAD("DEAD"),
	FINISHED("FINISHED");
	
	private final String text;
	
    private EnumStatusSolicitud(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

