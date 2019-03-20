package edu.isistan;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.isistan.agentes.AgentMediator;
import edu.isistan.items.Cena;
import edu.isistan.items.IItem;
import edu.isistan.items.Pelicula;

@SpringBootApplication
public class SmaMultiIssueNegotiationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SmaMultiIssueNegotiationApplication.class, args);
		
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
		AgentMediator agentMediator;
		IItem item;
		String rutaCatalogo;

		switch (opcion_select) {
		case 1:
			item = new Pelicula();
			rutaCatalogo = "catalogoItems/CatalogoPeliculas.txt";
			agentMediator = new AgentMediator(num_participantes, item, rutaCatalogo);
			break;
		
		case 2:
			item = new Cena();
			rutaCatalogo = "catalogoItems/CatalogoCena.txt";
			agentMediator = new AgentMediator(num_participantes, item, rutaCatalogo);
			break;

		default:
			System.out.println("La opcion ingresada no es válida. Ingrese una opcion nuevamente" );
		}
		
	}

}
