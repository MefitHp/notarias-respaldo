package com.palestra.notaria.enums;

	public enum EnumStatusDoc {

		SOLICITADO("SOLICITADO"),
		FIRMA("FIRMA"),
		ENTREGADO("ENTREGADO"),
		GESTOR("GESTOR"),
		DEVUELTO("DEVUELTO"),
		NO_PASO("NO_PASO"),
		CANCELA_NO_PASO("CANCELA_NOPASO");
		
		private final String text;
		
	    private EnumStatusDoc(final String text) {
	        this.text = text;
	    }
	    
	    @Override
	    public String toString() {
	        return text;
	    }
	}

