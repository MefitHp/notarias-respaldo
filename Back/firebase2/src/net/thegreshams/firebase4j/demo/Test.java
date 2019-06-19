package net.thegreshams.firebase4j.demo;

import java.awt.List;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import modelos.Incidencia;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import net.thegreshams.firebase4j.service.Firebase.FirebaseRestMethod;

public class Test {

	public static void main(String[] args) throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException {
	
		String baseUrl ="https://notaria-6d7e7.firebaseio.com/";
		Firebase firebase = new Firebase(baseUrl);
		
		FirebaseResponse r =  firebase.get();
		
		System.out.println(r.getRawBody());
		
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		
		Incidencia in = new Incidencia();
		
		in.setNombre("Hola mundo2");
		in.setMensaje("Descripcion de prueba actualizada recientemente");
		in.setFecha(new Date());
		
		Map<String, Object> dataMap2 = new LinkedHashMap<String, Object>();
		dataMap2.put("Incidencia", in);
		
		dataMap.put("-KgpKVX9h43n31QprOJi", dataMap2);
		//FirebaseResponse response = firebase.patch( dataMap );			
		//firebase.put(dataMap);
		
		FirebaseResponse response = firebase.get();
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Incidencia> incidencias = new ArrayList<Incidencia>();
		
		Iterator entries = response.getBody().entrySet().iterator();

		System.out.println(incidencias.get(0).getNombre());
		
	}

}
