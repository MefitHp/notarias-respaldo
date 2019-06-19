package com.palestra.notarias.variables;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.bo.ComparecienteHijoBo;
import com.palestra.notaria.bo.ComparecienteRepresentanteBo;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteConyugeBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteHijoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteRepresentanteBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notarias.utils.VariableUtils;

public class VariableValueAssignerCompareciente {
	
	public final static String TIPO_PERSONA_MORAL = "MORAL";
	public final static String TIPO_PERSONA_FISICA = "FÍSICA";
	public final static String TIPO_COMPARECIENTE_REPRESENTANTE = "REPRESENTANTE";
	public final static String TIPO_COMPARECIENTE_TESTADOR = "TESTADOR";

	private Compareciente compareciente;
	
	public VariableValueAssignerCompareciente(){}
	
	public VariableValueAssignerCompareciente(Compareciente compareciente) {
		setCompareciente(compareciente);
	}

	private char calculaGenero(){
		if(compareciente.getTratamiento().getDselemento().startsWith("el")){
			return 'M';
		} else {
			return 'F';
		}
	}
	
	public char calculaGenero(String tratamiento){
		if(tratamiento.startsWith("el")){
			return 'M';
		} else {
			return 'F';
		}
	}
	
	public String nombreCompleto(){
		if(compareciente.getPersona().getTipopersona()==null || compareciente.getPersona().getTipopersona().getDselemento().toUpperCase().equals(TIPO_PERSONA_MORAL)){
			StringBuilder nombreCompleto = new StringBuilder();
			nombreCompleto.append("\"").append(compareciente.getPersona().getDsnombrecompleto()).append("\" ");
			nombreCompleto.append(compareciente.getPersona().getRegimenfiscal()!=null?compareciente.getPersona().getRegimenfiscal().getDselemento():"");
			return nombreCompleto.toString().toUpperCase();
		}else{
			return compareciente.getPersona().getDsnombrecompleto().toUpperCase();
		}
		
	}

	public String tratamiento(){
		if(compareciente.getTratamiento()!=null){
			return compareciente.getTratamiento().getDselemento();
		}else{
			return "";
		}
	}
	
	public String nombreCompletoConTratamiento(){
		StringBuilder nombreCompleto = new StringBuilder("");
		if(compareciente.getTratamiento()!=null && !compareciente.getPersona().getTipopersona().getDscodigo().equals("PM")){
			nombreCompleto.append(tratamiento()).append(" ");
		}		
		nombreCompleto.append(nombreCompleto());
		return nombreCompleto.toString();
	}
	
	public String nacionalidadConGenero(){
		String nacionalidad = compareciente.getPersona().getNacionalidad().getDselemento();		
		if(nacionalidad!=null && compareciente.getTratamiento()!=null && nacionalidad.length()>0 && compareciente.getTratamiento().getDselemento().length()>0){
			nacionalidad = nacionalidad.substring(0,nacionalidad.length()-1);
			if(calculaGenero()=='M'){ // es masculino
				nacionalidad = nacionalidad+"o";
			} else { // es la, por lo tanto es femenino
				nacionalidad = nacionalidad+"a";
			}
			return nacionalidad;
		}
		return " ";
	}
	
	public String esRepresentada(){
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try{
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			if (representantes!=null && representantes.size()>0){
				return "true";
			} else {
				return "false";
			}				
		}catch(NotariaException e){
			System.out.printf("=====> compareciente:esRepresentada "+e.getMessage());
			e.printStackTrace(System.out);
			return "false";
		}
	}
	
	public String esRepresentadaTexto(){
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try{
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			if (representantes!=null && representantes.size()>0){
				if(representantes.size()==1){
				return "el representante de";
				}else{
					return "los representantes de";
				}
			} else {
				return "";
			}				
		}catch(NotariaException e){
			System.out.printf("=====> compareciente:esRepresentada "+e.getMessage());
			e.printStackTrace(System.out);
			return "false";
		}
	}
	
	public String originarioConGenero(){
		String originario = compareciente.getPersona().getDslugarnacimiento();
		String genero = compareciente.getTratamiento().getDselemento();
		if(originario!=null && genero!=null && originario.length()>0 && genero.length()>0){
			if(calculaGenero()=='M'){ // es masculino
				originario = "originario de "+originario;
			} else { // es la, por lo tanto es femenino
				originario = "originaria de "+originario;
			}
			return originario;
		}
		return " ";
	}
	
	public String nombre(){
		return compareciente.getPersona().getDsnombre();
	}
	
	public String apellidoPaterno(){
		return compareciente.getPersona().getDsapellidopat();
	}
	
	public String apellidoMaterno(){
		return compareciente.getPersona().getDsapellidomat()!=null?compareciente.getPersona().getDsapellidomat():"";
	}
	
	public String nacimientoEstado(){
		return compareciente.getPersona().getDslugarnacimiento();
	}    
	
	public String fechaNacimiento(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(compareciente.getPersona().getFechanacimiento()!=null){
			return dateFormat.format(compareciente.getPersona().getFechanacimiento());
		}else{
			return " ";
		}
	}
	
	public String estadoCivilConGenero(){
		String estadoCivil = compareciente.getEstadoCivil().getDselemento();
		String genero = compareciente.getTratamiento().getDselemento();
		if (estadoCivil!=null && genero!=null && estadoCivil.length()>0 && genero.length()>0){
			estadoCivil = estadoCivil.substring(0, estadoCivil.length()-1);
			if(calculaGenero()=='M'){ // es masculino
				return estadoCivil+"o";
			}else{
				return estadoCivil+"a";
			}
		}
		return " ";
	}
	
	
	public String estadoCivilActualAnterior(){
			
			ComparecienteConyuge conyuges;
			StringBuilder cadena = new StringBuilder("es el de ");
			String tratamiento = compareciente.getTratamiento()!=null?compareciente.getTratamiento().getDselemento():"";

			try {
				conyuges = getConyuges(compareciente.getIdcompareciente());

			} catch (NotariaException e) {
				System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Exception %s%n",e.getMessage());
				e.printStackTrace();
				return null;
			}
			//aquí van todos los casos:
			
			if(conyuges == null ||(conyuges.getConyugeCompra()==null && conyuges.getConyugeActual()==null)){ //se considera que es soltero al momento de la compra
				cadena.append("Soltero");
			}else if(conyuges.getConyugeCompra()!=null && conyuges.getIsmismoconyuge()){
				cadena.append("Casado, como ha quedado acreditado en el antecedente primero de este instrumento");
			}else if(conyuges.getConyugeActual()!=null || !conyuges.getIsmismoconyuge()){
				System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Intuimos que esta casado y ambos compraron por variable de conyugeCompra");
				cadena.append("Casado, con ");
				if(conyuges.getConyugeActual().getTratamiento()!=null){
					cadena.append(conyuges.getConyugeActual().getTratamiento().getDselemento().toLowerCase());
				}
				cadena.append(" ");
				cadena.append(conyuges.getConyugeActual().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeActual().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeActual().getPersona().getDsapellidomat());
				cadena.append(" bajo el regimen de");
				if(conyuges.getConyugeActual().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){
					cadena.append(" separación de bienes ");
				} else {
					cadena.append(" sociedad conyugal ");
				}						
				cadena.append("(según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA)");}/*else if(conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){ //es proindiviso						
				System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Es el mismo caso que si estuviese soltero");
				cadena.append("").append(tratamiento.toLowerCase()).append(" ");
				cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
				cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
				cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());						
				cadena.append(" bajo el regimen de separación de bienes (según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apendice de esta escritura con la letra #LETRA), adquirió por");						
			}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
				System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Se compro bajo sociedad conyugal");
				cadena.append("").append(tratamiento.toLowerCase()).append(" ");
				cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
				cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
				//cadena.append("").append(conyuges.getConyugeCompra().getTratamiento().getDselemento()).append(" ");
				cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());
				cadena.append(" bajo el regimen de sociedad conyugal (según consta de la copia certificada del acta de matrimonio que se me exhibe  y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA) adquirió por");
			}*/else{
				// NOTHING TO DO, not case for it
				System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: caso no estimado");
			}
			


		return cadena.toString();
	}
	
	
	/***
	 * se utiliza como en VariablesComplejaDefinicion.compareciente_ambos_compraron
	 * @return texto con el valor del compareciente, si es empresa se retorna el nombre completo; de otra fora se calcula el conyugue
	 */
	public String nombreCompletoConEstadoCivil(){
		StringBuilder texto = new StringBuilder();
		if(compareciente.getPersona().getTipopersona().getDselemento().toUpperCase().equals(TIPO_PERSONA_MORAL)){
			texto.append(nombreCompleto().toUpperCase()).append(" vende");
		} else {
			try{
				texto.append(VariableUtils.calculaTextoComparecienteAmbosCompraron(compareciente));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				return "--- no se logro transformar el texto de compareciente ambos compraron ---";
			}
		}
		return texto.toString();
	}
	
	
	public String hijosProcreados(){
		StringBuilder texto = new StringBuilder();
		ComparecienteBo combo = new ComparecienteBoImpl();
		ComparecienteHijoBo comHijBo= new ComparecienteHijoBoImpl();
		try {
			List<Compareciente> esposas = combo.listadoComparecientes(compareciente, "Esposo(a)");
			if(esposas.size()>0){
				Collections.reverse(esposas);
				for(int i = 0 ;i<esposas.size();i++){
					Compareciente e = esposas.get(i);
					List<Compareciente> hijos = comHijBo.findByEsposaId(e.getIdcompareciente());
						if(esposas.size()>1 && i==0){
							texto.append(", casado en primeras nupcias con ");
							
						}else{
							texto.append(", casado con ");
						}
						texto.append(e.getTratamiento().getDselemento()+" ");
						texto.append(e.getPersona().getDsnombrecompleto()+" ");
						if(hijos !=null && hijos.size()>0){
						int actualH = 0;
						int iHijos = hijos.size()-1;
						texto.append(" con quien ha procreado a ");
						for(Compareciente h:hijos){
							texto.append(h.getPersona().getDsnombrecompleto());
							texto.append(iHijos > ++actualH?", ":" y a ");					
						}
						if(texto.toString().endsWith(" y a ")){
							texto = new StringBuilder(texto.substring(0, texto.length()-5));
							
						}
					}
					
					
				}
				
			}
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			return "--- no se logro transformar el texto de compareciente hijos ---";
		}
		
		return texto.toString();
		
	}
	
	private Compareciente getAutorizante(String tipo){
							
					ComparecienteConyuge conyuges;
					try {
						conyuges = getConyuges(compareciente.getIdcompareciente());

					} catch (NotariaException e) {
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Exception %s%n",e.getMessage());
						e.printStackTrace();
						return null;
					}
					if(conyuges!=null){
						//aquí van todos los casos:
					if(conyuges.getIsmismoconyuge() || tipo.equals("COMPRA")){
						Compareciente conyugeCompra = conyuges.getConyugeCompra();
						String regimen = conyugeCompra.getRegimen().getDselemento().toUpperCase();
						
						if(conyuges.getConyugeCompra()!=null && regimen.equals("SOCIEDAD CONYUGAL") && !conyugeCompra.getAmboscompran()){
							return conyugeCompra;
							}else{
								// NOTHING TO DO, not case for it
								System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: caso no estimado");
							}
						}
					
						Compareciente conyugeActual = conyuges.getConyugeActual();
						String regimen = conyugeActual.getRegimen().getDselemento().toUpperCase();
						if(conyuges.getConyugeActual()!=null && regimen.equals("SOCIEDAD CONYUGAL") && !conyugeActual.getAmboscompran()){
							return conyugeActual;
							}else{
								// NOTHING TO DO, not case for it
								System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: caso no estimado");
							}
					}		
			return null;
		
	}
	
	
	public String calculaAutorizante(){
		StringBuilder cadenareturn = new StringBuilder();
		if(this.compareciente.getPersona().getTipopersona().getDscodigo().equals("PM") || 
			(this.compareciente.getTipoCompareciente()!=null && 
			!this.compareciente.getTipoCompareciente().getDsnombre().toUpperCase().equals("VENDEDOR")))
		{
			return cadenareturn.toString();
		}
		Compareciente autorizante = getAutorizante("COMPRA");
		
		if(autorizante!=null){
			cadenareturn.append(" con el consentimiento de su conyuge ");
			StringBuilder nombre = new StringBuilder();
			nombre.append(autorizante.getTratamiento().getDselemento()).append(" ");
			nombre.append(autorizante.getPersona().getDsnombrecompleto().toUpperCase());		
			cadenareturn.append(nombre.toString());
		}

		return cadenareturn.toString();
		
	}
	
	public String calculaAutorizanteActual(){
		
		Compareciente autorizante = getAutorizante("ACTUAL");
		StringBuilder cadenareturn = new StringBuilder();
		
		if(compareciente.getTratamiento().getDscodigo().equals("ELSE")){
			cadenareturn.append("el mencionado señor ");
		}else if (compareciente.getTratamiento().getDscodigo().equals("LASE")) {
			cadenareturn.append("la mencionada señora ");
		}else{
			cadenareturn.append("la mencionada señorita ");
		}
		
		
		if(autorizante!=null){
			cadenareturn.append("con el consentimiento de su conyuge ");
			StringBuilder nombre = new StringBuilder();
			nombre.append(autorizante.getTratamiento().getDselemento()).append(" ");
			nombre.append(autorizante.getPersona().getDsnombrecompleto().toUpperCase());		
			cadenareturn.append(nombre.toString());
		}
		return cadenareturn.toString();
		
	}
	
	public String obtenAutorizanteTitulo(){
		Compareciente autorizante = getAutorizante("COMPRA");
		StringBuilder cadenareturn = new StringBuilder();
		
		if(autorizante!=null){
			StringBuilder nombre = new StringBuilder();
			nombre.append(autorizante.getTratamiento().getDselemento()).append(" ");
			nombre.append(autorizante.getPersona().getDsnombrecompleto().toUpperCase());		
			cadenareturn.append(nombre.toString());
		}

		return cadenareturn.toString();
		
	}
	
	
	public String calculaDeudorGarante(){
		StringBuilder texto = new StringBuilder();
		ComparecienteBo combo = new ComparecienteBoImpl();
		ArrayList<Compareciente> garantes  = new ArrayList<Compareciente>();
		try {
			garantes = (ArrayList<Compareciente>) combo.listadoComparecientesByActo(compareciente.getActo().getIdacto(),"Garante");
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
		}
		if(garantes.size()>0){
			//Victor Espinosa
			// POR EL MOMENTO SOLO TOMO EL PRIMER GARANTE COMO EJEMPLO DE CREACION DE VARIABLES COMPLEJAS, FALTAN LOS MULTIPLES GARANTES
			
			texto.append(garantes.get(0).getTratamiento().getDselemento()+" "+garantes.get(0).getPersona().getDsnombrecompleto());
		}
			else{
			texto.append(compareciente.getTratamiento().getDselemento()+" "+compareciente.getPersona().getDsnombrecompleto());
		}
		
		return texto.toString();
	}
	
	public String comproProIndiviso(){
		StringBuilder texto = new StringBuilder();
		if(compareciente.getAmboscompran()!=null && compareciente.getAmboscompran()){
			texto.append(" y pro-indiviso por partes iguales");
		} else {
			texto.append("");
		}
		return texto.toString();
	}
	
	private ComparecienteConyuge getConyuges(String idcompareciente) throws NotariaException{
		ComparecienteConyuge conyuges = null;
		ComparecienteConyugeBo conyugeBo = new ComparecienteConyugeBoImpl();				
		conyuges = conyugeBo.findById(compareciente.getIdcompareciente());
		return conyuges;
	}
	
	public String compraronEstadoCivil() {
		StringBuilder cadena = new StringBuilder(); //Cadena donde se devulve el texto de antecedente compra de inmueble
				
				String tratamiento = compareciente.getTratamiento()!=null?compareciente.getTratamiento().getDselemento():"";
				ComparecienteConyuge conyuges;
				try {
					conyuges = getConyuges(compareciente.getIdcompareciente());

				} catch (NotariaException e) {
					System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Exception %s%n",e.getMessage());
					e.printStackTrace();
					return null;
				}
				if(conyuges!=null){
					//aquí van todos los casos:
					
					if(conyuges.getConyugeCompra()==null){ //se considera que es soltero al momento de la compra
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Intuimos que es soltero ya que conyuge compra viene en null");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando soltero y declara que el inmueble que más adelante se relaciona jamás lo aporto a sociedad conyugal alguna, adquirió por");
					}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getAmboscompran()){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Intuimos que esta casado y ambos compraron por variable de conyugeCompra");
						cadena.append(tratamiento.toLowerCase()).append(" "); //Adquirieron ambos conyugues 
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat());
						cadena.append(" y ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());
						cadena.append(" estando casados entre sí bajo el regimen de");
						if(conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){
							cadena.append(" separación de bienes ");
						} else {
							cadena.append(" sociedad conyugal ");
						}						
						cadena.append("(según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA), adquirieron por");
					}else if(conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){ //es proindiviso						
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Es el mismo caso que si estuviese soltero");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());						
						cadena.append(" bajo el regimen de separación de bienes (según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apendice de esta escritura con la letra #LETRA), adquirió por");						
					}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Se compro bajo sociedad conyugal");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						//cadena.append("").append(conyuges.getConyugeCompra().getTratamiento().getDselemento()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());
						cadena.append(" bajo el regimen de sociedad conyugal (según consta de la copia certificada del acta de matrimonio que se me exhibe  y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA) adquirió por");
					}else{
						// NOTHING TO DO, not case for it
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: caso no estimado");
					}
				}else{
					//seguro es soltero:
					if(compareciente.getPersona().getTipopersona().getDscodigo().equals("PF")){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: seguro es soltero, no se encontro registro en conyuge");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando soltero y declara que el inmueble que más adelante se relaciona jamás lo aporto a sociedad conyugal alguna, ");
					}
					cadena.append("adquirió por");
					
				} 
				
			
		return cadena.toString();
	}
	
	
	public String certficiacionpersonalidad(){
		StringBuilder texto = new StringBuilder();
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try {
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			if(representantes !=null){
				boolean repmuchos =(representantes.size() > 1)?false:true;
				String el=(repmuchos)?"el":"los";
				String n=(repmuchos)?"":"n";
				String s=(repmuchos)?"":"s";
				texto.append("#E.- ");
				texto.append("Que "+el+" representante"+s+" de "+compareciente.getPersona().getDsnombrecompleto()+", manifiesta"+n+" que su representada se encuentra capacitada ");
				texto.append("legalmente para la celebración de este acto, y acredita"+n+" la personalidad que ostenta"+n+", ");
				texto.append("que no le"+s+" ha sido revocada, ni en forma alguna modificada con la certificación que agrego al apéndice de esta escritura con la letra “#LETRA”.");
				}
		} catch(NotariaException e){
			System.out.printf("=====> compareciente:representante "+e.getMessage());
			e.printStackTrace(System.out);
			return null;
		}
		return texto.toString();
	}
	
	
	
	public String representante(){
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try{
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			StringBuilder textoRepresentantes = new StringBuilder("");
			if (representantes!=null && representantes.size()==1){
				Compareciente representante = representantes.get(0);
				if(representante.getTratamiento()!=null){
					textoRepresentantes.append(representante.getTratamiento().getDselemento()).append(" ");
				}
				textoRepresentantes.append(representante.getPersona().getDsnombrecompleto().toUpperCase());
			}else if(representantes!=null && representantes.size()>1){
				/*Integer iRepresentantes = representantes.size()-1;
				Integer actual = 0;
				for(Compareciente representante:representantes){
					if(representante.getTratamiento()!=null){
						textoRepresentantes.append(representante.getTratamiento().getDselemento()).append(" ");
					}
					textoRepresentantes.append(representante.getPersona().getDsnombrecompleto());
					textoRepresentantes.append(iRepresentantes > ++actual?", ":" y ");					
				}
				if(textoRepresentantes.toString().endsWith(" y ")){
					textoRepresentantes = new StringBuilder(textoRepresentantes.substring(0, textoRepresentantes.length()-3));
				}*/
				VariableValueAssigner vva = new VariableValueAssigner();
				textoRepresentantes.append(vva.calculaTratamientoComparecientes(representantes, ""));
			}
			return textoRepresentantes.toString();
		}catch(NotariaException e){
			System.out.printf("=====> compareciente:representante "+e.getMessage());
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public String empleo(){
		if(compareciente.getDsocupacion()!=null){
			return compareciente.getDsocupacion();
		}else{
			return "";
		}
		
	}
	
	public String calculaRFCRepresentada(){
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try{
			Compareciente representada = representanteBO.calculaRepresentadoPorIdRepresentante(compareciente.getIdcompareciente());
			return representada!=null?representada.getPersona().getDsrfc():"";
		}catch(NotariaException e){
			System.out.printf("=====> compareciente:representante "+e.getMessage());
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public String capacidadRepresentantes(){
		StringBuilder capacidadStr = new StringBuilder("Que ");
		VariableToTokenTransformer tokenTransformer = new VariableToTokenTransformer();
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try {
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			boolean plural = true;

			if (representantes==null || representantes.size()<1){
				return "";
			} else if(representantes.size()>1) {
				capacidadStr.append("los representantes de ");
				
				
			}else{
				capacidadStr.append("el representante de ");
				plural = false;
			}		
			if(compareciente.getTratamiento()!=null){
			capacidadStr.append(compareciente.getTratamiento().getDselemento());
			capacidadStr.append(" ");
			
			}
			capacidadStr.append(compareciente.getPersona().getDsnombrecompleto());
			
			if(plural){
				capacidadStr.append(" manifiestan ");
			}else{
				capacidadStr.append(" manifiesta ");
			}
			
			
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			return null;
		}
		
		capacidadStr.append("que su representada  se encuentra capacitada legalmente para la celebración de este acto, y acredita la personalidad que ostenta que no les ha sido revocada ni en forma alguna modificada con las certificaciones que agrego al apéndice de esta escritura con la letra “#LETRA”.");
		return capacidadStr.toString();		
		
	}
	
	public String datosIdentificacion(){		
		StringBuilder identificacion = new StringBuilder();
		VariableToTokenTransformer tokenTransformer = new VariableToTokenTransformer();
		try{
			if(compareciente.getTipoCompareciente().getDsnombre().toUpperCase().equals(TIPO_COMPARECIENTE_REPRESENTANTE)){
				//valida que tenga una relación en la tabla de representados:
				ComparecienteRepresentanteBo representados = new ComparecienteRepresentanteBoImpl();
				Compareciente representado = representados.calculaRepresentadoPorIdRepresentante(compareciente.getIdcompareciente());
				if(representado!=null ){ 
					identificacion.append(nombreCompleto()).append(", ");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas", nacionalidadConGenero())).append(", ");
					identificacion.append(originarioConGenero()).append(", lugar donde nació el día ");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("texto,minusculas",fechaNacimiento())).append(", ");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas",estadoCivilConGenero())).append(", con domicilio en ");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("texto_con_numeros",domicilio())).append(", ");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas",empleo())).append(".<br />");
					identificacion.append("Y manifiesta que su representada se encuentra inscrita en el ");
					identificacion.append("Registro Federal de Contribuyentes con el número \"");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("letras, mayusculas",representado.getPersona().getDsrfc())).append("\".<br/>");
				}else{
					// no hago nada, ya que hay inconsistencia, m�s representantes de que representados:
					System.out.println(String.format("=====> Compareciente.datosIdentificacion - Hay inconsistencia, más representantes de que representados (%s)", 
							compareciente.getPersona().getDsnombrecompleto()));
				}
			} else if(!compareciente.getPersona().getTipopersona().getDselemento().toUpperCase().equals(TIPO_PERSONA_MORAL)){						
				identificacion.append(nombreCompleto()).append(", ");
				identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas", nacionalidadConGenero())).append(", ");
				identificacion.append(originarioConGenero()).append(", lugar donde nació el día ");
				identificacion.append(tokenTransformer.evaluaFuncionVariable("texto,minusculas",fechaNacimiento())).append(", ");
				if(!compareciente.getTipoCompareciente().getDsnombre().toUpperCase().equals(TIPO_COMPARECIENTE_TESTADOR)){
					identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas",estadoCivilConGenero())).append(", con domicilio en ");
				}else{
					identificacion.append(" con domicilio en ");
				}
				identificacion.append(tokenTransformer.evaluaFuncionVariable("texto_con_numeros",domicilio())).append(", ");
				identificacion.append(tokenTransformer.evaluaFuncionVariable("minusculas",empleo()));
				
				if(!compareciente.getTipoCompareciente().getDsnombre().toUpperCase().equals(TIPO_COMPARECIENTE_TESTADOR)){
					identificacion.append(", con Registro Federal de Contribuyentes número \"");
					identificacion.append(tokenTransformer.evaluaFuncionVariable("letras, mayusculas",rfc())).append("\" ");
				}else{
					identificacion.append(" ");
				}
				
				
				identificacion.append("y con Clave Única de Registro de Población número \"");
				identificacion.append(tokenTransformer.evaluaFuncionVariable("letras, mayusculas",curp())).append("\".<br/>");			
			}
			return identificacion.toString();
		}catch(NotariaException e){
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public String unRepresentante(){
		ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
		try{
			List<Compareciente> representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
			if (representantes!=null && representantes.size()<=1){
				return "true";
			} else {
				return "false";
			}				
		}catch(NotariaException e){
			System.out.printf("=====> compareciente:esRepresentada "+e.getMessage());
			e.printStackTrace(System.out);
			return "false";
		}
	}
	
	public String domicilio(){
		return compareciente.getDomicilio().getDsdircompleta();		
	}
	
	public String rfc(){
		if(compareciente.getPersona().getDsrfc()!=null){
			return compareciente.getPersona().getDsrfc();
		}else{
			return "";
		}
		
	}
	
	public String curp(){
		if(compareciente.getPersona().getDscurp()!=null){
			return compareciente.getPersona().getDscurp();
		}else{
			return "";
		}
	}
	
	public String domicilioCalle(){
		return compareciente.getPersona().getDomicilio().getDscalle();
	}
	
	public String domicilioNumeroExterior(){
		return compareciente.getPersona().getDomicilio().getDsnumext();
	}
	
	public String domicilioNumeroInterior(){
		return compareciente.getPersona().getDomicilio().getDsnumint();
	}
	
	public String domicilioColonia(){
		return compareciente.getPersona().getDomicilio().getDscolonia();
	}
	
	public String domicilioMunicipio(){
		return compareciente.getPersona().getDomicilio().getDsmunicipio();
	}
	
	public String domicilioCodigoPostal(){
		return compareciente.getPersona().getDomicilio().getDscodpos();
	}
	
	public String domicilioEntidadFederativa(){
		return compareciente.getPersona().getDomicilio().getDsestado();
	}
	
	public String contactoTelefono(){
		return compareciente.getContacto().getDstelefono();
	}

	public String certificadoExtranjeria(){
		if(compareciente.getPersona().getTipopersona().getDselemento().toUpperCase().equals(TIPO_PERSONA_MORAL)){
			StringBuilder respuesta = new StringBuilder();
			ComparecienteRepresentanteBo representanteBO = new ComparecienteRepresentanteBoImpl();
			List<Compareciente> representantes;
			boolean plural = false;
			try {
				representantes = representanteBO.findByRepresentadoId(compareciente.getIdcompareciente());
				
				if(representantes.size()>1){
					plural = true;
				}
			} catch (NotariaException e) {
				e.printStackTrace();
				
			}
			
			if(compareciente.getPersona().getIsextranjero()){
				respuesta.append("#E.- ");
				if(!compareciente.getPersona().getIscapitalextranjero()){
					if(!plural){
						respuesta.append("Que el compareciente declara ");
					}else{
						respuesta.append("Que los comparecientes declaran ");
					}
					respuesta.append("de manera expresa y bajo protesta de decir verdad, que en ");
					respuesta.append(compareciente.getPersona().getDsnombrecompleto());
					respuesta.append(", no participa ningún tipo de inversión extranjera.");
				}else{
					if(compareciente.getIsavisoextranjero()){	
						if(!plural){
							respuesta.append("El compareciente me acredita que ");

						}else{
							respuesta.append("Los comparecientes me acreditan que ");

						}
						respuesta.append(compareciente.getPersona().getDsnombrecompleto());
						respuesta.append(", se encuentra inscrita en el Registro Nacional de Inversiones Extranjeras con el documento que agrego al apéndice de esta escritura con la \"LETRA#\".");
					}else{
						if(!plural){
							respuesta.append("Que advertí a el compareciente ");
						}else{
							respuesta.append("Que advertí a los comparecientes ");
						}
						respuesta.append("que, en virtud de no haberme acreditado la inscripción de");
						respuesta.append(compareciente.getPersona().getDsnombrecompleto());
						respuesta.append(" en el Registro Nacional de Inversiones Extranjeras, procederé a dar el aviso a que se refiere ");
						respuesta.append("el artículo treinta y cuatro de la Ley de Inversión Extranjera.");
					}
				}
			}
		
			
			return respuesta.toString();
			
		}else{
			return "";
		}
		
	}
	
	public String contactoCorreoElectronico(){
		return compareciente.getContacto().getDscorreoelectronico();
	}
	
	public Compareciente getCompareciente(){
		return this.compareciente;
	}
	

	
	
	public void setCompareciente(Compareciente compareciente){
		this.compareciente = compareciente;
	}
	
	public List<String> reflect() throws NotariaException {
		Method[] metodos = this.getClass().getMethods();
		List<String> listado = new ArrayList<String>();
		for(Method metodo:metodos){
			if(!(metodo.getName().startsWith("get")||metodo.getName().startsWith("set")) 
					&& !metodo.getName().equals("reflect")
					&& !metodo.getName().equals("wait")
					&& !metodo.getName().equals("equals")
					&& !metodo.getName().equals("toString")
					&& !metodo.getName().equals("hashCode")
					&& !metodo.getName().equals("notify")
					&& !metodo.getName().equals("notifyAll")){
				listado.add(metodo.getName());
			}			
		}
		return listado;		
	}
	
}
