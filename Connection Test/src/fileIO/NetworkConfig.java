package fileIO;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

import simulator.Node;


public class NetworkConfig {
    
	private ArrayList<Node> nodes;
	private Node activeNode;

    public NetworkConfig(String pFileName) {
        try {
            // obtain and configure a SAX based parser
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);

            // obtain object for SAX parser
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // obtain an XML reader
            XMLReader xmlReader = saxParser.getXMLReader();
            XMLLoader loadedNodes = new XMLLoader();
            xmlReader.setContentHandler(loadedNodes);
            xmlReader.parse(convertToFileURL(pFileName));

            this.nodes = loadedNodes.getNodes();
            this.activeNode = null;
            setActiveNode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public NetworkConfig() {
        try {
            // obtain and configure a SAX based parser
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);

            // obtain object for SAX parser
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // obtain an XML reader
            XMLReader xmlReader = saxParser.getXMLReader();
            XMLLoader loadedNodes = new XMLLoader();
            xmlReader.setContentHandler(loadedNodes);
            //xmlReader.parse(convertToFileURL(pFileName));

            this.nodes = loadedNodes.getNodes();
            this.activeNode = null;
            setActiveNode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int size() {
    	
    	return(this.nodes.size());
    }
    
    public boolean add(Node pNewNode) {
    
    	return(this.nodes.add(pNewNode));
    }
    
    public void add(int pIndex, Node pNewNode) {
        
    	this.nodes.add(pIndex, pNewNode);
    }
    
    public void clear() {
        
    	this.nodes.clear();
    }
    
    public boolean contains(Node pNode) {
        
    	return(this.nodes.contains(pNode));
    }
    
    public Node get(int pIndex) {
        
    	return(this.nodes.get(pIndex));
    }
    
    public Node get(UUID pUUID) {
    	for (int index = 0; index < this.nodes.size(); index++) {
    		Node lSearchNode = this.nodes.get(index);
    		if (lSearchNode.getNID().equals(pUUID)) {
    			return(lSearchNode);
    		}
    	}
    	return(null);
    }
    
    public int indexOf(Node pNode) {
        
    	return(this.nodes.indexOf(pNode));
    }

    public boolean isEmpty() {
        
    	return(this.nodes.isEmpty());
    }

    public Iterator<Node> iterator() {
        
    	return(this.nodes.iterator());
    }

    public int lastIndexOf(Node pNode) {
        
    	return(this.nodes.lastIndexOf(pNode));
    }

    public ListIterator<Node> listIterator() {
        
    	return(this.nodes.listIterator());
    }

    public ListIterator<Node> listIterator(int pIndex) {
        
    	return(this.nodes.listIterator(pIndex));
    }

    public Node remove(int pIndex) {
        
    	return(this.nodes.remove(pIndex));
    }
    
    public boolean remove(Node pNode) {
        
    	return(this.nodes.remove(pNode));
    }
    
    public Node remove(UUID pUUID) {
    	for (int index = 0; index < this.nodes.size(); index++) {
    		Node lSearchNode = this.nodes.get(index);
    		if (lSearchNode.getNID().equals(pUUID)) {
    			this.nodes.remove(lSearchNode);
    			return(lSearchNode);
    		}
    	}
    	return(null);
    }
    
    public Node set(int pIndex, Node pNode) {
        
    	return(this.nodes.set(pIndex, pNode));
    }
    
    public boolean update(Node pNode) {

    	for (int index = 0; index < this.nodes.size(); index++) {
    		Node lSearchNode = this.nodes.get(index);
    		if (lSearchNode.getNID().equals(pNode.getNID())) {
    			this.nodes.set(index, pNode);
    			return(true);
    		}
    	}
    	
		return(this.nodes.add(pNode));
    }
    
    public Object[] toArray() {
        
    	return(this.nodes.toArray());
    }
    
    public Node getActiveNode() {
    	return(this.activeNode);
    }
    private String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }
    
    private void setActiveNode() {
    	try {
	    	ArrayList<InetAddress> netDeviceIPs = new ArrayList<InetAddress>();
	    	Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	    	for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
	    		netDeviceIPs.addAll(getInterfaceIPs(networkInterface));
	    	}
	    	this.activeNode = findActiveNode(netDeviceIPs);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private ArrayList<InetAddress> getInterfaceIPs(NetworkInterface pNetworkInterface) {
		ArrayList<InetAddress> interfaceIPs = new ArrayList<InetAddress>();
		Enumeration<InetAddress> inetAddresses = pNetworkInterface.getInetAddresses();
		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
		    if (isValidIP(inetAddress)) {
		    	interfaceIPs.add(inetAddress);
		    }
		}
		return(interfaceIPs);
    }
    
    private boolean isValidIP(InetAddress pInetAddress) {
    	//String ipAddress = pInetAddress.toString();
    	/* Any IP allowed for the time being
    	if (ipAddress.matches("/127.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}") ||
    		ipAddress.matches("/192.168.[0-9]{1,3}.[0-9]{1,3}") ||
    	    ipAddress.matches("/10.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}") ||
    	    ipAddress.matches("/172.1[6-9]|2[0-9]|3[0-1].[0-9]{1,3}.[0-9]{1,3}") ||
    	    ipAddress.matches("/22[4-9]|/23[0-9].[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}") ||
    	    ipAddress.matches("255.255.255.255")) {
    		return(false);
    	}
    	*/
    	return(true);
    }
    
    private Node findActiveNode(ArrayList<InetAddress> pNetDeviceIPs) {
    	try {
	    	InetAddress specialIP = InetAddress.getByName("1.1.1.1");
	    	for (int nodeIndex = 0; nodeIndex < this.nodes.size(); nodeIndex++) {
	    		ArrayList<InetAddress> nodeIPs = new ArrayList<InetAddress>();
	    		nodeIPs.addAll(this.nodes.get(nodeIndex).getExternalIPs());
	    		nodeIPs.addAll(this.nodes.get(nodeIndex).getInternalIPs());
	    		for (int ipIndex = 0; ipIndex < nodeIPs.size(); ipIndex++) {
	    			if (pNetDeviceIPs.contains(nodeIPs.get(ipIndex)) ||
	    				pNetDeviceIPs.contains(specialIP)) {
	    				return(this.nodes.get(nodeIndex));
	    			}
	    			if (nodeIPs.contains(specialIP)) {
		    			return(this.nodes.get(nodeIndex));
		    		}
	    		}
	    	}
	    	return(null);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return(null);
    	}
    }
}