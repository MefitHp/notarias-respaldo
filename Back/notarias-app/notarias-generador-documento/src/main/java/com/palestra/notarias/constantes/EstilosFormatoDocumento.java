package com.palestra.notarias.constantes;

/**
 * Contiene los estilos y etiquetas html para que al convertir el documento
 * .html a .doc, el documento notarial tenga los formatos adecuados desde
 * margenes, tamaño de hoja, tipo letra, etc.
 * 
 * @author sofia
 * 
 */
public class EstilosFormatoDocumento {

	//TODO: darle mejor estructura
	/** Se define los margenes, el tamaño del papel y el numero de la pagina **/
	public static final String HEADER = "<html><head><title></title><style>@page { size:21.59cm 33.02cm;margin: 7.0cm 3.00cm 4.0cm 3.00cm;line-height: 0cm;}</style></head>"
			+ "<body>";
//			+ "<DIV TYPE=HEADER style='margin: 0cm; padding: 0cm;line-height: 0%;'>"
//			+ "    <p style='text-align: right;'><span style='font-size:10pt'><span style='font-family:courier'>"  // ,courier,monospace
//			+ "- <SDFIELD TYPE=PAGE SUBTYPE=RANDOM FORMAT=PAGE></SDFIELD> -</span></span></p>"
//			+ "</DIV>";
			/** Div que engloba el contenido del contenido, para darle tipo de letra, tamaño, etc**/
			public static final String BODY_FORMAT_BEGIN = "<div style='text-align:justify;margin: 0cm; padding: 0cm;line-height: 150%;font-size:10pt;font-family:courier'>"; // new,courier,monospace
			public static final String BODY_FORMAT_END = "<p>&nbsp;</p><br></div></body>";
			/** Definir el footer **/
			public static final String FOOTER = ""
//			+ "<DIV TYPE=FOOTER>"
//			+ "<p style='text-align:right;margin: 0cm; padding: 0cm;line-height: 0%;'>"
//			+ "<span style='font-size:10pt'><span style='font-family:courier'></span></span></p>"  //  new,courier,monospace
//			+ "</DIV>"
			+ "</body></html>";

}
