package enums;

public enum Respuestas {
	
	
	REGISTRO_EXITOSO("Se registro correctamente el objeto"),
	REGISTRO_FALLO("Fallo al generar el registro"),
	ACTUALIZACION_EXITOSO("Se actualizo correctamente el objeto"),
	ACTUALIZACION_FALLO("Fallo al actualizar el objeto"),
	LISTA_EXITOSO("Se obtuvo el listado correctamente"),
	LISTA_FALLO("Algo malo paso al obtener el listado"),
	BORRADO_EXITOSO("Se elimino correctamente el objeto"),
	BORRADO_FALLO("Ocurrio un error al elimnar el objeto"),
	BORRADO_FALLO_HIJOS("El objeto tiene referencias asociadas y no es posible eliminarlo");
	
	private final String texto;
	
	private Respuestas(String texto){
		this.texto= texto;
	}
	
	 public boolean equalsTexto(String texto) {
	        return (texto == null) ? false : this.texto.equals(texto);
	    }

	    public String toString() {
	       return this.texto;
	    }
	    
	    
	
}
