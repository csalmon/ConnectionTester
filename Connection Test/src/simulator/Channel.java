package simulator;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.UUID;


public abstract class Channel {

	protected UUID cID;
	protected DatagramChannel channel;
	protected ByteBuffer buffer;
	protected InetSocketAddress initiator;
	protected InetSocketAddress listener;
	//TODO:  Convert to object/java object?
	protected String transportProtocol;
	//TODO:  Convert to object/java object?
	protected String applicationProtocol;
	
	public Channel(InetSocketAddress pInitiator, InetSocketAddress pListener) {
		this.cID = UUID.randomUUID();
		this.initiator = pInitiator;
		this.listener = pListener;
		this.transportProtocol = new String("UDP");
		this.applicationProtocol = new String("Simulation");
		this.buffer = null;
	}
	
	public UUID getCID() {
		return(this.cID);
	}
	
	public void setCID(UUID pUUID) {
		this.cID = pUUID;
	}
	
	public int getInitiatorPort() {
		return(this.initiator.getPort());
	}
	
	public InetAddress getInitiatorAddr() {
		return(this.initiator.getAddress());
	}
	
	public InetSocketAddress getInitiator() {
		return (this.initiator);
	}
	
	public void setInitiator(InetAddress pAddress, int pPort) {
		this.initiator = new InetSocketAddress(pAddress, pPort);
	}
	
	public int getListenerPort() {
		return(this.listener.getPort());
	}
	
	public InetAddress getListenerAddr() {
		return(this.listener.getAddress());
	}
	
	public InetSocketAddress getListener() {
		return(this.listener);
	}
	
	public void setListener(InetAddress pAddress, int pPort) {
		this.listener = new InetSocketAddress(pAddress, pPort);
	}
	
	public String getTransportProtocol() {
		return(this.transportProtocol);
	}
	
	public void setTransportProtocol(String pProtocol) {
		this.transportProtocol = pProtocol;
	}
	
	public String getApplicationProtocol() {
		return(this.applicationProtocol);
	}
	
	public void setApplicationProtocol(String pProtocol) {
		this.applicationProtocol = pProtocol;
	}
	
	abstract void openConnection();
	
	abstract void closeConnection();
}
