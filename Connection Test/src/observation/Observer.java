package observation;

import simulator.Node;
import fileIO.NetworkConfig;

public interface Observer {
	public void update(NetworkConfig netConfigFile);
	public void update(Node node, int flag);
}
