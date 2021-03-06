package fileIO;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import enums.NodeState;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import simulator.Channel;
import simulator.ContactInfo;
import simulator.Node;
import simulator.Listener;
import simulator.Initiator;

public class XMLLoader extends DefaultHandler {

    private Stack<String> openTags;
    private ArrayList<String> tagValueList;
    private ArrayList<String> tagContainerList;
    private ArrayList<Node> nodes;
    private Node currNode;
    private Listener currListener;
    private Initiator currInitiator;
    private InetAddress channelAddr;
    private InetAddress remoteAddr;
    private int channelPort;
    private int remotePort;
    private String channelTransport;
    private String channelApplication;
    private String fileVersion;
    private boolean approval;
    private final String NODE = new String("node");
    private final String LISTENER = new String("listener");
    private final String INITIATOR = new String("initiator");
    private final String NETWORKCONFIG = new String("networkconfig");
    private final String VERSION = new String("version");
    private final String APPROVAL = new String("approved");
    private final String APPROVED = new String("true");
    private Logger rtLogger = Logger.getRootLogger();

    public XMLLoader() {
        this.openTags = new Stack<String>();
        this.tagValueList = new ArrayList<String>();
        this.tagContainerList = new ArrayList<String>();
        this.nodes = new ArrayList<Node>();
        this.currNode = null;
        this.currListener = null;
        this.currInitiator = null;
        this.channelAddr = null;
        this.remoteAddr = null;
        this.channelPort = 0;
        this.remotePort = 0;
        this.fileVersion = new String();
        this.approval = false;
        this.channelTransport = new String();
        this.channelApplication = new String();
        initTags();
    }

    // this method is called every time the parser gets an open tag '<'
    // identifies which tag is being open at time by assigning an open flag
    @Override
    public void startElement(String pUri, String pLocalName, String pQName,
                             Attributes pAttributes) throws SAXException {

        this.openTags.push(pQName);
        if (this.NETWORKCONFIG.equals(pQName)) {
            this.fileVersion = pAttributes.getValue(this.VERSION);
            if (this.APPROVED.equals(pAttributes.getValue(this.APPROVAL))) {
            	this.approval = true;
            } else {
            	this.approval = false;
            }
        } else if (this.NODE.equals(pQName)) {
            this.currNode = new Node(this.fileVersion);
            rtLogger.debug("Creating a new node.");
        } else if (this.LISTENER.equals(pQName)) {
            this.currListener = new Listener(null, null, null);
            rtLogger.debug("Creating a new listener.");
        } else if (this.INITIATOR.equals(pQName)) {
            this.currInitiator = new Initiator(null, null);
            rtLogger.debug("Creating a new initiator.");
        }
    }

    // prints data stored in between '<' and '>' tags
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if (!this.openTags.isEmpty()) {
            String property = this.openTags.peek();

            if (this.tagValueList.contains(property)) {
                String value = new String(ch, start, length);

                addNodeValue(property, value);
            }
        }
    }

    // calls by the parser whenever '>' end tag is found in xml
    // makes tags flag to 'close'
    @Override
    public void endElement(String pUri, String pLocalName, String pQName)
            throws SAXException {

        this.openTags.pop();
        if (this.NODE.equals(pQName)) {
            this.nodes.add(this.currNode);
            //this.currNode = null;
            rtLogger.debug("New node ["  + currNode.getNID().toString().substring(30) + "] added to network configuration");
            this.currNode = null;
        } else if (this.LISTENER.equals(pQName)) {
            this.currListener.setListener(this.channelAddr, this.channelPort);
            this.currListener.setTransportProtocol(this.channelTransport);
            this.currListener.setApplicationProtocol(this.channelApplication);
            this.currNode.addListener(this.currListener);
            clearElementVariables(this.currListener);
            rtLogger.debug("New listener added to the node.");
        } else if (this.INITIATOR.equals(pQName)) {
            this.currInitiator.setInitiator(this.channelAddr, this.channelPort);
            this.currInitiator.setListener(this.remoteAddr, this.remotePort);
            this.currInitiator.setTransportProtocol(this.channelTransport);
            this.currInitiator.setApplicationProtocol(this.channelApplication);
            this.currNode.addInitiator(this.currInitiator);
            clearElementVariables(this.currInitiator);
            rtLogger.debug("New initiator added to the node.");
        }
    }

    public ArrayList<Node> getNodes() {
        return(this.nodes);
    }

    public String getFileVersion() {
    	return(this.fileVersion);
    }
    
    public boolean getApproval() {
    	return(this.approval);
    }
    
    private void addNodeValue(String pProperty, String pValue) {
        try {
            if (null != this.currNode) {
                if (pProperty.equals("NUUID")) {
                    this.currNode.setNID(UUID.fromString(pValue));
                } else if (pProperty.equals("name")) {
                    this.currNode.setName(pValue);
                } else if (pProperty.equals("operating-system")) {
                    this.currNode.setOperatingSystem(pValue);
                } else if (pProperty.equals("required-software")) {
                    this.currNode.setRequiredSoftware(pValue);
                } else if (pProperty.equals("external-ip")) {
                    this.currNode.addExternalIP(InetAddress.getByName(pValue));
                } else if (pProperty.equals("internal-ip")) {
                    this.currNode.addInternalIP(InetAddress.getByName(pValue));
                } else if (pProperty.equals("security-zone")) {
                    this.currNode.setSecurityZone(pValue);
                } else if (pProperty.equals("status")) {
                    if (pValue.equals("ACTIVE")) {
                        this.currNode.setState(NodeState.ACTIVE);
                    } else if (pValue.equals("INACTIVE")) {
                        this.currNode.setState(NodeState.INACTIVE);
                    } else if (pValue.equals("REAL")) {
                        this.currNode.setState(NodeState.REAL);
                    } else {
                        this.currNode.setState(NodeState.ACTIVE);
                    }
                } else if (pProperty.equals("organization")) {
                    ContactInfo info = this.currNode.getContactInfo();
                    info.setOrganization(pValue);
                    this.currNode.setContactInfo(info);
                } else if (pProperty.equals("contact-person")) {
                    ContactInfo info = this.currNode.getContactInfo();
                    info.setContactPerson(pValue);
                    this.currNode.setContactInfo(info);
                } else if (pProperty.equals("contact-phone")) {
                    ContactInfo info = this.currNode.getContactInfo();
                    info.setContactPhone(pValue);
                    this.currNode.setContactInfo(info);
                } else if (pProperty.equals("LUUID")) {
                    this.currListener.setCID(UUID.fromString(pValue));
                } else if (pProperty.equals("lip-address") ||
                		   pProperty.equals("iip-address")) {
                    this.channelAddr = InetAddress.getByName(pValue);
                } else if (pProperty.equals("lport") ||
                		   pProperty.equals("iport")) {
                    this.channelPort = Integer.parseInt(pValue);
                } else if (pProperty.equals("IUUID")) {
                    this.currInitiator.setCID(UUID.fromString(pValue));
                } else if (pProperty.equals("remote-ip")) {
                	this.remoteAddr = InetAddress.getByName(pValue);
                } else if (pProperty.equals("remote-port")) {
                    this.remotePort = Integer.parseInt(pValue);
                } else if (pProperty.equals("transport-protocol")) {
                	this.channelTransport = pValue;
                } else if (pProperty.equals("application-protocol")) {
                	this.channelApplication = pValue;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initTags() {
        // Initialize list of tag with values
        this.tagValueList.add("NUUID");
        this.tagValueList.add("name");
        this.tagValueList.add("operating-system");
        this.tagValueList.add("required-software");
        this.tagValueList.add("external-ip");
        this.tagValueList.add("internal-ip");
        this.tagValueList.add("security-zone");
        this.tagValueList.add("status");
        this.tagValueList.add("organization");
        this.tagValueList.add("contact-person");
        this.tagValueList.add("contact-phone");
        this.tagValueList.add("LUUID");
        this.tagValueList.add("lip-address");
        this.tagValueList.add("lport");
        this.tagValueList.add("IUUID");
        this.tagValueList.add("iip-address");
        this.tagValueList.add("iport");
        this.tagValueList.add("remote-ip");
        this.tagValueList.add("remote-port");
        this.tagValueList.add("transport-protocol");
        this.tagValueList.add("application-protocol");

        // Initialize list of tags that are containers
        this.tagContainerList.add("networkconfig");
        this.tagContainerList.add("node");
        this.tagContainerList.add("listener");
        this.tagContainerList.add("initiator");
    }
    
    private void clearElementVariables(Channel pChannel) {
    	pChannel = null;
        this.channelAddr = null;
        this.remoteAddr = null;
        this.channelPort = 0;
        this.channelTransport = new String();
        this.channelApplication = new String();
    }
}