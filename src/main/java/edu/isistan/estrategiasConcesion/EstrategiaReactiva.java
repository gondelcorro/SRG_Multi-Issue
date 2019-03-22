package edu.isistan.estrategiasConcesion;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.isistan.agentes.AgentUser;
import edu.isistan.items.IItem;
import edu.isistan.util.Utilidades;

public class EstrategiaReactiva implements IEstrategiaConcesion{

	List<AgentUser> listaAgentes;
	AgentUser agenteQuePropone;
	HashMap<AgentUser, Float> concesionAgentes;
	IItem propQSeDebeRealizar;
	
	
	@Override
	public IItem ejecutarEstrategia(List<AgentUser> listaAgentes, AgentUser agenteQuePropone) {

		this.listaAgentes = listaAgentes;
		this.agenteQuePropone = agenteQuePropone;
		this.concesionAgentes = new HashMap<>();
		float cantAConceder = 0f;
		Utilidades ultimaPropuesta;
		Utilidades penultimaPropuesta = null; 
		boolean primeraVez = false;
		
		for(AgentUser agente : listaAgentes) {
			List<Utilidades> propuestasRealizadas = agente.getListaUtilidadesYaPropuestas();
			ultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-1); 
			ultimaPropuesta.setUtilidad(agente.getUtilidad(agente.getPropuestaActual()));
			try {
				penultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-2);// ojo aqui, va a devolver null la 1era vez q se propone
				cantAConceder = ultimaPropuesta.getUtilidad() - penultimaPropuesta.getUtilidad();
				concesionAgentes.put(agente, cantAConceder);
			} catch (Exception e) {
				primeraVez = true;
			}
		}
		if(primeraVez) {
			propQSeDebeRealizar = agenteQuePropone.getPropuestaActual();
		}else {
			propQSeDebeRealizar = obtenerPropuesta();
		}
		return propQSeDebeRealizar;
	}
	
	
	private IItem obtenerPropuesta() {
		
		AgentUser agConLaMenorConcesion =  (AgentUser) Collections.min(concesionAgentes.entrySet(),  Map.Entry.comparingByValue()).getKey(); //obtener la key con el valor min en un hashmap con java 8
		float minConcesion = concesionAgentes.get(agConLaMenorConcesion);
		
		IItem item = null;
		//De la lista de propuestas orgininal del agente q propone, q obtenga la prop q tenga un valor menor o igual al minConcesion
		for(Utilidades utilidad : agenteQuePropone.getListaUtilidadesOriginal()) {
			if(utilidad.getUtilidad() <= agenteQuePropone.getUtilidad(agenteQuePropone.getPropuestaActual()) - minConcesion) {
				item = utilidad.getItem();
			}
		}
		return item;
	}

}
