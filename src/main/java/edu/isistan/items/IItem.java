package edu.isistan.items;

import java.util.List;

public interface IItem<T> {
	
	
	public List<T> loadItems();
	public String getNombre();
	public void setNombre(String nombre);

}
