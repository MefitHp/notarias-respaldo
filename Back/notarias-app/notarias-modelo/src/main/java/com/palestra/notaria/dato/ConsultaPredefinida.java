package com.palestra.notaria.dato;

public class ConsultaPredefinida{

	private String identificador;
	private String nombre;
	private String hql;
	private String campo;
	
	/***
	 * default constructor
	 */
	public ConsultaPredefinida() {}
	
	/***
	 * constructor establece los campos necesarios para que tenga sentido de vida el objeto
	 * @param identificador identificador o nombre de la consulta predefinida
	 * @param hql valor de la consulta que se realiza a la base de datos, en este caso se interpretará que el primer campo de la consulta será el valor
	 */
	public ConsultaPredefinida(String identificador, String hql){
		this.setIdentificador(identificador);
		this.setHql(hql);
	}
	
	/***
	 * constructor establece todos los campos del objeto, por ende tiene sentido su vida
	 * @param identificador identificador o nombre de la consulta predefinida
	 * @param hql valor de la consulta que se realiza a la base de datos
	 * @param campo field dentro de la proyección del query que se usará para mostrar los valores de la lista
	 */
	public ConsultaPredefinida(String identificador, String hql, String campo){
		this.setIdentificador(identificador);
		this.setHql(hql);
		this.setCampo(campo);
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	

}
