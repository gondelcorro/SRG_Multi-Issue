package edu.isistan.protocolos;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

import edu.isistan.agentes.AgentUser;
import edu.isistan.items.IItem;

public class ProtocoloDeProyeccionSecuencial implements IProtocolo{
	
	List<AgentUser> listaAgentes;
	HashMap<AgentUser, IItem>  propuestas;

	@Override
	public void ejecutarProtocolo(List<AgentUser> listaAgentes) {
		
		this.listaAgentes = listaAgentes;
		
		StopWatch timer = new StopWatch();
		timer.start();
		
		
		propuestas = recolectarPropIniciales();
		
		/*
		//Step 1: Inform the agents about the concession strategy they have to use
		informConcessionStrategyToAgents();
		
		//Step 2: Collect initial proposals (load the HashMap<AgentID, Proposal>)
		HashMap<String, AgProposal<T>> proposals = collectInitialProposals();

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
						
//						//REMOVE THIS or change it -- debug only
//						HashMap<String, Double> utilitiesMap = new HashMap<>();
//						for (UserAg<T> ag : agContainer.values())
//							utilitiesMap.put(ag.getID(), ag.getUtilityFor(newProposal));
//							
//						logger.debug("Utilities over new proposal:"+utilitiesMap);
						
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
		*/
		timer.stop();
		
	}
	
	private HashMap<AgentUser, IItem> recolectarPropIniciales() {
		
		return propuestas;
	}

}
