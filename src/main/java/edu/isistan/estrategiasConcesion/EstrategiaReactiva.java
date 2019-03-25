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
	boolean primeraVez;
	
	/*La estrategia consiste en que el agente conceda segun cuanto concenden los demas
	 *el agente concedera un valor igual al minimo de la diferencia entre las ultimas 2 propuestas 
	 *de todos los agentes (incluido el). En base a ese valor se tomara el nuevo item a proponer
	 */
	
	@Override
	public IItem ejecutarEstrategia(List<AgentUser> listaAgentes, AgentUser agenteQuePropone, boolean primeraVez) {

		this.listaAgentes = listaAgentes;
		this.agenteQuePropone = agenteQuePropone;
		this.primeraVez = primeraVez;
		this.concesionAgentes = new HashMap<>();
		float cantAConceder = 0f;
		Utilidades ultimaPropuesta;
		Utilidades penultimaPropuesta = null; 
		
		if(primeraVez) {
			propQSeDebeRealizar = agenteQuePropone.getPropuestaActual();
		}else {
			for(AgentUser agente : listaAgentes) {
				List<Utilidades> propuestasRealizadas = agente.getListaUtilidadesYaPropuestas();
				ultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-1); 
				ultimaPropuesta.setUtilidad(agente.getUtilidad(agente.getPropuestaActual()));
				try {
					// ojo aqui, la 2da vez q se propone sólo hay un elem en propuestasYaRealizadas, devuelve null
					penultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-2);
					cantAConceder = ultimaPropuesta.getUtilidad() - penultimaPropuesta.getUtilidad();
					concesionAgentes.put(agente, cantAConceder);
					propQSeDebeRealizar = obtenerPropuesta(); //Aplicando la estrategia
				} catch (Exception e) {
					//Elegir una propuesta mas de la listaOriginal (Esto es x unica vez hasta obtener las 2 prop minimas q se necesitan)
					propQSeDebeRealizar = agenteQuePropone.elegirPropuesta();
					break;
				}
			}
		}
		return propQSeDebeRealizar;
	}
	
	private IItem obtenerPropuesta() {
		//obtener la key con el valor min en un hashmap con java 8
		AgentUser agConLaMenorConcesion =  (AgentUser) Collections.min(concesionAgentes.entrySet(),  Map.Entry.comparingByValue()).getKey(); 
		float minConcesion = concesionAgentes.get(agConLaMenorConcesion);
		IItem item = null;
		System.out.println("Minima Concesion :" + minConcesion);
		//De la lista de propuestas orgininal del agente q propone, q obtenga la 1er prop q tenga un valor menor o igual al minConcesion
		for(Utilidades utilidad : agenteQuePropone.getListaUtilidadesOriginal()) {
			if(utilidad.getUtilidad() <= agenteQuePropone.getUtilidad(agenteQuePropone.getPropuestaActual()) - minConcesion) {
				item = utilidad.getItem();
				break;
			}
		}
		return item;
	}

}
