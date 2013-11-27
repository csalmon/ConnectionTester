package observation;

import fileIO.NetworkConfig;

public interface Observer {
	public void update(NetworkConfig netConfigFile);
}
