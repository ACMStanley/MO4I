package Actions;

import mo4i.main.Client;

public class ExitAction implements Action{

	@Override
	public void execute() {
		Client.quit();
	}

}
