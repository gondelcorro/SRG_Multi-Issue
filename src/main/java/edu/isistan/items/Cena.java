package edu.isistan.items;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("cena")
public class Cena implements IItem{

	private String nombre;
	
	@Override
	public List<Cena> loadItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
