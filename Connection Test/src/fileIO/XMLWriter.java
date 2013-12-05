package fileIO;

import java.net.InetAddress;
import java.util.ArrayList;

import simulator.Initiator;
import simulator.Listener;
import simulator.Node;

public class XMLWriter
{
	public XMLWriter(){};
	String TAB = "   ";
	
	public String writeXML(NetworkConfig currConfig)
	{
		String xmlStr = new String("");
		Node currNode = null;
		String approval = new String("false");
		if (currConfig.isApproved()) {
			approval = new String("true");
		}
		xmlStr += "<?xml version=\"1.0\"?>\r\n<networkconfig version=\"" + currConfig.getFileVersion() + "\" approved=\"" + approval + "\">\r\n";
		
		for (int index = 0; index < currConfig.size(); index++)
		{
			currNode = currConfig.get(index);

			xmlStr += TAB + "<node>\r\n";
			xmlStr += TAB + TAB + "<NUUID>" + currNode.getNID().toString() + "</NUUID>\r\n";
			xmlStr += TAB + TAB + "<name>" + currNode.getName() + "</name>\r\n";
			xmlStr += TAB + TAB + "<operating-system>" + currNode.getOperatingSystem() + "</operating-system>\r\n";
			xmlStr += TAB + TAB + "<required-software>" + currNode.getRequiredSoftware() + "</required-software>\r\n";
			
			ArrayList<InetAddress> aExternalIPs = currNode.getExternalIPs();
			for(InetAddress currExternalIP : aExternalIPs)
			{
				xmlStr += TAB + TAB + "<external-ip>" + currExternalIP.getHostAddress() + "</external-ip>\r\n";
			}
			
			ArrayList<InetAddress> aInternalIPs = currNode.getInternalIPs();
			for (InetAddress currInternalIP : aInternalIPs)
			{
				xmlStr += TAB + TAB + "<internal-ip>" + currInternalIP.getHostAddress() + "</internal-ip>\r\n";
			}
			
			xmlStr += TAB + TAB + "<security-zone>" + currNode.getSecurityZone() + "</security-zone>\r\n";
			xmlStr += TAB + TAB + "<status>" + currNode.getState() + "</status>\r\n";
			xmlStr += TAB + TAB + "<contact-info>\r\n";
			xmlStr += TAB + TAB + TAB + "<organization>" + currNode.getContactInfo().getOrganization() + "</organization>\r\n";
			xmlStr += TAB + TAB + TAB + "<contact-person>" + currNode.getContactInfo().getContactPerson() + "</contact-person>\r\n";
			xmlStr += TAB + TAB + TAB + "<contact-phone>" + currNode.getContactInfo().getContactPhone() + "</contact-phone>\r\n";
			xmlStr += TAB + TAB + "</contact-info>\r\n";
			
			ArrayList<Listener> aListeners = currNode.getListeners();
			for (Listener currListener : aListeners)
			{
				xmlStr += TAB + TAB + "<listener>\r\n";
				xmlStr += TAB + TAB + TAB + "<LUUID>" + currListener.getCID() + "</LUUID>\r\n";
				xmlStr += TAB + TAB + TAB + "<lip-address>" + currListener.getListenerAddr().getHostAddress() + "</lip-address>\r\n";
				xmlStr += TAB + TAB + TAB + "<lport>" + currListener.getListenerPort() + "</lport>\r\n";
				xmlStr += TAB + TAB + TAB + "<transport-protocol>" + currListener.getTransportProtocol() + "</transport-protocol>\r\n";
				xmlStr += TAB + TAB + TAB + "<application-protocol>" + currListener.getApplicationProtocol() + "</application-protocol>\r\n";
				xmlStr += TAB + TAB + "</listener>\r\n";
			}
			
			ArrayList<Initiator> aInitiators = currNode.getInitiators();
			for (Initiator currInitiator : aInitiators)
			{
				xmlStr += TAB + TAB + "<initiator>\r\n";
				xmlStr += TAB + TAB + TAB + "<IUUID>" + currInitiator.getCID() + "</IUUID>\r\n";
				xmlStr += TAB + TAB + TAB + "<iip-address>" + currInitiator.getInitiatorAddr().getHostAddress() + "</iip-address>\r\n";
				xmlStr += TAB + TAB + TAB + "<iport>" + currInitiator.getInitiatorPort() + "</iport>\r\n";
				xmlStr += TAB + TAB + TAB + "<remote-ip>" + currInitiator.getListenerAddr().getHostAddress() + "</remote-ip>\r\n";
				xmlStr += TAB + TAB + TAB + "<remote-port>" + currInitiator.getListenerPort() + "</remote-port>\r\n";
				xmlStr += TAB + TAB + TAB + "<transport-protocol>" + currInitiator.getTransportProtocol() + "</transport-protocol>\r\n";
				xmlStr += TAB + TAB + TAB + "<application-protocol>" + currInitiator.getApplicationProtocol() + "</application-protocol>\r\n";
				xmlStr += TAB + TAB + "</initiator>\r\n";
			}
			
			xmlStr += TAB + "</node>\r\n";
		}
		
		xmlStr += "</networkconfig>";
		
		return xmlStr;
	}
	
}