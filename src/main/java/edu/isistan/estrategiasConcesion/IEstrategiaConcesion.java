package edu.isistan.estrategiasConcesion;

import java.util.List;

import edu.isistan.agentes.AgentUser;
import edu.isistan.items.IItem;

public interface IEstrategiaConcesion {

	public IItem ejecutarEstrategia(List<AgentUser> listaAgentes, AgentUser agente);
}
