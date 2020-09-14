package ma.fstt.agent;

import java.io.File;
import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ma.fstt.util.AppVars;

public class SentimentAnalysisAgent extends Agent{
	private static final long serialVersionUID = 1L;
	public static final String TYPE = "sentiment";

	private SentimentAnalysisBehaviour b;
	
	@Override
	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			String tag = (String) args[0];
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType(TYPE);
			sd.setName(TYPE + "-" + tag);
			dfd.addServices(sd);
			try {
				DFService.register(this, dfd);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
			
			b = new SentimentAnalysisBehaviour(tag);
			addBehaviour(b);
		}
	}
	
	@Override
	protected void takeDown() {
		b.stop();
	}
}

class SentimentAnalysisBehaviour extends Behaviour{
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private Process process;
	private boolean running=false;
	
	public SentimentAnalysisBehaviour(String tag) {
		this.tag = tag;
	}

	@Override
	public void action() {
		if(!running)try {
			String cmd = String.format("python main.py %s", tag);
			process = Runtime.getRuntime().exec(cmd, null, new File(AppVars.SENTIMENT_APP));
			running = process.isAlive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return !(process!=null && process.isAlive());
	}
	
	@Override
	public int onEnd() {
		stop();
		return super.onEnd();
	}
	
	public void stop() {
		if(process!=null)process.destroyForcibly();
		myAgent.doDelete();
	}
}