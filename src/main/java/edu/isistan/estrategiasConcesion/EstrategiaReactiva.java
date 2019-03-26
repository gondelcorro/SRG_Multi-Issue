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
		Utilidades ultimaPropuesta = null;
		Utilidades penultimaPropuesta = null; 
		boolean bandera = false;
		
		if(primeraVez) {
			propQSeDebeRealizar = agenteQuePropone.getPropuestaActual();
		}else {
			for(AgentUser agente : listaAgentes) {
				List<Utilidades> propuestasRealizadas = agente.getListaUtilidadesYaPropuestas();
				if(propuestasRealizadas.size() >= 2) {
					ultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-1); 
					penultimaPropuesta = propuestasRealizadas.get(propuestasRealizadas.size()-2);
					cantAConceder = (penultimaPropuesta.getUtilidad() - ultimaPropuesta.getUtilidad()); // actua como cola, la penultima tiene mayor utilidad
					concesionAgentes.put(agente, cantAConceder);
				}else {
					propQSeDebeRealizar = agenteQuePropone.elegirPropuesta();
					bandera = true;
					break;
				}
			}
			if(!bandera) {
				propQSeDebeRealizar = obtenerPropuesta(ultimaPropuesta); //Aplicando la estrategia
			}
		}
		return propQSeDebeRealizar;
	}
	
	private IItem obtenerPropuesta(Utilidades ultimaPropuesta) {
		//obtener la key con el valor min en un hashmap con java 8
		AgentUser agConLaMenorConcesion =  (AgentUser) Collections.min(concesionAgentes.entrySet(),  Map.Entry.comparingByValue()).getKey(); 
		float minConcesion = concesionAgentes.get(agConLaMenorConcesion);
		IItem item = null;
		System.out.println("Min concesion:" + minConcesion);
		//De la lista de propuestas orgininal del agente q propone, q obtenga la 1er prop q tenga un valor menor o igual al minConcesion
		//agenteQuePropone.getPropuestaActual()
		for(Utilidades utilidad : agenteQuePropone.getListaUtilidadesOriginal()) {
			item = agenteQuePropone.elegirPropuesta();
			
			//if((agenteQuePropone.getUtilidad(item) - minConcesion) <=  utilidad.getUtilidad()) {
			
			if((ultimaPropuesta.getUtilidad()-minConcesion) <= agenteQuePropone.getUtilidad(item) ) {
				//invocar a elegir propuesta para q se actualice la listaTemporal
				//item;
				break;
			}
		}
		return item;
	}

}
