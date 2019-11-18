import java.sql.*;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import templates.Server;
import connectors.ServerDatabaseConnector;
import readers.XMLReader;

///TODO
//ADD UNIT TESTS
//IMPLIMENT SINGLETON ON READERS AND CONNECTORS
//COMMENT AND CLEAN 

public class MainApp
{
	static Scanner m_scanner = new Scanner(System.in);
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
    {
		boolean running = true;
		XMLReader xmlReader = new XMLReader();
		Map<String,String> dbCreds = xmlReader.readNode("NTT_DB_Credentials", "database", "mysql", Arrays.asList("host", "port", "database", "username", "password"));
		String conUrl = "jdbc:" + dbCreds.get("database") + "://" + dbCreds.get("host") + ":" +  dbCreds.get("port") + "/" +  dbCreds.get("database");
		ServerDatabaseConnector dbCon = new ServerDatabaseConnector(conUrl,dbCreds.get("username"),dbCreds.get("password"));  
		showHelp();
	
		while(running)
		{
	        String option = m_scanner.nextLine().replaceAll("\\s", "");	    
		    if(option.equalsIgnoreCase("help"))
		    {
		    	showHelp();
	    	}
		    else if(option.equalsIgnoreCase("quit"))
		    {
		    	running = false;
		    }
		    else if(option.equalsIgnoreCase("countServers"))
		    {
	              System.out.println(dbCon.countServers() + " servers availible"); 
		    }
		    else if(option.equalsIgnoreCase("addServer"))
		    {
		    	System.out.println("Choose a server name");
		    	String serverName = m_scanner.nextLine();
		    	templates.Server server = new Server(dbCon.getNextServerId(), serverName);
		    	System.out.println(dbCon.addServer(server) + " server(s) added"); 
		    }
		    else if(option.equalsIgnoreCase("deleteServer"))
		    {
		    	System.out.println("Choose a server name");
		    	String serverName = m_scanner.nextLine();
		    	System.out.println(dbCon.removeServer(serverName) + " server(s) removed"); 
		    }
		    else if(option.equalsIgnoreCase("editServer"))
		    {
		    	System.out.println("Name server to edit");
		    	String oldServerName = m_scanner.nextLine();
		    	System.out.println("Select new server name");
		    	String newServerName = m_scanner.nextLine();
		    	dbCon.editServerName(oldServerName, newServerName);
		    	System.out.println("Server name updated"); 
		    }
		    else if(option.equalsIgnoreCase("listServers"))
		    {
		    	System.out.println("Current server list:");
		    	for(Server server : dbCon.listServers())
		    		System.out.println(server.getName());
		    	
		    }
		    else {
		    	System.out.println("Command not reconised, please choose a listed command");
		    	showHelp();
		    }
		}
    }

    private static void showHelp()
    {
    	System.out.println("Choose from the options below by typing the command word:");
		System.out.println("help - to display this message");
		System.out.println("countServers - to display the current number of servers present");
		System.out.println("addServer - to add a new server");
		System.out.println("editServer - to change the name of a server identified by id (takes 2 additional args - the id and the new name)");
		System.out.println("deleteServer - to delete a server (takes one more arg - the id of the server to delete)");
		System.out.println("listServers - to display details of all servers servers");
    }
}
