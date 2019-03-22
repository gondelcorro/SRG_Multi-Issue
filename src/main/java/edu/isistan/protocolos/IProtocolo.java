package edu.isistan.protocolos;

import java.util.List;

import edu.isistan.agentes.AgentUser;
import edu.isistan.estrategiasConcesion.IEstrategiaConcesion;

public interface IProtocolo {
	
	public void setEstrategiaConcesion(IEstrategiaConcesion estrategiaConcesion);
	public void ejecutarProtocolo(List<AgentUser> listaAgentes);
}
