package ma.fstt.agent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class TestAgent extends Agent{
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		addBehaviour(new TestBehaviour());
	}

}

class TestBehaviour extends Behaviour{
	private static final long serialVersionUID = 1L;
	
	int i=0;

	@Override
	public void action() {
		i++;
		System.out.println(i);
	}

	@Override
	public boolean done() {
		return i==10;
	}
	
}
