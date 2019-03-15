package edu.isistan.agents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import edu.isistan.items.IItem;
import edu.isistan.util.Utilidades;

@Component
@Scope("prototype")
public class AgentUser {
	
	private IItem itemANegociar;
	private String nombreUsuario;
	private List<IItem> listaItems;
	private List<Utilidades> listaUtilidades;
	private IItem propuestaActual;
	private float utilidadActual;
	
	public AgentUser() {
	}
	
	public void loadItems(){
		
		this.listaItems = new ArrayList<>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			archivo = new ClassPathResource("catalogoItems/CatalogoPeliculas.txt").getFile();
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				this.itemANegociar.setNombre(linea);
				listaItems.add(getItemANegociar());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	public void loadUtilidades() {
		listaUtilidades = new ArrayList<>();
		for(IItem item : listaItems) {
			Utilidades utilidad = new Utilidades();
			utilidad.setItem(item);
			float util = (float) Math.abs(Math.random() * 5);
			int aux = (int) util * 1000;
			float result = aux /1000f; //me quedo con 2 decimales
			utilidad.setUtilidad(result);
			listaUtilidades.add(utilidad);
		}
		Collections.sort(listaUtilidades);//utilidades debe implementar Comparable
		Collections.reverse(listaUtilidades);
		listaUtilidades.forEach(item -> System.out.println(item.getItem().getNombre() + " - " + item.getUtilidad() + " | "));
	}*/


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public List<IItem> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<IItem> listaItems) {
		this.listaItems = listaItems;
	}

	public List<Utilidades> getListaUtilidades() {
		return listaUtilidades;
	}

	public void setListaUtilidades(List<Utilidades> listaUtilidades) {
		this.listaUtilidades = listaUtilidades;
	}

	public IItem getPropuestaActual() {
		return propuestaActual;
	}

	public void setPropuestaActual(IItem propuestaActual) {
		this.propuestaActual = propuestaActual;
	}

	public float getUtilidadActual() {
		return utilidadActual;
	}

	public void setUtilidadActual(float utilidadActual) {
		this.utilidadActual = utilidadActual;
	}

	public IItem getItemANegociar() {
		return itemANegociar;
	}

	public void setItemANegociar(IItem itemANegociar) {
		this.itemANegociar = itemANegociar;
	}

}
