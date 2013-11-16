import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.apache.log4j.Logger;


public class Listener extends Channel implements Runnable {

	private Selector channelMgr;
	private DatagramChannel client;
	private InetSocketAddress destAddress;
	private Logger _logger = Logger.getLogger(Listener.class);
	private MessageQueue messages;
	
	public Listener(InetSocketAddress pInitiator, InetSocketAddress pListener, MessageQueue pMsgQueue, int pBufferSize) {
		super(pInitiator, pListener);
		this.channelMgr = null;
		this.client = null;
		this.destAddress = null;
		this.messages = pMsgQueue;
		this.buffer = ByteBuffer.allocateDirect(pBufferSize);
	}

	@Override
	public void run() {
		try{
			// Call openConnection on listener thread
			this.openConnection();
			// Put listener in a listening or wait state
			this.listen();
			this.closeConnection();
        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
	}
	
	public void openConnection() {
		try {
			//Establish a new channel
			this.channel = DatagramChannel.open();
			this.channel.socket().bind(this.listener); 
			this.channelMgr = SelectorProvider.provider().openSelector();
		    this.channel.configureBlocking(false);   
		    this.channel.register(this.channelMgr, this.channel.validOps());
		    this._logger.debug("Listening channel is open.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void listen() {
		try {
			this._logger.debug("Listener is commencing.");
			while (!Thread.currentThread().isInterrupted()) {                          
	            this.channelMgr.select();
	            for (Iterator<SelectionKey> keyIndex = channelMgr.selectedKeys().iterator(); keyIndex.hasNext();) { 
	            	
	    			SelectionKey lKey = keyIndex.next(); 
	    			keyIndex.remove(); 
	    			this.client = (DatagramChannel)lKey.channel();
	    			//reading data
	    			if (lKey.isReadable()) {
	    				this.buffer.clear();	 
	    				//TCP version contains a loop to read all the data, but overall it would not affect the design
	    				//The construct to read data in a loop is converted to a single line
	    				this.destAddress = (InetSocketAddress)this.client.receive(buffer);
	    				this._logger.debug("Incoming destination address: " + destAddress);
	        			this.buffer.flip();
	        			//New Input Message
	        			if (this.buffer.hasRemaining()) {
	        				this._logger.debug("Received message");
	        				Message lMessage = convertBufferToMessage(this.buffer);
	        				this._logger.debug("Message's file version: " + lMessage.getFileVersion());
	        				ArrayList<UUID> lNodeIDs = lMessage.getNodeIDs();
	        				for (int index = 0; index < lNodeIDs.size(); index++) {
	        					this._logger.debug("Node UUID " + Integer.toString(index) + ": "+ lNodeIDs.get(index));
	        				}
	        				//Add message to message queue
	        				this.messages.enqueue(lMessage);
	        			} //buffer.hasRemaining
	    				buffer.clear();
	        		}
	            }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (this.channel.isConnected()) {
				closeConnection();
			}
		}
	}
	
	private Message convertBufferToMessage(ByteBuffer pBuffer) {
		Message lMsg = null;					
	   	byte[] bytes = new byte[buffer.remaining()];
		pBuffer.get(bytes);
		lMsg = Serializer.reconstruct(bytes);
		return(lMsg);
	}
	
	public void closeConnection() {
		try {
			if (this.channel.isConnected()) { 
          		this.channel.close();
          		this._logger.debug("Listening channel is closed.");
			}
			this._logger.debug("Listener has completed listening.");
		} catch (Exception ex) {
		}
	}
}
