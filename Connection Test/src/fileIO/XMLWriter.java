package fileIO;

//import javax.print.Doc;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Attr;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;

import simulator.Initiator;
import simulator.Listener;
import simulator.Node;

public class XMLWriter
{
	public XMLWriter(){};
	
	public String writeXML(NetworkConfig currConfig)
	{
		String xmlStr = new String("");
		// TODO this is actually part of the node
		xmlStr += "<?xml version=\"1.0\"?>\r\n<networkconfig version=\"1.0\">\r\n";
		
		for (Node currNode : currConfig.nodes)
		{
			xmlStr += "\t<node>\r\n";
			xmlStr += "\t\t<NUUID>" + currNode.getNID().toString() + "</NUUID>\r\n";
			xmlStr += "\t\t<name>" + currNode.getName() + "</name>\r\n";
			xmlStr += "\t\t<operating-system>" + currNode.getOperatingSystem() + "</operating-system>\r\n";
			xmlStr += "\t\t<required-software>" + currNode.getRequiredSoftware() + "</required-software>\r\n";
			
			ArrayList<InetAddress> aExternalIPs = currNode.getExternalIPs();
			for(InetAddress currExternalIP : aExternalIPs)
			{
				xmlStr += "\t\t<external-ip>" + currExternalIP.getHostAddress() + "</external-ip>\r\n";
			}
			
			ArrayList<InetAddress> aInternalIPs = currNode.getInternalIPs();
			for (InetAddress currInternalIP : aInternalIPs)
			{
				xmlStr += "\t\t<internal-ip>" + currInternalIP.getHostAddress() + "</internal-ip>\r\n";
			}
			
			xmlStr += "\t\t<security-zone>" + currNode.getSecurityZone() + "</security-zone>\r\n";
			xmlStr += "\t\t<status></status>\r\n";  // + currNode.getState() +
			xmlStr += "\t\t<contact-info>\r\n";
			xmlStr += "\t\t\t<organization>" + currNode.getContactInfo().getOrganization() + "</organization>\r\n";
			xmlStr += "\t\t\t<contact-person>" + currNode.getContactInfo().getContactPerson() + "</contact-person>\r\n";
			xmlStr += "\t\t\t<contact-phone>" + currNode.getContactInfo().getContactPhone() + "</contact-phone>\r\n";
			xmlStr += "\t\t</contact-info>\r\n";
			
			ArrayList<Listener> aListeners = currNode.getListeners();
			for (Listener currListener : aListeners)
			{
				xmlStr += "\t\t<listener>\r\n";
				xmlStr += "\t\t\t<LUUID>" + currListener.getCID() + "</LUUID>\r\n";
				xmlStr += "\t\t\t<lip-address>" + currListener.getListenerAddr() + "</lip-address>\r\n";
				xmlStr += "\t\t\t<lport>" + currListener.getListenerPort() + "</lport>\r\n";
				xmlStr += "\t\t</listener>\r\n";
			}
			
			ArrayList<Initiator> aInitiators = currNode.getInitiators();
			for (Initiator currInitiator : aInitiators)
			{
			xmlStr += "\t\t<initiator>\r\n";
			xmlStr += "\t\t\t<IUUID>" + currInitiator.getCID() + "</IUUID>\r\n";
			xmlStr += "\t\t\t<iip-address>" + currInitiator.getInitiatorAddr() + "</iip-address>\r\n";
			xmlStr += "\t\t\t<iport>" + currInitiator.getInitiatorPort() + "</iport>\r\n";
			xmlStr += "\t\t</initiator>\r\n";
			}
			
			// TODO these are actually part of the channel
			Initiator tempInitiator = aInitiators.get(0);
			xmlStr += "\t\t<transport-protocol>" + tempInitiator.getTransportProtocol() + "</transport-protocol>\r\n";
			xmlStr += "\t\t<application-protocol>" + tempInitiator.getApplicationProtocol() + "</application-protocol>\r\n";
			
			xmlStr += "\t</node>\r\n";
		}
		
		xmlStr += "</networkconfig>";
		
		return xmlStr;
	}
		// maybe later
//		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder docBuilder;
//		
//		try
//		{
//			docBuilder = docFactory.newDocumentBuilder();
//			
//			Document doc = docBuilder.newDocument();
//			Element rootElement = doc.createElement("networkconfig");
//			doc.appendChild(rootElement);
//			
//			// for each node
//			Element node = doc.createElement("node");
//			rootElement.appendChild(node);
//			
//			Element name = doc.createElement("name");
//			name.setTextContent("Node 1");
//			node.appendChild(name);
//			// end for each
//			xmlStr = doc.toString();
			
			// write xml
//			TransformerFactory xformerFactory = TransformerFactory.newInstance();
//			Transformer xformer = xformerFactory.newTransformer();
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File(pFileName));
			
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			//xformer.transform(source, result);
	 
			//System.out.println("File: " + pFileName + " saved!");
//		}
//		catch (ParserConfigurationException pce)
//		{
//			pce.printStackTrace();
//		}
//		catch (TransformerException tfe)
//		{
//			tfe.printStackTrace();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		
	
}