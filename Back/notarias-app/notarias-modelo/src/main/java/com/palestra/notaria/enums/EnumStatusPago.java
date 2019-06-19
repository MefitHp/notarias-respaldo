package com.palestra.notaria.enums;

public enum EnumStatusPago {

	PENDIENTE("PENDIENTE"),
	PAGADO("PAGADO"),
	ANTICIPO("ANTICIPO");
	
	
	private final String text;
	
    private EnumStatusPago(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
