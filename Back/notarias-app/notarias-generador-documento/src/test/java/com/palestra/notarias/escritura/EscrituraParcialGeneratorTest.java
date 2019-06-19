package com.palestra.notarias.escritura;

//import java.util.List;
//
//import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
//import com.palestra.notaria.exceptions.NotariaException;
//import com.palestra.notaria.modelo.Acto;
//import com.palestra.notaria.modelo.EscrituraActo;
//import com.palestra.notarias.pojo.FiltroVariables;

public class EscrituraParcialGeneratorTest {

//	public static void main(String args[]) throws NotariaException {
//
//		EscrituraParcialGenerator pGenerator = new EscrituraParcialGenerator();
//		EscrituraActoBoImpl escrituraActoBo = new EscrituraActoBoImpl();
//
//		List<EscrituraActo> escrituraActos = escrituraActoBo
//				.findByEscrituraId("c6f78b34dbf55ce8e411a6beb943fc3a");
//
//		if (escrituraActos != null) {
//			//Prueba con un acto
//			EscrituraActo escact = escrituraActos.get(0);
//			Acto acto = escact.getActo();
//
//			if (acto != null) {
//				String primerSustitucion = pGenerator.llenaPlantillaDelActo(acto, false);
//				if(primerSustitucion!=null){
//					System.out.println("************************************************");
//					System.out.println("************************************************");
//					System.out.println("************************************************");
//					System.out.println(primerSustitucion);
//					System.out.println("************************************************");
//					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
//					
//					FiltroVariables fvariables = new FiltroVariables();
//					fvariables.setIdescritura("1983471975c4c1dc774cd63ecc10a875");
//					try {
//						
//						Boolean forceReplace = true;// Si valor varaible==null, reemplazar por char '_'
//						String segundaSustitucion = pGenerator.reemplazarVariables(fvariables, primerSustitucion, forceReplace);
//						if(segundaSustitucion!=null){
//							System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
//							System.out.print(segundaSustitucion);
//							System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
//							System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
//						}
//					} catch (Exception e) {
//						System.out.println("No se pudo reemplazar variables error");
//						//TODO: retornar 'primerSustitucion'
//						e.printStackTrace();
//					}
//					
//				}
//			}
//		}
//	}
}