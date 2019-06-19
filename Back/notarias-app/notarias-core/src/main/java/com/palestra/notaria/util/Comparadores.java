package com.palestra.notaria.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Comparadores<T> {
	  
	  void sortArray(String propertyName,List<Personita> lista)
	  {
		
	    Collections.sort(lista);
	    for (Object obj : lista) {
	      System.out.println((T)obj.toString());
	    }
	  }
	  
	  
	  
	  public static void main(String[] args) {
		  Comparadores<Personita> comparador = new Comparadores<Personita>();
		  List<Personita> milista = new ArrayList<Personita>();
		  Personita persona1 = new Personita("juan","perez");
		  Personita persona2 = new Personita("alberto","vazquez");
		  Personita persona3 = new Personita("alan","lopez");
		  Personita persona4 = new Personita("jose","hernandez");
		  Personita persona5 = new Personita("beto","cuevas");
		  milista.add(persona1);
		  milista.add(persona2);
		  milista.add(persona3);
		  milista.add(persona4);
		  milista.add(persona5);
		  comparador.sortArray("nombre", milista);
	  }
	}

class Personita implements Comparator<Personita>, Comparable<Personita>{
	  private String nombre;
	  private String apellido;
	public Personita(String nombre, String apellido) {
		// TODO Auto-generated constructor stub
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	@Override
	public int compare(Personita o1, Personita o2) {
		// TODO Auto-generated method stub
        return o2.compareTo(o1);
	}
	@Override
	public int compareTo(Personita o) {
		return this.getNombre().compareTo(o.getNombre());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getNombre();
	}
	
}
