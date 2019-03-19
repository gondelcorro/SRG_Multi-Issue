package edu.isistan.items;

public class Pelicula implements IItem{
	
	private String nombre;
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
