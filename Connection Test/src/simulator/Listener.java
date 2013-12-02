package simulator;

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
	// Size of file version + size of UUID + some extra
	private final int BUFFER_SIZE = (20 + 16 + 64) * 100;
	
	public Listener(InetSocketAddress pInitiator, InetSocketAddress pListener, MessageQueue pMsgQueue) {
		super(pInitiator, pListener);
		this.channelMgr = null;
		this.client = null;
		this.destAddress = null;
		this.messages = pMsgQueue;
		this.buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
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
        catch(Exception e){
        	String why = null;
            Throwable cause = e.getCause();
            if (cause != null) {
                why = cause.getMessage();
            } else {
                why = e.getMessage();
            }
            _logger.error("Exception thrown in run(): " + why);
            
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
		} catch (Exception e) {
			String why = null;
            Throwable cause = e.getCause();
            if (cause != null) {
                why = cause.getMessage();
            } else {
                why = e.getMessage();
            }
            _logger.error("Exception thrown in openConnection(): " + why + "\n Here's the stacktrace: \n");
            e.printStackTrace();
            
        }
		
	}
	
	private void listen() {
		try {
			this._logger.info("Listener is commencing.");
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
	    				this._logger.info("Incoming message from: " + destAddress.getHostName());
	        			this.buffer.flip();
	        			//New Input Message
	        			if (this.buffer.hasRemaining()) {
	        				this._logger.info("Received message");
	        				Message lMessage = convertBufferToMessage(this.buffer);
	        				this._logger.info("Received message's file version: " + lMessage.getFileVersion());
	        				ArrayList<UUID> lNodeIDs = lMessage.getNodeIDs();
	        				for (int index = 0; index < lNodeIDs.size(); index++) {
	        					this._logger.info("Received Node UUID " + Integer.toString(index) + ": "+ lNodeIDs.get(index).toString().substring(30));
	        				}
	        				//Add message to message queue
	        				this.messages.enqueue(lMessage);
	        			} //buffer.hasRemaining
	    				buffer.clear();
	        		}
	            }
			}
			this.channelMgr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
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
			this.channel.socket().close();
          	this.channel.close();
          	this._logger.debug("Listening channel is closed.");
			this._logger.info("Listener has completed listening.");
		} catch (Exception ex) {
			
		}
	}
	
	public void setMessageQueue(MessageQueue pMessages) {
		this.messages = pMessages;
	}
}
