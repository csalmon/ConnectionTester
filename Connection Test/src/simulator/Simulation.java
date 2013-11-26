package simulator;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;


public class Simulation implements Runnable {
	Node lActiveNode = null;
	Listener lTestListener = null;
	Initiator lTestInitiator = null;
	Logger _lLogger = null;
	
	public Simulation() {
		
	}
	
	public void testSimulator() {
		System.out.println("Simulation started");
		try {
			// Items marked with *** will really be used as constructor actions
			// or as a call to the SimulatorPanel class when the NetworkConfiguration
			// is loaded.  Here, these actions are just used to setup a driver program.
			_lLogger = Logger.getLogger(Simulation.class);
			int lNumNodes = 5;
			// Size of file version + size of UUID + some extra
			int lMessageSize = 20 + 16 + 64;

			// *** Create an array of nodes representing the NetworkConfiguration
			lActiveNode = new Node("1.0.0.0");
			_lLogger.debug("Node created");
			
			// ** Define the source and destinations of the channel
			int lPort = 16128;
			InetSocketAddress lInitiatorAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), lPort);
			InetSocketAddress lListenerAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), lPort);
			_lLogger.debug("Channel Endpoints Created");
			
			// *** Create listener channels and associate with each node
			// (as defined by the NetworkConfiguration)
			lTestListener = new Listener(lInitiatorAddr, lListenerAddr, lActiveNode.getMessageQueue(), lNumNodes * lMessageSize);
			lActiveNode.addListener(lTestListener);
			_lLogger.debug("Listeners Created");
			
			// *** Create initiator channels and associate with each node
			// (as defined by the NetworkConfiguration)
			lTestInitiator = new Initiator(lInitiatorAddr, lListenerAddr);			
			lActiveNode.addInitiator(lTestInitiator);
			_lLogger.debug("Initiators Created");
			

			// Activate Node Listeners
			lActiveNode.startListeners();
			_lLogger.debug("Listeners activated");
			
			// Process the active node while the simulation is running
			// while (true = simulation running) {
			Message lMessage = new Message();
			while (lMessage.getNodeIDs().size() < lNumNodes && !Thread.currentThread().isInterrupted() ) {
				// Process messages for the active (local) node identified from the NetworkConfiguration
				lMessage = lActiveNode.processMessage();
			}
			
			// Wait for listener to finish its debuggin I/O
			Thread.sleep(3000);
			lActiveNode.stopListeners();
			lActiveNode = null;
			_lLogger.debug("Simulation ended");
			System.out.println("Simulation ended");
		
		} catch (Exception ex) {
			//ex.printStackTrace();
			lActiveNode.stopListeners();
			lActiveNode = null;
			_lLogger = null;
			System.out.println("Simulation interrupted");
		}
	}

	@Override
	public void run() {
		this.testSimulator();
		
    }
		
}
