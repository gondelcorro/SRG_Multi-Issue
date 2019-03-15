package edu.isistan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import edu.isistan.agents.AgentMediator;

@SpringBootApplication
public class SmaMultiIssueNegotiationApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SmaMultiIssueNegotiationApplication.class, args);
	}
	
 	@Bean
	public AgentMediator agentMediator() {
		return new AgentMediator();
	}

}
