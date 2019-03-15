package edu.isistan.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.isistan.items.Cena;
import edu.isistan.items.IItem;
import edu.isistan.items.Pelicula;

public class AgentMediator {
	
	@Autowired
	private AgentUser agentUser;
	
	private List<AgentUser> listaAgentes;
	
	public AgentMediator() {
		System.out.println("\n********** - ********** - ********** - *********");
		System.out.println("BIENVENIDO AL SISTEMA DE RECOMENDACION A GRUPOS");
		System.out.println("\nHola, soy el encargado de la negociacion");
		System.out.println("Por favor, ingresá el numero de participantes:");
		
		Scanner sc = new Scanner(System.in);
		int num_participantes = sc.nextInt();
		
		System.out.println("\nPor favor, selecciona el item de negociacion:");
		System.out.println("Opcion 1: Pelicula");
		System.out.println("Opcion 2: Cena");
		
		int opcion_select = sc.nextInt();
		
		switch (opcion_select) {
		case 1:
			System.out.println("Paquete de ofertas a negociar: Paquete 1 - Pelicula" );
			this.crearGrupo(num_participantes, opcion_select);
			System.out.println("*** FIN ***");
			break;
		
		case 2:
			System.out.println("Paquete de ofertas a negociar: Paquete 2 - Cena" );
			this.crearGrupo(num_participantes, opcion_select);
			System.out.println("*** FIN ***");
			break;

		default:
			System.out.println("La opcion ingresada no es válida. Ingrese una opcion nuevamente" );
		}
		
	}
	
	private void crearGrupo(int num_participantes, int opcion_select) {
		
		listaAgentes = new ArrayList<>();
		ApplicationContext context = new AnnotationConfigApplicationContext(AgentUser.class); 
		int i =1;
		while(i<=num_participantes) {
			
			AgentUser agentUser = context.getBean(AgentUser.class);
			if(opcion_select == 1) {
				agentUser.setItemANegociar(new Pelicula());
			}else {
				agentUser.setItemANegociar(new Cena());
			}
			agentUser.setNombreUsuario("Negociador" + i);
			agentUser.loadItems();
			listaAgentes.add(agentUser);
			i++;
		}
		for(AgentUser agente : listaAgentes) {
			System.out.println("Item a negociar del agente " + agente.getNombreUsuario() + ": " + agente.getItemANegociar().getClass());
			for(IItem item : agente.getListaItems()) {
				System.out.println(item.getNombre());
			}
		}
	}

	public AgentUser getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(AgentUser agentUser) {
		this.agentUser = agentUser;
	}

	public List<AgentUser> getListaAgentes() {
		return listaAgentes;
	}

	public void setListaAgentes(List<AgentUser> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}
	
}
