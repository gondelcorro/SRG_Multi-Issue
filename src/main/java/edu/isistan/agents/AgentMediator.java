package edu.isistan.agents;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

import edu.isistan.items.IItem;

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
		ejecutarProtocolo();
	}
	
	private void crearGrupo() {
		//el grupo es una lista de agentes
		listaAgentes = new ArrayList<>();
		int i =1;
		while(i<=num_participantes) {
			AgentUser agentUser = new AgentUser();
			agentUser.setNombreUsuario("Negociador" + i);
			agentUser.loadCatalogo(itemANegociar, rutaCatalogo); //los agentes misma lista de items a negociar
			agentUser.loadUtilidades();
			listaAgentes.add(agentUser);
			i++;
		}
		
		for(AgentUser agente : listaAgentes) {
			System.out.println("Los items del agente " + agente.getNombreUsuario() + " son:");
			/*for(Object item : agente.getListaItems()) {
				System.out.print( ((IItem)item).getNombre() + " | ");
			}*/
			agente.getListaUtilidades().forEach(item -> System.out.print(item.getItem().getNombre() + " - " + item.getUtilidad() + " | "));
			System.out.println("\n");
		}
	}

	private void ejecutarProtocolo() {
		
		//StopWatch timer = new StopWatch(); //inicia cronometro
		//timer.start();
		
		//Step 1: Inform the agents about the concession strategy they have to use
		
		//Step 2: Collect initial proposals (load the HashMap<AgentID, Proposal>)
		
		//Step 3: Loop until you reach an Agreement or a Conflict
				
				//Select who has to concede
		
	}
	/*
	public NegotiationResult<T> executeProtocol() throws ZeroAgentsInCoordinatorException{

		if (agContainer.isEmpty()){
			throw new ZeroAgentsInCoordinatorException();
		}
		
		StopWatch timer = new StopWatch();
		timer.start();
		
		//Step 1: Inform the agents about the concession strategy they have to use
		logger.info("Step 1: Inform the agents about the concession strategy they have to use. => "+ getMultilateralConcessionStrategy().getClass().getName());
		informConcessionStrategyToAgents();
		
		//Step 2: Collect initial proposals (load the HashMap<AgentID, Proposal>)
		logger.info("Step 2: Collect initial proposals (load the HashMap<AgentID, Proposal>)");
		HashMap<String, AgProposal<T>> proposals = collectInitialProposals();
//		logger.info("Step 2.a: Initial Proposals: \n "+proposals);

		logger.info("Step 3: Loop until you reach an Agreement or a Conflict.");
		//Step 3: Loop until you reach an Agreement or a Conflict
		while(!checkAgreement(proposals)){

			//Select who has to concede
			logger.info("Step 3.a: Select Agent who has to concede");
			List<UserAg<T>> shouldConcede = this.negotiationStrategy.selectWhoHasToConcede(agContainer.values()); //can be more than one if, for example, there are more than 1 agent with the same willingness to risk conflict value (if using the Zeuthen Strategy)

			if (shouldConcede.isEmpty()){
				logger.info("Step 4: CONFLICT REACHED.");
				timer.stop();
				return createNegotiationResult(new ConflictDealAgProposal<T>(), timer.getTime()); //conflict 
			}				
			else{				
				logger.info("Step 3.b: Agent/s who has to concede [#= "+ shouldConcede.size()+"]=> "+ shouldConcede.toString());
				
				for (UserAg<T> concedingAg : shouldConcede){
					//Make "concedingAg" to concede
					AgProposal<T> newProposal;
					try {
						newProposal = concedingAg.makeConcession();
						
						logger.info("Step 3.c: New Proposal made by the agent => "+newProposal.toString());
						//Update proposals map
						proposals.put(concedingAg.getID(), newProposal);
						
					//REMOVE THIS or change it -- debug only
					HashMap<String, Double> utilitiesMap = new HashMap<>();
					for (UserAg<T> ag : agContainer.values())
						utilitiesMap.put(ag.getID(), ag.getUtilityFor(newProposal));
						
						logger.debug("Utilities over new proposal:"+utilitiesMap);
						
					} catch (NonConcedableCurrentProposalException e) {
						  This should never happen because we have invoked the method using an agent 
						  that we know that it can concede but as we need to catch the exception we 
						  need this catch block.
						 
					}
				}
			}
		}
		//At this point an agreement should have been found => return it
		this.setLastAgreement(getAgreement(proposals));
		
		logger.info("Step 4: AGREEMENT reached with => "+ ((this.getLastAgreement() != null)? this.getLastAgreement().toString() : "Last Agreement was NULL"));
		timer.stop();
		return createNegotiationResult(this.getLastAgreement(), timer.getTime());
	}*/

	public List<AgentUser> getListaAgentes() {
		return listaAgentes;
	}

	public void setListaAgentes(List<AgentUser> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}
	
}
