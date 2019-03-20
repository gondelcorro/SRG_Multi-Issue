package edu.isistan.agentes;

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

public class AgentUser implements Comparable<AgentUser>{

	private String nombre;
	private List<IItem> listaItems;
	private List<Utilidades> listaUtilidades;
	private IItem propuestaActual;
	private float utilidadActual;
	private Float utilidadDeReserva;//min aceptable. Definido como wrapper p/poder implementar comparable

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
	
	
    public IItem elegirPropuesta() { // La eleccion es tomar la 1era pelicula, la de mayor utilidad (ya q la lista esta ordenada de mayor a menor)
        if (!this.listaUtilidades.isEmpty()) {
            Utilidades utilidad = this.listaUtilidades.remove(0); //la saco d la lista y guardo el item y su utilidad
            this.propuestaActual = utilidad.getItem();
            this.utilidadActual = utilidad.getUtilidad();
            return propuestaActual;
        }
        return null;
    }

    public boolean aceptaPropuesta(IItem item) {// se acepta si la utilidad del item prop es > q la utilidad de la prop actual
        float utilidadPeliPropuesta = getUtilidad(item);
        System.out.println("Agente " + this.getNombre() + ": UPeliPropuesta(" + item.getNombre() + ")= " + utilidadPeliPropuesta + " | UMiPropuesta(" + propuestaActual.getNombre() + ")=" + this.utilidadActual);
        return (utilidadPeliPropuesta >= this.utilidadActual); //retorna true (acepta) si la utilidad de peliPropuesta es mayor o igual a la utilidad de mi propuesta
    }

    
    public float getUtilidad(IItem item) {
        float utilidad = 0;
        for (Utilidades u : listaUtilidades) {
            if (u.getItem().equals(item)) {
                utilidad = u.getUtilidad();
                break;
            }
        }
        return utilidad;
    }
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreUsuario) {
		this.nombre = nombreUsuario;
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

	public Float getUtilidadDeReserva() {
		return utilidadDeReserva;
	}

	public void setUtilidadDeReserva(Float utilidadDeReserva) {
		this.utilidadDeReserva = utilidadDeReserva;
	}

	@Override
	public int compareTo(AgentUser ag) {
		if (getUtilidadDeReserva() == null  || ag.getUtilidadDeReserva() == null) {
            return 0;
        }
        return getUtilidadDeReserva().compareTo(ag.getUtilidadDeReserva());
	}

}
