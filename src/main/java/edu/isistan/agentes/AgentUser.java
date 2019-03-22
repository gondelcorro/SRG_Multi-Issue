package edu.isistan.agentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import edu.isistan.items.IItem;
import edu.isistan.util.Utilidades;

public class AgentUser {

	private String nombre;
	private List<IItem> listaItems;
	private List<Utilidades> listaUtilidadesOriginal; //Contiene todos los items con su utilidad
	private List<Utilidades> listaUtilidadesTemporal; //Varia a medida q se realizan las propuestas
	private IItem propuestaActual;
	private float utilidadActual;
	private Float utilidadDeReserva;//min aceptable. Definido como wrapper p/poder implementar comparable
	private List<Utilidades> listaUtilidadesYaPropuestas;
	

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
		listaUtilidadesOriginal = new ArrayList<>();
		listaUtilidadesTemporal = new ArrayList<>();
		for (IItem item : listaItems) {
			Utilidades utilidad = new Utilidades();
			utilidad.setItem(item);
			float util = (float) Math.abs(Math.random() * 5);
            int aux = (int) (util * 1000); //me quedo con 3 decimales
            float result = aux / 1000f;
            utilidad.setUtilidad(result);
			listaUtilidadesOriginal.add(utilidad);
		}
		Collections.sort(listaUtilidadesOriginal, Comparator.comparing(Utilidades::getUtilidad));// ordenar usando comparator, segun utilidad.
		Collections.reverse(listaUtilidadesOriginal);
		listaUtilidadesTemporal = listaUtilidadesOriginal.stream().collect(Collectors.toList()); // copiar lista a otra en java 8 con stream
	}
	
	
    public IItem elegirPropuesta() { // La eleccion es tomar la 1era pelicula, la de mayor utilidad (ya q la lista esta ordenada de mayor a menor)
        if (!this.listaUtilidadesTemporal.isEmpty()) {
            listaUtilidadesYaPropuestas = new ArrayList();
            Utilidades utilidad = this.listaUtilidadesTemporal.remove(0); //la saco d la lista y guardo el item y su utilidad
            listaUtilidadesYaPropuestas.add(utilidad); //guarda las propuestas q van realizando los agentes
            this.propuestaActual = utilidad.getItem();
            this.utilidadActual = utilidad.getUtilidad();
            return propuestaActual;
            }
        return null;
    }

    public boolean aceptaPropuesta(IItem item) {// se acepta si la utilidad del item prop es >= q la utilidad de la prop actual
        float utilidadItemPropuesta = this.getUtilidad(item);
        System.out.println("\n" + this.getNombre() + ": UItemPropuesto(" + item.getNombre() + ")= " + utilidadItemPropuesta + " | UMiPropuesta(" + propuestaActual.getNombre() + ")=" + this.utilidadActual);
        return (utilidadItemPropuesta >= this.utilidadActual);
    }

    //sobreescribir equals & hashcode
    public float getUtilidad(IItem item) {
        float utilidad = 0;
        for (Utilidades u : listaUtilidadesOriginal) {
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<IItem> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<IItem> listaItems) {
		this.listaItems = listaItems;
	}

	public List<Utilidades> getListaUtilidadesOriginal() {
		return listaUtilidadesOriginal;
	}

	public void setListaUtilidadesOriginal(List<Utilidades> listaUtilidadesOriginal) {
		this.listaUtilidadesOriginal = listaUtilidadesOriginal;
	}

	public List<Utilidades> getListaUtilidadesTemporal() {
		return listaUtilidadesTemporal;
	}

	public void setListaUtilidadesTemporal(List<Utilidades> listaUtilidadesTemporal) {
		this.listaUtilidadesTemporal = listaUtilidadesTemporal;
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

	public List<Utilidades> getListaUtilidadesYaPropuestas() {
		return listaUtilidadesYaPropuestas;
	}

	public void setListaUtilidadesYaPropuestas(List<Utilidades> listaUtilidadesYaPropuestas) {
		this.listaUtilidadesYaPropuestas = listaUtilidadesYaPropuestas;
	}

}
