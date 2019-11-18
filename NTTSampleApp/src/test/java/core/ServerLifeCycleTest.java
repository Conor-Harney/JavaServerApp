package core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connectors.ServerDatabaseConnector;
import readers.XMLReader;
import templates.Server;


public class ServerLifeCycleTest {
	@BeforeClass
    public static void BeforeClass() {
        System.out.println("Before Class");
        Map<String,String> dbCreds = XMLReader.getInstance().readNode("NTT_DB_Credentials", "database", "mysql", Arrays.asList("host", "port", "database", "username", "password"));
		String conUrl = "jdbc:" + dbCreds.get("database") + "://" + dbCreds.get("host") + ":" +  dbCreds.get("port") + "/" +  dbCreds.get("database");
		ServerDatabaseConnector.getInstance(conUrl,dbCreds.get("username"),dbCreds.get("password"));
    }

    @Before
    public void before() {
        System.out.println("Before");
    }
    
    @Test
    public void testAddingServer() {
    	System.out.println("Test");
    	int serverId = ServerDatabaseConnector.getInstance().getNextServerId();
    	assertNotNull(serverId);
    	String ServerName = "TestServer" + String.valueOf(serverId);
    	Server serverToCreate = new Server(serverId, ServerName);
    	int serversBeforeCount = ServerDatabaseConnector.getInstance().countServers();
    	ServerDatabaseConnector.getInstance().addServer(serverToCreate);
    	assertTrue(ServerDatabaseConnector.getInstance().countServers() > serversBeforeCount);
    	boolean foundServer = false;
    	for(Server server : ServerDatabaseConnector.getInstance().listServers())
    	{
    		if(server.compare(serverToCreate)) foundServer = true;
    	}
    	assertTrue(foundServer);//server was added
    	
    	serverToCreate.setName(serverToCreate.getName() + "-2");
    	ServerDatabaseConnector.getInstance().editServerById(serverToCreate.getId(), serverToCreate.getName());
    	foundServer = false;
    	for(Server server : ServerDatabaseConnector.getInstance().listServers())
    	{
    		if(server.compare(serverToCreate)) foundServer = true;
    	}
    	assertTrue(foundServer);//server name was edited
    	
    	ServerDatabaseConnector.getInstance().removeServerById(serverToCreate.getId());
    	foundServer = false;
    	for(Server server : ServerDatabaseConnector.getInstance().listServers())
    	{
    		if(server.compare(serverToCreate)) foundServer = true;
    	}
    	assertFalse(foundServer);//server name was removed
    	
    }

    @After
    public void after() {
        System.out.println("After");
    }

    @AfterClass
    public static void AfterTest() {
        System.out.println("AfterClass");
    }
}
