package simulator;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import enums.NodeState;

public class Node {

	private UUID nID;
	private String name;
	private String fileVersion;
	private String operatingSystem;
	private String requiredSoftware;
	private ArrayList<InetAddress> externalIPs;
	private ArrayList<InetAddress> internalIPs;
	private String securityZone;
	private NodeState state;
	private ContactInfo contactInfo;
	private ArrayList<Listener> listeners;
	private ArrayList<Thread> listenerThreads;
	private ArrayList<Initiator> initiators;
	private MessageQueue messages;
	private Logger _logger = Logger.getLogger(Node.class);
	int numRecvMessages;
	int inactiveTimer;
	int inactiveTimerCount;
	
	public Node (String pFileVersion) {
		this.nID = UUID.randomUUID();
		this.name = new String("New Node");
		this.fileVersion = pFileVersion;
		this.operatingSystem = new String("Operating System");
		this.requiredSoftware = new String("");
		this.externalIPs = new ArrayList<InetAddress>();
		this.internalIPs = new ArrayList<InetAddress>();
		this.securityZone = new String("");
		this.state = NodeState.INACTIVE;
		this.contactInfo = new ContactInfo();
		this.listeners = new ArrayList<Listener>();
		this.listenerThreads = new ArrayList<Thread>();
		this.initiators = new ArrayList<Initiator>();
		this.messages = new MessageQueue();
		this.numRecvMessages = 0;
		this.inactiveTimer = 0;
		this.inactiveTimerCount = 0;
	}

	public UUID getNID() {
		return(nID);
	}

	public void setNID(UUID pUUID) {
		this.nID = pUUID;
	}
	
	public String getName() {
		return(name);
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String getFileVersion() {
		return(fileVersion);
	}

	public void setFileVersion(String pFileVersion) {
		this.fileVersion = pFileVersion;
	}

	public String getOperatingSystem() {
		return(operatingSystem);
	}

	public void setOperatingSystem(String pOperatingSystem) {
		this.operatingSystem = pOperatingSystem;
	}

	public String getRequiredSoftware() {
		return(requiredSoftware);
	}

	public void setRequiredSoftware(String pRequiredSoftware) {
		this.requiredSoftware = pRequiredSoftware;
	}

	public ArrayList<InetAddress> getExternalIPs() {
		return(externalIPs);
	}

	public void addExternalIP(InetAddress pExternalIPs) {
		this.externalIPs.add(pExternalIPs);
	}

	public ArrayList<InetAddress> getInternalIPs() {
		return(internalIPs);
	}

	public void addInternalIP(InetAddress pInternalIPs) {
		this.internalIPs.add(pInternalIPs);
	}

	public String getSecurityZone() {
		return(securityZone);
	}

	public void setSecurityZone(String pSecurityZone) {
		this.securityZone = pSecurityZone;
	}

	public NodeState getState() {
		return(state);
	}

	public void setState(NodeState pState) {
		this.state = pState;
	}

	public ContactInfo getContactInfo() {
		return(contactInfo);
	}

	public void setContactInfo(ContactInfo pContactInfo) {
		this.contactInfo = pContactInfo;
	}

	public ArrayList<Listener> getListeners() {
		return(listeners);
	}

	public void addListener(Listener pListener) {
		pListener.setMessageQueue(this.messages);
		this.listeners.add(pListener);
	}

	public ArrayList<Initiator> getInitiators() {
		return(initiators);
	}

	public void addInitiator(Initiator pInitiator) {
		this.initiators.add(pInitiator);
	}

	public MessageQueue getMessageQueue() {
		return(this.messages);
	}
	
	public void startListeners() {
		
		// For every listener, create a new listening thread
		for (int index = 0; index < this.listeners.size(); index++) {
			this._logger.debug("Activating listener " + Integer.toString(index));
			String lTName = new String("listener");
			lTName += Integer.toString(index);
			this.listenerThreads.add(new Thread((Listener)this.listeners.get(index), lTName));
			this.listenerThreads.get(index).start();
		}
		this._logger.debug("Listeners have been activated.");
	}
	
	public Message processMessage(int pNumNodes) {
		
		// Dequeue a message (if one exists), if not create a new one
		Message lMessage = messages.dequeue();
		if (null == lMessage) {
			lMessage = createNewMessage();
		} else if (this.numRecvMessages > pNumNodes) {
			try {
				//Thread.sleep(pNumNodes * 1000);
				messages.clear();
				lMessage = createNewMessage();
				this.numRecvMessages = 0;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			// Update message (add current node ID)
			lMessage = updateMessage(lMessage);
			this.numRecvMessages++;
		}

		// For every initiator send out the updated message
		for (int index = 0; index < initiators.size(); index++) {
			this._logger.debug("Sending message for initiator: " + Integer.toString(index));
			initiators.get(index).openConnection();
			initiators.get(index).sendMessage(lMessage);
			initiators.get(index).closeConnection();
		}
		
		this._logger.debug("All initiators have sent their messages");
		// Return updated message
		return(lMessage);
	}
	
	public void stopListeners() {
	
		for (int index = 0; index < this.listenerThreads.size(); index++) {
			this.listenerThreads.get(index).interrupt();
			this.listenerThreads.remove(index);
		}
	}
	
	
	public void setInactiveTimerCount(int pTimerCount) {
		this.inactiveTimerCount = pTimerCount;
	}
	
	public int getInactiveTimerCount() {
		return(this.inactiveTimerCount);
	}
	
	public int getInactiveTimer() {
		return(this.inactiveTimer);
	}
	
	public void resetInactiveTimer() {
		this.inactiveTimer = this.inactiveTimerCount;
	}
	
	public void inactiveTimerTick() {
		this.inactiveTimer--;
	}
	
	private Message updateMessage(Message pMessage) {
		
		pMessage.addNodeID(this.nID);
		return(pMessage);
	}
	
	private Message createNewMessage() {
		Message lMessage = new Message(this.fileVersion);
		lMessage.addNodeID(this.nID);
		this._logger.info("New message created");
		return(lMessage);
	}
}
