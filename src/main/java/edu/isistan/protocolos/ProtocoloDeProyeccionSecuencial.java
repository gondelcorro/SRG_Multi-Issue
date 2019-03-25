package edu.isistan.protocolos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

import edu.isistan.agentes.AgentUser;
import edu.isistan.estrategiasConcesion.IEstrategiaConcesion;
import edu.isistan.items.IItem;

public class ProtocoloDeProyeccionSecuencial implements IProtocolo{
	
	List<AgentUser> listaAgentes;
	IEstrategiaConcesion estrategiaConcesion;
	HashMap<AgentUser, IItem>  propuestasIniciales;
	AgentUser agenteQuePropone;
	boolean acuerdo = false;
	boolean primeraVez = true;

	@Override
	public void ejecutarProtocolo(List<AgentUser> listaAgentes) {
		
		this.listaAgentes = listaAgentes;
		int num_ronda = 1;
		StopWatch timer = new StopWatch();
		timer.start();
		
		//PASO 1: RECOLECTAR PROPUESTAS INICIALES
		propuestasIniciales = recolectarPropIniciales();
		System.out.println("Propuestas iniciales de los agentes:");
		propuestasIniciales.forEach((agente, item) -> System.out.println(agente.getNombre() + ": " + item.getNombre() + " - U(" + agente.getUtilidad(item) + ")"));
		
		//PASO 2: FIJAR ORDEN EN QUE PROPONEN LOS AGENTES
		fijarOrdenAgentes();

		//PASO 3.A: NEGOCIAR HASTA ALCANZAR ACUERDO
		while(!acuerdo) {
			System.out.println("\n***** Ronda de Negociación N° " + num_ronda + " *****");
			int turnoAgente = 0;
			while(turnoAgente < listaAgentes.size()) { //mientras no termine la ronda
				AgentUser agenteQuePropone = listaAgentes.get(turnoAgente);
				IItem itemAProponer = estrategiaConcesion.ejecutarEstrategia(listaAgentes, agenteQuePropone, primeraVez);
				//PASO 3: CHEQUEAR SI SE ACEPTA LA PROPUESTA
				acuerdo = chequearAcuerdo(agenteQuePropone, itemAProponer);
				//Si no hay un acuerdo con la propuesta inicial, la estrategia de concesion debe devolver la cant
				//que debe conceder el siguiente agente segun el orden fijado (hacer rondas)
				//despues debo verificar si restando ese valor de utilidad no supero la Ur
				//entonces tomo la 
				if(acuerdo) {
					System.out.println("\n*** La propuesta es aceptada. Fin de la negociacion. ***");
					break;
				}else {
					System.out.println("\nNo hay acuerdo en el propuesta del agente " + agenteQuePropone.getNombre());
					turnoAgente = turnoAgente + 1;
				}
			}
			primeraVez = false;
			num_ronda ++;
		}
		timer.stop();
		System.out.println("N° de rondas realizadas: " + (num_ronda - 1));
		System.out.println("Tiempo que tardo la negocición: " + timer.getTime() + " ms.");
	}
	
	private HashMap<AgentUser, IItem> recolectarPropIniciales() {
		propuestasIniciales = new HashMap<>();
		for(AgentUser agente : listaAgentes) {
			IItem item = agente.elegirPropuesta();
			propuestasIniciales.put(agente, item);
		}
		return propuestasIniciales;
	}
	
	private void fijarOrdenAgentes() {
		//Va a proponer primero el agente con mayor reservaDeUtilidad (no arbitrario)
		Collections.sort(listaAgentes, Comparator.comparing(AgentUser::getUtilidadDeReserva));
		Collections.reverse(listaAgentes);
		System.out.println("\nOrden de propuesta de los agentes:" );
		listaAgentes.forEach(agent -> System.out.println(agent.getNombre() + " | Ur (" + agent.getUtilidadDeReserva() + ")"));
	}
	
	private boolean chequearAcuerdo(AgentUser agenteQuePropone, IItem itemAProponer) {
		System.out.println("\nEl agente " + agenteQuePropone.getNombre() + ": propone " + agenteQuePropone.getPropuestaActual().getNombre() + " U(" + agenteQuePropone.getUtilidad(itemAProponer) + ")");
		List<Boolean> listaRespuestas = new ArrayList<>();
		Boolean respuestaAgente;
		for(AgentUser agente : listaAgentes) {
			if(!agenteQuePropone.equals(agente)) {
				respuestaAgente = agente.aceptaPropuesta(agenteQuePropone.getPropuestaActual());
				if(respuestaAgente) {
					System.out.println("El agente " + agente.getNombre() + " acepta la propuesta " + agenteQuePropone.getPropuestaActual().getNombre());
				}else {
					System.out.println("El agente " + agente.getNombre() + " rechaza la propuesta " + agenteQuePropone.getPropuestaActual().getNombre());
				}
				listaRespuestas.add(respuestaAgente);
			}
		}
		for(Boolean respuesta : listaRespuestas) {
			if(!respuesta) { //si al menos un agente rechaza la propuesta -> no hay acuerdo
				acuerdo = false;
				break;
			}
			acuerdo = true;
		}
		
		return acuerdo;
		/*
		propuestasIniciales.forEach((agente, item) -> {
			if(!agenteQuePropone.equals(agente)) {
				acuerdo = agente.aceptaPropuesta(agenteQuePropone.getPropuestaActual());
				if(acuerdo) {
					System.out.println("El agente " + agente.getNombre() + " acepta la propuesta " + item.getNombre());
				}else {
					System.out.println("El agente " + agente.getNombre() + " rechaza la propuesta " + item.getNombre());
				}
			}
		});*/
	}

	@Override
	public void setEstrategiaConcesion(IEstrategiaConcesion estretegiaConcesion) {
		this.estrategiaConcesion = estretegiaConcesion;
	}

}
