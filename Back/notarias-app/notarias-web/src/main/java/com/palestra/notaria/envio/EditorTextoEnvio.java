package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.EditorTexto;

public class EditorTextoEnvio extends GenericEnvio {

	private EditorTexto editorTexto=null;
	
	private ArrayList<EditorTexto> editorTextoList=null;

	public EditorTexto getEditorTexto() {
		return editorTexto;
	}

	public void setEditorTexto(EditorTexto editorTexto) {
		this.editorTexto = editorTexto;
	}

	public ArrayList<EditorTexto> getEditorTextoList() {
		return editorTextoList;
	}

	public void setEditorTextoList(ArrayList<EditorTexto> editorTextoList) {
		this.editorTextoList = editorTextoList;
	}
}
