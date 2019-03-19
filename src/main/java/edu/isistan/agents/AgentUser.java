package edu.isistan.agents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

import edu.isistan.items.IItem;
import edu.isistan.util.Utilidades;

public class AgentUser {

	private String nombreUsuario;
	private List<IItem> listaItems;
	private List<Utilidades> listaUtilidades;
	private IItem propuestaActual;
	private float utilidadActual;

	public AgentUser() {
	}

	public void loadCatalogo(IItem item, String rutaCatalogo) {
		listaItems = new ArrayList<>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			archivo = new ClassPathResource(rutaCatalogo).getFile();
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				IItem nuevoItem = item.getClass().newInstance();
				nuevoItem.setNombre(linea);
				listaItems.add(nuevoItem);
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

	public void loadUtilidades() {
		listaUtilidades = new ArrayList<>();
		for (IItem item : listaItems) {
			Utilidades utilidad = new Utilidades();
			utilidad.setItem(item);
			float util = (float) Math.abs(Math.random() * 5);
            int aux = (int) (util * 1000); //me quedo con 3 decimales
            float result = aux / 1000f;
            utilidad.setUtilidad(result);
			listaUtilidades.add(utilidad);
		}
		Collections.sort(listaUtilidades);// la clase utilidades debe implementar Comparable para poder ordenar
		Collections.reverse(listaUtilidades);
	}
	
	/*
    public Movie elegirPropuesta() { // La eleccion es tomar la 1era pelicula
        if (!this.listaUtilidades.isEmpty()) {
            Utilidades utilidad = this.listaUtilidades.remove(0); //la saco d la lista y guardo la movie y su utilidad
            propuestaActual = utilidad.getMovie();
            utilidadActual = utilidad.getUtilidad();
            return propuestaActual;
        }
        return null;
    }

    public boolean aceptaPropuesta(Movie peli) {// se acepta si la utilidad d la peli prop es > q la prop actual
        float utilidadPeliPropuesta = getUtilidad(peli);
        System.out.println("Agente " + this.getLocalName() + ": UPeliPropuesta(" + peli.getName() + ")= " + utilidadPeliPropuesta + " | UMiPropuesta(" + propuestaActual.getName() + ")=" + this.utilidadActual);
        return (utilidadPeliPropuesta >= this.utilidadActual); //retorna true (acepta) si la utilidad de peliPropuesta es mayor o igual a la utilidad de mi propuesta
    }

    public float getUtilidad(Movie movie) {
        float utilidad = 0;
        for (Utilidades u : listaUtilidades) {
            if (u.getMovie().equals(movie)) {
                utilidad = u.getUtilidad();
                break;
            }
        }
        return utilidad;
    }
	*/
	
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

}
