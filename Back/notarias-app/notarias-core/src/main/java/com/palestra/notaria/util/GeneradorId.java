package com.palestra.notaria.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

public class GeneradorId {
	
	public static String generaId(Object object){
		//System.out.println("hashCode() "+object.hashCode());
		//System.out.println("timestamp "+new Timestamp(new Date().getTime()));
		String id = (String.valueOf(object.hashCode())+new Timestamp(new Date().getTime())).trim();
		//System.out.println("string final "+id);
		
		return encriptaEnMD5(id);
	}
	
	public static String encriptaEnMD5(String stringAEncriptar){
		
		final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
        try
        {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++)
           {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
    }

}
