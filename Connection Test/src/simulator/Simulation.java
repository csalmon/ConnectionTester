package simulator;

import java.util.ArrayList;

import observation.Observable;
import observation.Observer;

import org.apache.log4j.Logger;

public class Simulation implements Runnable, Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	Node lActiveNode = null;
	Logger _lLogger = null;//Logger.getLogger(Simulation.class);
	Message lMessage = null;
	final int UNUSED_FLAG = 0;
	
	public void setActiveNode(Node pActiveNode) {
		this.lActiveNode = pActiveNode;
	}
	
	public Simulation() {
		
	}
	
	public void testSimulator() {
		
		try {
			_lLogger = Logger.getLogger(Simulation.class);
			if (null == this.lActiveNode) {
				_lLogger.info("No active node defined.  Unable to run simulation.");
				return;
			}
			
			// Activate Node Listeners
			this.lActiveNode.startListeners();
			_lLogger.info("Listeners activated");
			
			// Process the active node while the simulation is running
			_lLogger.info("Beginning Simulation");
			lMessage = new Message();
			while (!Thread.currentThread().isInterrupted() ) {
				// Process messages for the active (local) node identified from the NetworkConfiguration
				lMessage = lActiveNode.processMessage();
				//Pass lMessage back to the UI
				this.notifyObservers();
				Thread.sleep(1000);
			}
		} catch (Exception ex) {
			try {
				// Wait for listener to finish its debugging I/O
				//Thread.sleep(500);
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
				//Thread.sleep(500);
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

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObservers() {
		for (Observer ob : observers) {
            System.out.println("Notifying Observers about a node change.");
            
            ob.update(this.lMessage,UNUSED_FLAG);
		}
		
	}
}
