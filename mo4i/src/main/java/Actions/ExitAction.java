package Actions;

import mo4i.main.Main;

public class ExitAction implements Action{

	@Override
	public void execute() {
		Main.quit();
	}

}
