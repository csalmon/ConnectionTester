package simulator;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;


public class Initiator extends Channel {

	private Logger _logger = Logger.getLogger(Initiator.class);
	
	public Initiator(InetSocketAddress pInitiator, InetSocketAddress pListener) {
		super(pInitiator, pListener);	
	}
	
	public void openConnection() {
		try {
			this.channel = DatagramChannel.open();
			//this.channel.socket().bind(this.initiator);
			this.channel.configureBlocking(false);       
			this.channel.connect(this.listener);
			this._logger.debug("Initiator connection is open.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public void sendMessage(Message pMessage) {
		try {
			if (null != this.channel) {
				if (this.channel.isConnected()) {	     
					this.buffer = ByteBuffer.wrap(Serializer.serialize(pMessage));
					this.channel.send(buffer,listener);
					this._logger.debug("Initiator has sent a message.");
					this._logger.debug("Sent message's file version: " + pMessage.getFileVersion());
					ArrayList<UUID> lNodeIDs = pMessage.getNodeIDs();
    				for (int index = 0; index < lNodeIDs.size(); index++) {
    					this._logger.debug("Sent Node UUID " + Integer.toString(index) + ": " + lNodeIDs.get(index));
    				}
	        	 }
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			if (this.channel.isConnected()) { 
	      		this.channel.close();
	      		this._logger.debug("Initiator connection is closed.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
