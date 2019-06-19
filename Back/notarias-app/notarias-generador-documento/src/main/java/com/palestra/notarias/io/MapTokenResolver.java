package com.palestra.notarias.io;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author sofia
 *
 */
public class MapTokenResolver implements ITokenResolver {

	protected Map<String, String> tokenMap = new HashMap<String, String>();

	public MapTokenResolver(Map<String, String> tokenMap) {
		this.tokenMap = tokenMap;
	}

	@Override
	public String resolveToken(String tokenName) {
		return this.tokenMap.get(tokenName);
	}

}
