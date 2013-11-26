package fileIO;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

import simulator.Node;


public class NetworkConfig {
    
	public ArrayList<Node> nodes;

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
}