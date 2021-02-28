package Actions;

import mo4i.main.Client;

public class RunAction implements Action{
	
	@Override
	public void execute() {
		Client.getRunHandler().run();
	}
}
