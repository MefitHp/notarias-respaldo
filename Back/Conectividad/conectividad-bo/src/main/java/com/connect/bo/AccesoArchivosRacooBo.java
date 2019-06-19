package com.connect.bo;

import java.io.IOException;
import java.util.List;

public interface AccesoArchivosRacooBo {

	public List<String> listaArchivos(String carpeta,String rutaRacoo) throws IOException;

}
