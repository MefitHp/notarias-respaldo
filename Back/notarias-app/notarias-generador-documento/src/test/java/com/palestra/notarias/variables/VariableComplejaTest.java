package com.palestra.notarias.variables;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.pojo.VariablesTokens;

public class VariableComplejaTest {

	public static void main(String args[]) {
		VariableComplejaTest vct = new VariableComplejaTest();
		//vct.obtenValorCompleto();
		vct.getValueComposeQueryByActo();
	}
	
	public void getValueComposeQueryByActo(){
		VariablesComplejaDefinicion varCompleja = new VariablesComplejaDefinicion();
		
		String idacto="69a0e03dbd7a7bf43381cad4834a45c7";
		String definicionFuncion = "tipo=VENDEDOR";
		String variableName = "compareciente_conyugue";
		
		String valor = varCompleja.getValueComposeQueryByActo(variableName, definicionFuncion, idacto);
		System.out.println("====> Valor: " + valor );
		
	}
	
	public void  obtenValorCompleto() throws NotariaException{
		VariablesComplejaDefinicion varCompleja = new VariablesComplejaDefinicion();

		VariablesTokens varToken = new VariablesTokens();
		varToken.setName("compareciente_compraciente_cantidad");
		varToken.setDeficionFuncion("tipo=VENDEDOR");
		//varToken.setName("notaria_asociada_notario");
		 
		FiltroVariables fvariables = new FiltroVariables();
		fvariables.setIdacto("5ce24c0bb221a975adf39215e1acdc61");
		//fvariables.setIdescritura("f84570f2354c09fadf7633e3515a84b4");

		List<VariablesTokens> listaVariables = new ArrayList<VariablesTokens>();
		listaVariables.add(varToken);

		for (VariablesTokens var : listaVariables) {
			// Trabajar con variables que sus valores sean nullos
			if (var.getValue() == null) {
					String returnValue = (String) varCompleja
							.obtenValorComplejo(var.getName(), fvariables, var.getDeficionFuncion());
					System.out.println("TEST ====>" + returnValue);
					var.setValue(returnValue);
			}
		}

	}

}
