package ma.fstt.agent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

/**
 * This is a test Agent that prints from 0 to 10
 * @author Nouhaila
 *
 */

public class Test2Agent extends Agent{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		addBehaviour(new B());
	}
	
class B extends Behaviour{

	private static final long serialVersionUID = 1L;
	int i =0;
	
	@Override
	public void action() {
		System.out.println(i);
		i++;
	}

	@Override
	public boolean done() {
		if (i==10)return true;
		return false;
	}
	
}
}
