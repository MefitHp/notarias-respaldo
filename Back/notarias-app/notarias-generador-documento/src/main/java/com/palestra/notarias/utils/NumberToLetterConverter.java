package com.palestra.notarias.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.palestra.notaria.exceptions.NotariaException;



/**
 * Esta clase provee la funcionalidad de convertir un numero representado en
 * digitos a una representacion en letras. Mejorado para leer centavos, fechas y numeros en 
 * general
 * 
 * Version inicial
 * de:http://es.axiacore.com/blog/2008/09/convertir-numeros-a-letras-en-java/
 * 
 * @author Camilo Nova
 */
public abstract class NumberToLetterConverter {

	static Logger logger = Logger.getLogger(NumberToLetterConverter.class);

	private static final String[][] ORDINARIOS_UNIDADES = {{"1","primero"},{"2","segundo"},{"3","tercero"},{"4","cuarto"},{"5","quinto"},
	{"6","sexto"},{"7","séptimo"},{"8","octavo"},{"9","noveno"}};
	
	private static final String[][] ORDINARIOS_DECIMOS = {{"1","décimo"},{"2","vigésimo"},{"3","trigésimo"},{"4","cuadragésimo"},{"5","quincuagésimo"},
	{"6","sexagésimo"},{"7","septuagésimo"},{"8","octogésimo"},{"9","nonagésimo"}};
	
	private static final String[][] ORDINARIOS_ESPECIALES = {{"11","undécimo"},{"12","duodécimo"}};
	
	private static final String[] UNIDADES = { "", "un ", "dos ", "tres ",
			"cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve ", "diez ",
			"once ", "doce ", "trece ", "catorce ", "quince ", "dieciséis ",
			"diecisiete ", "dieciocho ", "diecinueve ", "veinte " };

	private static final String[] DECENAS = { "veinti", "treinta ", "cuarenta ",
			"cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa ", "cien" };

	private static final String[] CENTENAS = { "ciento ", "doscientos ",
			"trescientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
			"setecientos ", "ochocientos ", "novecientos " };

	private static final String[] MES = { "enero", "febrero", "marzo", "abril",
			"mayo", "junio", "julio", "agosto", "septiembre", "octubre",
			"noviembre", "diciembre" };

	private static final String[][] UNIDADES_MEDIDA = {{"m2","metros cuadrados"}, {"h2","hectáreas"}, {"k2","kilometros cuadrados"}};
	
	private static final String[][] LETRAS = {{"A","a"}, {"B","b"}, {"C","c"}, {"D","d"}, {"E","e"}, {"F","f"}, {"G","g"}, {"H","h"}, 
		{"I","i"}, {"J","j"}, {"K","c"}, {"L","l"}, {"M","m"}, {"N","n"}, {"Ñ","ñ"}, {"O","o"}, {"P","p"}, {"Q","q"},
		{"R","r"}, {"S","s"}, {"T","t"}, {"U","u"}, {"V","v"}, {"W","u"}, {"X","x"}, {"Y", "y"}, {"Z","z"}, 
		{"0","cero"}, {"1","uno"}, {"2","dos"}, {"3","tres"}, {"4","cuatro"}, {"5","cinco"}, {"6","seis"}, {"7","siete"}, {"8","ocho"}, {"9","nueve"}, 
		{"-","guion"}, {"_","guion bajo"}, {" ","espacio"}, {"#","numero"}, {"@", "arroba"}, {"/","diagonal"}, {",", "coma"},{".","punto"}};
	
	
	public static final String MONEY_FORMAT = "moneda";

	public static final String NUMBER_FORMAT = "numerico";

	public static final String DATE_FORMAT = "fecha";
	
	public static final String PORCENTUAL_FORMAT = "porciento";

	/**
	 * Convierte a letras un numero de la forma $123,456.32
	 * 
	 * @param number
	 *            Numero en representacion texto
	 * @return Numero en letras en formato moneda(pesos y centavos)
	 */
	public static String convertMoneyToLetter(String number) {
		return convertNumberToLetter(number, NumberToLetterConverter.MONEY_FORMAT);
	}
	
	public static String convertPorcentual(String number) {
		return convertNumberToLetter(number, NumberToLetterConverter.PORCENTUAL_FORMAT);
	}

	/**
	 * Convierte a letras un numero de la forma 123,456,32
	 * 
	 * @param number
	 *            Numero en representacion texto
	 * @return Numero en letras en formato numero
	 */
	public static String convertNumberToLetter(String number) {
		return convertNumberToLetter(number, NumberToLetterConverter.NUMBER_FORMAT);
	}

	/**
	 * Convierte a letras una fecha de la forma 'dddd-mm-aa'
	 * 
	 * @param date
	 *            Fecha en representacion texto
	 * @return Numero en letras en formato fecha
	 */
	public static String convertDateToLetter(String date) {
		if (!date.matches("^(\\d{4}-?\\d{2}-?\\d{2})$")) {
			logger.info("Formato de fecha incorrecto: " + date);
			return "";
		}

		String splitDate[] = String.valueOf(date).split("-");
		int dia = Integer.parseInt(splitDate[2]);
		int mes = Integer.parseInt(splitDate[1]);
		StringBuilder converted = new StringBuilder();

		if (dia == 1) {
			converted.append("primero ");
		} else if (dia > 1) {
			converted.append(convertNumberToLetter(splitDate[2]));
		}

		converted.append(" de " + MES[mes - 1] + " de "
				+ convertNumberToLetter(splitDate[0]));
		return converted.toString();
	}
	
	public static String convertDateDDMMYYYToLetter(String date) {
		if (!date.matches("^(\\d{2}/?\\d{2}/?\\d{4})$")) {
			logger.info("Formato de fecha incorrecto: " + date);
			return "";
		}

		String splitDate[] = String.valueOf(date).split("/");
		int dia = Integer.parseInt(splitDate[0]);
		int mes = Integer.parseInt(splitDate[1]);
		StringBuilder converted = new StringBuilder();

		if (dia == 1) {
			converted.append("primero ");
		} else if (dia > 1) {
			converted.append(convertNumberToLetter(splitDate[0]));
		}

		converted.append(" de " + MES[mes - 1] + " de "
				+ convertNumberToLetter(splitDate[2]));

		return converted.toString();
	}	
	
	public static String convertDateDD_MM_YYYToLetter(String date) {
		if (!date.matches("^(\\d{2}-?\\d{2}-?\\d{4})$")) {
			logger.info("Formato de fecha incorrecto: " + date);
			return "";
		}

		String splitDate[] = String.valueOf(date).split("-");
		int dia = Integer.parseInt(splitDate[0]);
		int mes = Integer.parseInt(splitDate[1]);
		StringBuilder converted = new StringBuilder();

		if (dia == 1) {
			converted.append("primero ");
		} else if (dia > 1) {
			converted.append(convertNumberToLetter(splitDate[0]));
		}

		converted.append(" de " + MES[mes - 1] + " de "
				+ convertNumberToLetter(splitDate[2]));
		return converted.toString();
	}		

	/**
	 * Convierte a letras un numero de la forma 123,456.32
	 * 
	 * @param number
	 *            Numero en representacion texto
	 * @param formatType
	 *            Para indicar formato de texto en pesos o numerico
	 * @throws NumberFormatException
	 *             Si valor del numero no es valido (fuera de rango o )
	 * @return Numero en letras
	 */
	public static String convertNumberToLetter(String number, String formatType)
			throws NumberFormatException {
		boolean comaAlFinal = false;
		if(number.endsWith(",")) comaAlFinal = true;
		if(number.contains(",")) number = number.replace(",", "");	
		if(number.contains(",")) number = number.replace("'", "");		
		if(number.contains(",")) number = number.replace("$", "");		
		if(number.contains(" ")) number = number.replace(" ", "");
		if(number.contains("%")) number = number.replace("%", "");
		if(number.length()>0){
			DecimalFormat format = new DecimalFormat("###.########");
			
			Double num = Double.parseDouble(number);
			Boolean endcero = false;
			if(formatType.equals("moneda")){
				endcero = number.endsWith(".00");	
			}else{
				endcero = number.endsWith("0");
			}
			
			String sNumero = convertNumberToLetter(num, formatType,endcero);
System.out.println("nada");
if(comaAlFinal){				
				sNumero = sNumero!=null && sNumero.length()>0? sNumero.trim() + ",":sNumero ;
			}
			return sNumero;
		}else{
			return number;
		}
	}

	
	private static String quitaCerosDerecha(String numero){
		while (numero.endsWith("0")){
			numero = numero.substring(0,numero.length()-1);
		}
		return numero.length()>0?numero:"0";
	}
	
	/**
	 * Convierte un numero en representacion numerica a uno en representacion de
	 * texto. El numero es valido si esta entre 0 y 9'999'999.99
	 * 
	 * @param number
	 *            Numero a convertir
	 * @return Numero convertido a texto
	 * @throws NumberFormatException
	 *             Si el numero esta fuera del rango
	 */
	public static String convertNumberToLetter(double doubleNumber, String formatType,boolean finishCero) throws NumberFormatException {
		logger.info("=====> Entro a convertNumberToLetter: "+doubleNumber +" - "+formatType);
		StringBuilder converted = new StringBuilder();

		String patternThreeDecimalPoints = "##########.#####";

		DecimalFormat format = new DecimalFormat(patternThreeDecimalPoints);		
		format.setRoundingMode(RoundingMode.DOWN);

		BigDecimal decimal = BigDecimal.valueOf(doubleNumber);
		String formatedDouble = format.format(decimal);	
		decimal = BigDecimal.valueOf(Double.valueOf(formatedDouble));
		// Validamos que sea un numero legal
		if (doubleNumber >= Double.MAX_VALUE){
			System.out.printf("=====> El numero %d es mayor de %d, no es posible convertirlo\n",doubleNumber, Double.MAX_VALUE);
		}
		if (doubleNumber < 0)
			System.out.printf("=====> El numero %d debe ser positivo para poder ser transformado.\n");
				
		BigInteger entero = decimal.toBigInteger();
		
		System.out.println("Decimal::"+decimal.toString()+"::LENGTH::"+decimal.toString().length());
		int mult = 1;
		int mulAjuste = 2;
		if(!formatType.equals(MONEY_FORMAT)){
			
			
			if(finishCero){
				mulAjuste = 1;
			}
			for(int i = 0;i<(decimal.toString().length()-mulAjuste);i++){
				mult *= 10;
			}
			
		}else{
			mult=100;
		}
		Double fraccion = 0.00;
		if(!formatType.equals(MONEY_FORMAT)){
			fraccion = Math.ceil(decimal.doubleValue()*mult-entero.doubleValue()*mult); // para el manejo de millonesimas en el proceso de transformación de números.		
		}else{
			fraccion = decimal.doubleValue()*mult-entero.doubleValue()*mult; // para el manejo de millonesimas en el proceso de transformación de números.		
		}
		System.out.println(fraccion.intValue());
		String splitNumber[] = new String[2];
		splitNumber[0] = entero.toString();
		splitNumber[1] = fraccion.toString().substring(0,fraccion.toString().indexOf("."));
		//splitNumber[1] = quitaCerosDerecha(splitNumber[1]);
		
		System.out.printf("=====> NUMBER TO LETTER FOR: %s\t%s\t%s", String.valueOf(decimal), splitNumber[0], (splitNumber[1].length() > 1? splitNumber[1]:"0"));
		
		// Descompone el trio de millones
		int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],8))
				+ String.valueOf(getDigitAt(splitNumber[0], 7))
				+ String.valueOf(getDigitAt(splitNumber[0], 6)));
		
		if (millon == 1){
			converted.append("un millon ");
		} else if (millon > 1) {
			converted.append(convertNumber(String.valueOf(millon))
					+ "millones ");
		}
		
		// Descompone el trio de miles
		int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 5))
				+ String.valueOf(getDigitAt(splitNumber[0], 4))
				+ String.valueOf(getDigitAt(splitNumber[0], 3)));

		if (miles == 1)
			converted.append("mil ");
		else if (miles > 1)
			converted.append(convertNumber(String.valueOf(miles)) + "mil ");

		// Descompone el ultimo trio de unidades
		int cientos = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 2))
				+ String.valueOf(getDigitAt(splitNumber[0], 1))
				+ String.valueOf(getDigitAt(splitNumber[0], 0)));
		if (cientos == 1) {
			if (formatType.equals(MONEY_FORMAT)) {
				converted.append(" un ");
			} else if (formatType.equals(NUMBER_FORMAT)) {
				converted.append("uno ");
			}
		}

		if (millon + miles + cientos == 0 ){
			converted.append("cero ");
		}
		if (cientos > 1) {
			converted.append(convertNumber(String.valueOf(cientos)));
			String unidad = String.valueOf(getDigitAt(splitNumber[0], 0));
			completeUnit(converted, cientos, formatType, unidad);
		}
		String decimales = "0";
		if(splitNumber.length>1){
			 decimales = splitNumber[1];
			//converted = new StringBuilder(addFormatType(millon, miles, cientos, decimales, converted, formatType));
			//return converted.toString();
		}
		if(formatType.equals(PORCENTUAL_FORMAT)){
			return addFormatTypePorcentual(fraccion,(decimal.toString().length()-mulAjuste));

		}else{
			return addFormatType(millon, miles, cientos, decimales, converted, formatType);
		}

	}
	

	private static void completeUnit(StringBuilder converted, int cantidad,
			String formatType, String unidad) {

		if (cantidad > 20 && formatType.equals(NUMBER_FORMAT)) {
			double decimal = cantidad % 100;
			// 21,31,41..91 agregar UN+'O'
			if (unidad.equals("1") && !(decimal==11)) {
				converted.setLength(converted.length() - 1);
				converted.append("o ");
			}
		}
	}

	private static String addFormatType(int millon, int miles, int cientos,
			String strDecimales, StringBuilder converted, String formatType) {

		if (formatType.equals(MONEY_FORMAT)) {
			int centavos = Integer.parseInt(strDecimales);

			if (miles == 0 && cientos == 0) {
				converted.append("de ");
			}

			if (millon == 0 && miles == 0 && cientos == 1) {
				converted.append(" peso");
			} else {
				converted.append("pesos");
			}

			if (strDecimales.length() == 1 && centavos != 0) {
				//centavos *= 10;
				converted.append(" con "
						+ convertNumber(String.valueOf(centavos)) + " centavos");
			} else {
				if (centavos == 1)
					converted.append(" con un centavo");
				else if (centavos > 1) {
					converted.append(" con "
							+ convertNumber(String.valueOf(centavos))
							+ " centavos");
				}
			}
		} else if (formatType.equals(NUMBER_FORMAT)) {
			int decimales = Integer.parseInt(strDecimales);
			if (decimales > 0) {
				converted.append("punto ");
			}
			if (decimales == 1) {
				converted.append("uno");
			} else if (decimales > 1) {
				
				converted.append(convertNumber(String.valueOf(decimales)));
				String unidad = String.valueOf(getDigitAt(strDecimales, 0));
				completeUnit(converted, decimales, formatType, unidad);
			}

		} else if(formatType.equals(PORCENTUAL_FORMAT)){
			int decimales = Integer.parseInt(strDecimales);
			int entero = millon+miles+cientos;
			if(entero>1 || entero == 0){
				converted.append("enteros ");
			} else if(entero==1){
				converted.append("entero ");
			} else {
				converted.append("punto ");
			}
			if(decimales >1 && decimales < 100){ // ya esta preparado para las decimas
				//String unidad = String.valueOf(getDigitAt(strDecimales, 0));
				
				converted.append( convertNumberToLetter(decimales, NUMBER_FORMAT,false));
				completeUnit(converted, decimales, NUMBER_FORMAT, decimales+"");
				if(decimales<=10){
					converted.append("decimos de por ciento ");
				}else {
					converted.append("centésimos de por ciento ");
				}
			} else {
		
				StringBuilder bigDecimal = new StringBuilder();
				int dMiles = Integer.parseInt(String.valueOf(getDigitAt(strDecimales, 5))
							+ String.valueOf(getDigitAt(strDecimales, 4))
							+ String.valueOf(getDigitAt(strDecimales, 3)));
				if (dMiles == 1)
					bigDecimal.append("mil ");
				else if (dMiles > 1)
					bigDecimal.append(convertNumber(String.valueOf(dMiles)) + "mil ");
				int dCientos = Integer.parseInt(String.valueOf(getDigitAt(strDecimales, 2))
						+ String.valueOf(getDigitAt(strDecimales, 1))
						+ String.valueOf(getDigitAt(strDecimales, 0)));
				if (dCientos == 1) {
					bigDecimal.append("uno ");
				}
				if (dCientos > 1) {
					bigDecimal.append(convertNumber(String.valueOf(dCientos)));
					String dUnidad = String.valueOf(getDigitAt(strDecimales, 0));
					completeUnit(bigDecimal, dCientos, NUMBER_FORMAT, dUnidad);
				}					
				converted = new StringBuilder(addFormatType(0, dMiles, dCientos, "0", converted, NUMBER_FORMAT));
				converted.append(bigDecimal);
				if(decimales !=0){
				
					if(decimales<1000){
						converted.append("milésimos de por ciento ");
					} else if(decimales<10000){
						converted.append("diezmilésimos de por ciento ");
					} else if(decimales<100000){
						converted.append("cienmilésimos de por ciento ");
					} else if(decimales<1000000){
						converted.append("millonésimos de por ciento ");
					}
				}
			}
			
		}

		return converted.toString();

	}
	
	private static String addFormatTypePorcentual(Double cantidad,int longitud){
		
		StringBuilder retorno = new StringBuilder();
		retorno.append( convertNumberToLetter(cantidad, NUMBER_FORMAT,false));
		switch (longitud) {
		case 1:
			retorno.append("décimos");
			break;
		case 2:
			retorno.append("centésimos");
			break;
		case 3:
			retorno.append("milésimos");
			break;
		case 4:
			retorno.append("diezmilésimos");

			break;
		case 5:
			retorno.append("cienmilésimos");
			break;
		case 6:
			retorno.append("millonésimos");
			break;
		default:
			break;
		}
		
		retorno.append(" de porciento");
		return retorno.toString();
	}

	/**
	 * Convierte los trios de numeros que componen las unidades, las decenas y
	 * las centenas del numero.
	 * 
	 * @param number
	 *            Numero a convetir en digitos
	 * @return Numero convertido en letras
	 */
	private static String convertNumber(String number) {

		if (number.length() > 3){
			System.out.println("La longitud maxima debe ser 3 digitos y se tiene "+ number);
		}

		// Caso especial con el 100
		if (number.equals("100")) {
			return "cien ";
		}

		StringBuilder output = new StringBuilder();
		if (getDigitAt(number, 2) != 0)
			output.append(CENTENAS[getDigitAt(number, 2) - 1]);

		int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
				+ String.valueOf(getDigitAt(number, 0)));

		if (k <= 20)
			output.append(UNIDADES[k]);
		else if (k > 30 && getDigitAt(number, 0) != 0)
			output.append(DECENAS[getDigitAt(number, 1) - 2] + "y "
					+ UNIDADES[getDigitAt(number, 0)]);
		else
			output.append(DECENAS[getDigitAt(number, 1) - 2]
					+ UNIDADES[getDigitAt(number, 0)]);

		return output.toString();
	}

	/**
	 * Retorna el digito numerico en la posicion indicada de derecha a izquierda
	 * 
	 * @param origin
	 *            Cadena en la cual se busca el digito
	 * @param position
	 *            Posicion de derecha a izquierda a retornar
	 * @return Digito ubicado en la posicion indicada
	 */
	private static int getDigitAt(String origin, int position) {
		if (origin.length() > position && position >= 0)
			return origin.charAt(origin.length() - position - 1) - 48;
		return 0;
	}
	
	static public String letterToLetter(String clave){
		char[] chars = clave.toCharArray();
		StringBuilder vuelta = new StringBuilder();
		for(char c:chars){
			//System.out.println(c);
			vuelta.append(getString(c)).append(" ");
		}
		return vuelta.toString();
	}
	
	/***
	 * 
	 * @param medida debe de cumplir los siguientes casos: ###UM o ##,###UM o ###.##UM o ##,###.##UM es importante que la unidad de medida 
	 * sean los dos ultimos caracteres y los marcados como validos, m2, h2, k2 
	 * @return texto transformado con la unidad de medida
	 */
	static public String getUnitSurface(String medida){
		if(medida==null){
			return null;
		}		
		StringBuilder texto = new StringBuilder();
		
		String unidad = medida.trim().substring(medida.length()-2);
		unidad = unidad.toLowerCase();
		if(!(unidad.equals("m2")
				||unidad.equals("k2")
				||unidad.equals("h2"))){
					//return "** unidad de medida incorrecta -validas: m2 o k2 o h2 ***";
					return medida;
				}
		medida = medida.trim().substring(0, medida.length()-2);
		medida = convertNumberToLetter(medida, NUMBER_FORMAT);
		texto.append(medida).append(" ").append(descripcionUnidadMedida(unidad));
		return texto.toString();
	}

	static private String descripcionUnidadMedida(String unidad){
		for(String[] u:UNIDADES_MEDIDA){
			if(u[0].equals(unidad)){
				return u[1];
			}
		}
		return "-- sin unidad de medida --";
	}
	
	static private String getString(char c){
		for(String[] cadena:LETRAS){
			if(cadena[0].equals(String.valueOf(c).toUpperCase())){
				return cadena[1];
			}
		}
		return "";
	}
	
	/***
	 * Obtiene el texto de un número entero en su valor ordinario. El rango de transformación es entre 1 - 99
	 * @param numero valor entero del número que se transformará a ordinario
	 * @return java.lang.String que representa el valor ordinario de un número entero
	 * @throws NotariaException
	 */
	static public String getNumeroOrdinario(Integer numero) throws NotariaException {
		if(numero>99 && numero < 1){
			throw new NotariaException("Excepción al convertir un entero a número ordinario, el valor máximo es 99 y se tiene "+numero);
		}
		String strNumero = numero.toString();
		for(String[] especial:ORDINARIOS_ESPECIALES){
			if(especial[0].equals(strNumero)){
				return especial[1];
			}
		}
		String decimas = strNumero.length()==2?strNumero.substring(0,1):"";
		String unidades = strNumero.length()==2?strNumero.substring(1):strNumero.length()==1?strNumero:"";
		StringBuilder numeroOrdinario = new StringBuilder();
		if(!decimas.isEmpty()){
			for(String[] decimo:ORDINARIOS_DECIMOS){
				if(decimo[0].equals(decimas)){
					numeroOrdinario.append(decimo[1]);
					break;
				}
			}
		}
		if(!unidades.isEmpty()){
			for(String[] unidad:ORDINARIOS_UNIDADES){
				if(unidad[0].equals(unidades)){
					numeroOrdinario.append(" ").append(unidad[1]);
				}
			}
		}
		return numeroOrdinario.toString();
	}
	
	public static void main(String[] args){
		System.out.println(convertNumber("24"));
	}
	
}