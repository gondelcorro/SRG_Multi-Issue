package edu.isistan.agentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.isistan.items.IItem;
import edu.isistan.protocolos.IProtocolo;
import edu.isistan.protocolos.ProtocoloDeProyeccionSecuencial;

public class AgentMediator {
	
	private int num_participantes;
	private IItem itemANegociar;
	private String rutaCatalogo;
	private List<AgentUser> listaAgentes;
	
	public AgentMediator(int num_participantes, IItem itemSelect, String rutaCatalogo) {
		
		this.num_participantes = num_participantes;
		this.itemANegociar = itemSelect;
		this.rutaCatalogo = rutaCatalogo;
		
		crearGrupo();
		fijarOrdenAgentes();
		IProtocolo protocolo = new ProtocoloDeProyeccionSecuencial();
		protocolo.ejecutarProtocolo(listaAgentes);
	}
	
	private void crearGrupo() {
		//el grupo es una lista de agentes
		//cuando se crea el grupo, se setea los datos iniciales p/c agente: items y utilidades
		listaAgentes = new ArrayList<>();
		int i =1;
		while(i<=num_participantes) {
			AgentUser agentUser = new AgentUser();
			agentUser.setNombre("Agente" + i);
			agentUser.loadCatalogo(itemANegociar, rutaCatalogo); //los agentes misma lista de items a negociar
			agentUser.loadUtilidades();
			agentUser.setUtilidadDeReserva(generarRandom(2.5f, 1.0f));// random entre 2.5 y 3.5
			listaAgentes.add(agentUser);
			i++;
		}
		
		for(AgentUser agente : listaAgentes) {
			System.out.println("Los items del agente " + agente.getNombre() + " son:");
			/*for(Object item : agente.getListaItems()) {
				System.out.print( ((IItem)item).getNombre() + " | ");
			}*/
			agente.getListaUtilidades().forEach(item -> System.out.print(item.getItem().getNombre() + " - " + item.getUtilidad() + " | "));
			System.out.println("");
			System.out.println("Utilidad de reserva: " + agente.getUtilidadDeReserva() + "\n");
			
		}
	}
	
	private void fijarOrdenAgentes() {
		//Va a proponer primero el agente con mayor reservaDeUtilidad (no arbitrario)
		System.out.println("Orden de propuesta de los agentes:" );
		Collections.sort(listaAgentes);
		Collections.reverse(listaAgentes);
		listaAgentes.forEach(agent -> System.out.println(agent.getNombre() + " | Ur (" + agent.getUtilidadDeReserva() + ")"));
	}

	private float generarRandom(float min,float max) {
		float random = (float) ( Math.abs(Math.random() * max) + min); 
        int aux = (int) (random * 1000); //me quedo con 3 decimales
        float result = aux / 1000f;
        return result;
	}

	public List<AgentUser> getListaAgentes() {
		return listaAgentes;
	}

	public void setListaAgentes(List<AgentUser> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}
	
}
