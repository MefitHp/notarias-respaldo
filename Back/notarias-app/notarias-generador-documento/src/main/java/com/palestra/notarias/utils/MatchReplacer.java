package com.palestra.notarias.utils;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchReplacer {

	private final Pattern pattern;
	/** Solo se transforman letras, por el momento**/
	private String value = "A";
	private int charValue = value.charAt(0);
	private int posNum = 0;

	public MatchReplacer(Pattern pattern) {
		this.pattern = pattern;
	}

	public String replacement(MatchResult matchResult){
		System.out.println("MATCH RESULT::"+matchResult.toString());
		System.out.println("STRING::"+String.valueOf((char) (charValue+1)));
		
		return String.valueOf((char) (charValue ++));
	}

	public String replace(String input) {

		Matcher m = pattern.matcher(input);

		StringBuffer sb = new StringBuffer();

		while (m.find())
			m.appendReplacement(sb, replacement(m.toMatchResult()));

		m.appendTail(sb);

		return sb.toString();
	}

	public String replaceToRomano(String input) {
		Matcher m = pattern.matcher(input);

		StringBuffer sb = new StringBuffer();

		while (m.find()){
			posNum++;
			System.out.println("POS:"+posNum);
			m.appendReplacement(sb,EscrituraUtills.RomanNumerals(posNum));
			continue;
			
		}
			
		m.appendTail(sb);

		return sb.toString();
	}

}
