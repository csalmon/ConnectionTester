package simulator;

import org.apache.log4j.Logger;

public class Simulation implements Runnable {
	Node lActiveNode = null;
	Logger _lLogger = null;
	
	public Simulation(Node pActiveNode) {
		this.lActiveNode = pActiveNode;
	}
	
	public void testSimulator() {
		this._lLogger = Logger.getLogger(Simulation.class);
		
		try {
			if (null == this.lActiveNode) {
				this._lLogger.error("No active node defined.  Unable to run simulation.");
				return;
			}
			
			// Activate Node Listeners
			this.lActiveNode.startListeners();
			this._lLogger.info("Listeners activated");
			
			// Process the active node while the simulation is running
			this._lLogger.info("Beginning Simulation");
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
				_lLogger.info("Simulation stopped");
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
					_lLogger.info("Simulation ended");
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
