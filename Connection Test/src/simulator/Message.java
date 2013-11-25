package simulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


public class Message implements Serializable {

	private static final long serialVersionUID = 7185166580395590727L;
	private String fileVersion;
	//private UUID nodeID;
	private ArrayList<UUID> nodeIDs;
	
	public Message() {
		this.fileVersion = "";
//		this.nodeID = null;
		this.nodeIDs = new ArrayList<UUID>();
	}
	
	public Message(String pFileVersion) {
		this();
		this.fileVersion = pFileVersion;
	}
	
	public String getFileVersion() {
		return(fileVersion);
	}
	
	public void setFileVersion(String pVersion) {
		this.fileVersion = new String(pVersion);
	}

	public ArrayList<UUID> getNodeIDs() {
		return(this.nodeIDs);
	}
	
	public void addNodeID(UUID pNewNode) {
		this.nodeIDs.add(pNewNode);
	}
}
