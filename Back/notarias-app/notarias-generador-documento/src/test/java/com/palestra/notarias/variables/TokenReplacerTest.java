package com.palestra.notarias.variables;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.palestra.notarias.enums.EnumVariables;

public class TokenReplacerTest {
	
	public static void main(String args[]){
		TokenReplacerTest tr = new TokenReplacerTest();
		tr.remplazaTokenVariablePorValor();
	}
	
	public void remplazaTokenVariablePorValor() {
		TokenReplacer tokenReplacer = new TokenReplacer();
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("token1", "Nombre");
		tokens.put("token2", "Altavista 83 esquina con José María de Teresa");
		tokens.put("tokenNull", null);
		String sourceText = "1234567890${token1}abcdefg${token2}XYZ$000 ${tokenNull}";
		Reader reader = tokenReplacer.remplazaTokenVariablePorValor(sourceText,
				tokens, EnumVariables.VAR_PREFIX.toString(),
				EnumVariables.VAR_SUFIX.toString());
		int data = 0;
		try {
			data = reader.read();
			while (data != -1) {
				System.out.print((char) data);
				data = reader.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
