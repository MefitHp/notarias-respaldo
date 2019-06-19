package com.palestra.notarias.service;

import java.util.Date;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;

public class GeneradorDocumentoServiceTest {

	public static void main(String args[]) {

		GeneradorDocumentoServiceTest gsrvTest = new GeneradorDocumentoServiceTest();
		String idExpediente = "0bd358dbe28b564c2e0ffcb83e49c4ba";
		Expediente expediente = new Expediente();
		expediente.setIdexpediente(idExpediente);
		Escritura escritura = new Escritura();
		escritura.setIdescritura("1679ecc5f5f76a7251e5a42314c60684");
		escritura.setExpediente(expediente);

		gsrvTest.versionInicialDocParcial(escritura.getIdescritura());
//		gsrvTest.generaMasterPorEscritura(escritura);
	}

	public void generaMasterPorEscritura(Escritura escritura) throws NotariaException {
		GeneradorDocumentoService hTMLToDOCGenerator = new GeneradorDocumentoService();
		Boolean b = hTMLToDOCGenerator.generaMasterPorEscritura(escritura,
				"texto de prueba " + Math.random() + " fecha: " + new Date(), "session");
		if (b) {
			System.out.println("Se registro correctamente.");
		} else {
			System.out.println("No se pudo generar el documento");
		}
	}

	public void versionInicialDocParcial(String idEscritura) {
		GeneradorDocumentoService hTMLToDOCGenerator = new GeneradorDocumentoService();
		try {
			hTMLToDOCGenerator.versionInicialDocParcial(idEscritura);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
