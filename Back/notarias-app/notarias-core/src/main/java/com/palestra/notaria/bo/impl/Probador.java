//package com.palestra.notaria.bo.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import com.palestra.notaria.modelo.ElementoCatalogo;
//import com.palestra.notaria.modelo.Persona;
//import com.palestra.notaria.util.GeneradorId;
//
//public class Probador {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		PersonaBoImpl manejadorPersonas = new PersonaBoImpl();
//		Persona per = new Persona();
//		per.setIdpersona(GeneradorId.generaId(per));
//		
//		ElementoCatalogo tipoPersona = new ElementoCatalogo();
//		tipoPersona.setIdelemento("60d0f23b8e2e8fa9665b95a09b7cae8b");
//		
//		ElementoCatalogo nacionalidad = new ElementoCatalogo();
//		nacionalidad.setIdelemento("ca563e8a0bbbc4187299b337c247569c");
//		
//		ElementoCatalogo estadoCivil = new ElementoCatalogo();
//		estadoCivil.setIdelemento("3a3cbd14745968fe01a7c0681de40374");
//		
//		per.setTbcfgm911(tipoPersona);
//		per.setTbcfgm912(nacionalidad);
//		per.setTbcfgm913(estadoCivil);
//		
//		per.setDsnombre("Antonio");
//		per.setDsapellidopat("Ortiz");
//		per.setDsapellidomat("Flores");
//		per.setFechanacimiento(new Date());
//		per.setIdsesion("54564546");
//		per.setDsnombrecompleto("Antonio Ortiz Flores");
//		
////		per.setIdpersona(GeneradorId.generaId(per));
////		
////		ElementoCatalogo tipoPersona = new ElementoCatalogo();
////		tipoPersona.setIdelemento("60d0f23b8e2e8fa9665b95a09b7cae8b");
////		
////		ElementoCatalogo nacionalidad = new ElementoCatalogo();
////		nacionalidad.setIdelemento("ca563e8a0bbbc4187299b337c247569c");
////		
////		ElementoCatalogo estadoCivil = new ElementoCatalogo();
////		estadoCivil.setIdelemento("3a3cbd14745968fe01a7c0681de40374");
////		
////		per.setTbcfgm911(tipoPersona);
////		per.setTbcfgm912(nacionalidad);
////		per.setTbcfgm913(estadoCivil);
////		
////		per.setDsnombre("Carlos");
////		per.setDsapellidopat("Ortiz");
////		per.setDsapellidomat("Flores");
////		per.setFechanacimiento(new Date());
////		per.setIdsesion("54564546");
////		per.setDsnombrecompleto("Carlos Ortiz Flores");
//		
////		per.setIdpersona(GeneradorId.generaId(per));
////		
////		ElementoCatalogo tipoPersona = new ElementoCatalogo();
////		tipoPersona.setIdelemento("60d0f23b8e2e8fa9665b95a09b7cae8b");
////		
////		ElementoCatalogo nacionalidad = new ElementoCatalogo();
////		nacionalidad.setIdelemento("ca563e8a0bbbc4187299b337c247569c");
////		
////		ElementoCatalogo estadoCivil = new ElementoCatalogo();
////		estadoCivil.setIdelemento("3a3cbd14745968fe01a7c0681de40374");
////		
////		per.setTbcfgm911(tipoPersona);
////		per.setTbcfgm912(nacionalidad);
////		per.setTbcfgm913(estadoCivil);
////		
////		per.setDsnombre("Carla");
////		per.setDsapellidopat("Ortiz");
////		per.setDsapellidomat("Flores");
////		per.setFechanacimiento(new Date());
////		per.setIdsesion("54564546");
////		per.setDsnombrecompleto("Carla Ortiz Flores");
//		
//		Persona result = manejadorPersonas.save(per);
//		
//		List<Persona> listaPer = manejadorPersonas.findAll();
//		if(listaPer!=null)
//			for(Persona p:listaPer)
//				System.out.println(p);
//		
//
//	}
//	
//
//
//}
