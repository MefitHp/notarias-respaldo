package com.palestra.notarias.constantes;

import java.io.File;

public class DirectorioLocal {

	/**
	 * Directorio donde se depositan los documentos generados
	 *  linux = user.home/notaria/documentos
	 *  windows = user.home\notaria\documentos
	 **/
	public static final String FILE_PATH = System.getProperty("user.home")
			+ File.separator + "notaria" + File.separator + "documentos"
			+ File.separator;

}
