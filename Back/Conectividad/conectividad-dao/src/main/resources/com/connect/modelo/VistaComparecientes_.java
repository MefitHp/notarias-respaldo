package com.connect.modelo;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-18T13:44:21.720-0500")
@StaticMetamodel(VistaComparecientes.class)
public class VistaComparecientes_ {
	public static volatile SingularAttribute<VistaComparecientes, Double> idcompareciente;
	public static volatile SingularAttribute<VistaComparecientes, VistaExpediente> vistaExpediente;
	public static volatile SingularAttribute<VistaComparecientes, String> operacion;
	public static volatile SingularAttribute<VistaComparecientes, Double> libro;
	public static volatile SingularAttribute<VistaComparecientes, Double> escritura;
	public static volatile SingularAttribute<VistaComparecientes, String> folios;
	public static volatile SingularAttribute<VistaComparecientes, Date> fecha;
	public static volatile SingularAttribute<VistaComparecientes, String> estado;
	public static volatile SingularAttribute<VistaComparecientes, String> nombre;
	public static volatile SingularAttribute<VistaComparecientes, String> rol;
}
