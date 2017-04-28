package zewen.unimelb.VO;

import java.util.List;


public abstract class RequestVO extends AbstractVO {
	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
