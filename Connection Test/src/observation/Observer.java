package observation;

import simulator.Message;
import fileIO.NetworkConfig;

public interface Observer {
	public void update(NetworkConfig netConfigFile);
	public void update(Message message, int flag);
}
