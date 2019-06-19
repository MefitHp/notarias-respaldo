package com.palestra.notarias.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Clase para el manejo de operacion de escritura 
 * @author sofia
 *
 */
public class EscrituraUtills {
	
	static Logger logger = Logger.getLogger(EscrituraUtills.class);
	
	/**
	 * Dado un texto reemplaza el marcador por una letra su consecutivo
	 * correspondiente. 
	 *  ie. "texto #LETRA de #LETRA prueba" => "texto A de B prueba "
	 * @param input
	 * @return
	 */
	public static String replaceMarkerLetra(String input) {
		try{
			//Nota. Queda en duro, tal vez no cambie. 
			String marker = "#LETRA";
			MatchReplacer replacer = new MatchReplacer(Pattern.compile(marker));
			return replacer.replace(input);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String replaceMakerRomano(String input,String marker){
		try{
			MatchReplacer replacer = new MatchReplacer(Pattern.compile(marker));
			return replacer.replaceToRomano(input);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public static String RomanNumerals(int Int) {
	    LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
	    roman_numerals.put("M", 1000);
	    roman_numerals.put("CM", 900);
	    roman_numerals.put("D", 500);
	    roman_numerals.put("CD", 400);
	    roman_numerals.put("C", 100);
	    roman_numerals.put("XC", 90);
	    roman_numerals.put("L", 50);
	    roman_numerals.put("XL", 40);
	    roman_numerals.put("X", 10);
	    roman_numerals.put("IX", 9);
	    roman_numerals.put("V", 5);
	    roman_numerals.put("IV", 4);
	    roman_numerals.put("I", 1);
	    String res = "";
	    for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
	      int matches = Int/entry.getValue();
	      res += repeat(entry.getKey(), matches);
	      Int = Int % entry.getValue();
	    }
	    return res;
	  }
	
	  private static String repeat(String s, int n) {
	    if(s == null) {
	        return null;
	    }
	    final StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < n; i++) {
	        sb.append(s);
	    }
	    return sb.toString();
	  }


	public static String replaceASCII(String txtplantilla) {
		return txtplantilla.replace("&amp;", "&");
	}

}
