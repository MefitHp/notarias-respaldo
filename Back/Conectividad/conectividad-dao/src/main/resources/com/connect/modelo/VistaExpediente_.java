package com.connect.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-18T13:44:21.903-0500")
@StaticMetamodel(VistaExpediente.class)
public class VistaExpediente_ {
	public static volatile SingularAttribute<VistaExpediente, String> expediente;
	public static volatile SingularAttribute<VistaExpediente, Double> escritura;
	public static volatile SingularAttribute<VistaExpediente, String> ope;
	public static volatile SingularAttribute<VistaExpediente, String> fechaFirma;
	public static volatile SingularAttribute<VistaExpediente, String> iniciales;
	public static volatile SingularAttribute<VistaExpediente, String> ruta;
	public static volatile ListAttribute<VistaExpediente, VistaComparecientes> comparecientes;
}
