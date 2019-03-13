package edu.isistan.agents;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AgentMediator {
	
	public AgentMediator() {
		
		System.out.println("HOLA SOY UN AGENTE MEDIADOR DE LA NEGOCIACION");
		System.out.println("Los negociantes son:");		
		
		ApplicationContext context = new AnnotationConfigApplicationContext(AgentUser.class); 
		 
		AgentUser agentUserA =  (AgentUser) context.getBean(AgentUser.class);
		agentUserA.setNombreUsuario("A");
		
		AgentUser agentUserB =  (AgentUser) context.getBean(AgentUser.class);
		agentUserB.setNombreUsuario("B");
		
		AgentUser agentUserC =   (AgentUser) context.getBean(AgentUser.class);
		agentUserC.setNombreUsuario("C"); 
		
		System.out.println(agentUserA.getNombreUsuario());
		System.out.println(agentUserB.getNombreUsuario());
		System.out.println(agentUserC.getNombreUsuario()); 
		
		//this.ejecutarProtocolo();
	}
	
	private void ejecutarProtocolo() {
		
	}
	
}
