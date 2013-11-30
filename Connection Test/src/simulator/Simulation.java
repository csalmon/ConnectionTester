package simulator;

import org.apache.log4j.Logger;

public class Simulation implements Runnable {
	Node lActiveNode = null;
	Logger _lLogger = null;
	
	public Simulation(Node pActiveNode) {
		this.lActiveNode = pActiveNode;
	}
	
	public void testSimulator() {
		try {
			if (null == this.lActiveNode) {
				this._lLogger.debug("No active node defined.  Unable to run simulation.");
				System.out.println("No active node defined.  Unable to run simulation.");
				return;
			}
			
			this._lLogger = Logger.getLogger(Simulation.class);
			
			// Activate Node Listeners
			this.lActiveNode.startListeners();
			this._lLogger.debug("Listeners activated");
			System.out.println("Listeners activated");
			
			// Process the active node while the simulation is running
			this._lLogger.debug("Beginning Simulation");
			System.out.println("Beginning Simulation");
			Message lMessage = new Message();
			while (!Thread.currentThread().isInterrupted() ) {
				// Process messages for the active (local) node identified from the NetworkConfiguration
				lMessage = lActiveNode.processMessage();
				//TODO: pass lMessage back to the UI
				Thread.sleep(1000);
			}
		} catch (Exception ex) {
			try {
				// Wait for listener to finish its debugging I/O
				Thread.sleep(1000);
				this.lActiveNode.stopListeners();
				lActiveNode = null;
				_lLogger.debug("Simulation stopped");
				System.out.println("Simulation stopped");
				_lLogger = null;
			} catch (Exception iex) {
				ex.printStackTrace();
			}
			
		} finally {
			try {
				// Wait for listener to finish its debugging I/O
				Thread.sleep(1000);
				if (null != this.lActiveNode) {
					this.lActiveNode.stopListeners();
					lActiveNode = null;
				}
				
				if (null != _lLogger) {
					_lLogger.debug("Simulation ended");
					System.out.println("Simulation ended");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		this.testSimulator();	
    }
}
