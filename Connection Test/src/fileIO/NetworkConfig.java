package fileIO;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.ArrayList;

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
            System.out.println("Number of nodes loaded from xml file: " + nodes.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int numNodes() {
    	return(this.nodes.size());
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