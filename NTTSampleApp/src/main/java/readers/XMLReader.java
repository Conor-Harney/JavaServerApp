package readers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.List;

public class XMLReader {
	public Map<String,String> readNode(String resourceFileName, String dataSetType, String datasetId, List<String> tagNames)
	{
		Map<String,String> credMap = new HashMap<String,String>();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			
	        //URL resource = classLoader.getResource("xml/NTT_DB_Credentials.xml");
	        URL resource = classLoader.getResource("xml/"+ resourceFileName +".xml");
			File credsFile = new File(resource.getFile());
	        DocumentBuilderFactory docBuilderFac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFac.newDocumentBuilder();
	        Document doc = docBuilder.parse(credsFile);
	        //NodeList nttDatabases = doc.getElementsByTagName("database");
	        NodeList nttDatabases = doc.getElementsByTagName(dataSetType);
	        for(int i = 0; i< nttDatabases.getLength(); i++)
	        {
	        	if(nttDatabases.item(i).getNodeType() == Node.ELEMENT_NODE)
	        	{
	        		Element dbEle = (Element)(nttDatabases.item(i));
	        		//if(dbEle.getAttribute("id").equals("mysql"))
	        		if(dbEle.getAttribute("id").equals(datasetId))
	        		{
	        			for(String tagName : tagNames)
	        			{
	        				credMap.put(tagName, ((Element) dbEle.getElementsByTagName(tagName).item(0)).getTextContent());
	        			}
	        			/*credMap.put("host", ((Element) dbEle.getElementsByTagName("host").item(0)).getTextContent());
	        			credMap.put("port", ((Element) dbEle.getElementsByTagName("port").item(0)).getTextContent());
	        			credMap.put("database", ((Element) dbEle.getElementsByTagName("database").item(0)).getTextContent());
	        			credMap.put("username", ((Element) dbEle.getElementsByTagName("username").item(0)).getTextContent());
	        			credMap.put("password", ((Element) dbEle.getElementsByTagName("password").item(0)).getTextContent());*/
	        		}
	        	}
	        }
		}
		catch (Exception e) 
		{
	         e.printStackTrace();
	    }
		return credMap;
	}

}
