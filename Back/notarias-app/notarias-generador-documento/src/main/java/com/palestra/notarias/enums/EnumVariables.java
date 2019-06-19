package com.palestra.notarias.enums;

/**
 * Enum con los prefijos de variables
 * @author sofia
 *
 */
public enum EnumVariables {
	
	VAR_PREFIX("${"),
    VAR_SUFIX("}"),
    VAR_FRM_SUFIX("]}"),
	VAR_IDENTIFIER("var:"),
	GPO_IDENTIFIER("gpo:"),
	COMPARECIENTE_IDENTIFIER("cte:"),
	COMPARECIENTES_IDENTIFIER("ctes:"),
	FRM_IDENTIFIER("frm:"),
	TBL_PREFIX("${frm."),
	TBL_SUFIX("]}"),
	TBL_IDENTIFIER("tbl:"),
	CSV_IDENTIFIER("csv:"),
	BRACKET_OPEN("["),
	BRACKET_CLOSE("]");
	
	private final String text;
	
    private EnumVariables(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
