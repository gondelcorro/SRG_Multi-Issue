package edu.isistan.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AgentMediator {
	
	private List<AgentUser> listaAgentes;
	
	public AgentMediator() {
		
		System.out.println("\n********** - ********** - ********** - *********");
		System.out.println("BIENVENIDO AL SISTEMA DE RECOMENDACION A GRUPOS");
		System.out.println("\nHola, soy el encargado de la negociacion");
		System.out.println("Por favor, ingresá el numero de participantes:");
		
		Scanner sc = new Scanner(System.in);
		int num_participantes = sc.nextInt();
		
		this.crearGrupo(num_participantes);
		
		System.out.println("\nPor favor, selecciona el paquete de ofertas:");
		System.out.println("Paquete 1: Pelicula y Cena");
		System.out.println("Paquete 2: Pelicula");
		
		this.seleccionarPaqueteOferta();
		
	}
	
	private void crearGrupo(int num_participantes) {
		
		listaAgentes = new ArrayList<>();
		ApplicationContext context = new AnnotationConfigApplicationContext(AgentUser.class); 
		int i =1;
		while(i<=num_participantes) {
			AgentUser agentUser =  (AgentUser) context.getBean(AgentUser.class);
			agentUser.setNombreUsuario("Negociador" + i);
			listaAgentes.add(agentUser);
			i++;
		}
	}
	
	private void seleccionarPaqueteOferta() {
		
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		
		switch (opcion) {
		case 1:
			System.out.println("Paquete de ofertas a negociar: Paquete 1 - Pelicula y Cena" );
			this.crearPaqueteOferta(opcion);
			this.ejecutarProtocolo();
			break;
		
		case 2:
			System.out.println("Paquete de ofertas a negociar: Paquete 1 - Pelicula" );
			this.crearPaqueteOferta(opcion);
			this.ejecutarProtocolo();
			break;

		default:
			System.out.println("La opcion ingresada no es válida. Ingrese una opcion nuevamente" );
			seleccionarPaqueteOferta();
		}
	}
	
	private void crearPaqueteOferta(int paqueteOferta) {
		if(paqueteOferta == 1) {
			for(AgentUser agente : listaAgentes) {
				agente.loadItems();
			}
		}
	}
	
	private void ejecutarProtocolo() {

	}
	
}
