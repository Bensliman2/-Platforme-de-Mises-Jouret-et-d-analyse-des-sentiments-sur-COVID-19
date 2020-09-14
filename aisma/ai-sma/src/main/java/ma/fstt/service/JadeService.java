package ma.fstt.service;

import org.springframework.stereotype.Service;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

@Service
public class JadeService {	
	private Runtime runtime;
	private Profile p;
	private ContainerController containerController;
	
	public JadeService() {
		runtime = Runtime.instance();
		p = new ProfileImpl();
		containerController = runtime.createMainContainer(p);
		try {
			AgentController rma = containerController.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
			
//			AgentController appAgent = containerController.createNewAgent("app-agent", "ma.fstt.agent.AppAgent", null);
//			appAgent.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

	public Runtime getRuntime() {
		return runtime;
	}

	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	public Profile getP() {
		return p;
	}

	public void setP(Profile p) {
		this.p = p;
	}

	public ContainerController getContainerController() {
		return containerController;
	}

	public void setContainerController(ContainerController containerController) {
		this.containerController = containerController;
	}

}
