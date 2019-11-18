package main;
import java.sql.*;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import templates.Server;
import connectors.ServerDatabaseConnector;
import readers.FileReader;
import readers.XMLReader;


public class MainApp
{
	static Scanner m_scanner = new Scanner(System.in);
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
    {
		boolean running = true;
		init();
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
	              System.out.println(ServerDatabaseConnector.getInstance().countServers() + " servers availible"); 
		    }
		    else if(option.equalsIgnoreCase("addServer"))
		    {
		    	System.out.println("Choose a server name");
		    	String serverName = m_scanner.nextLine();
		    	System.out.println("Choose a unique server id (numbers only)");
		    	int serverId = Integer.parseInt(m_scanner.nextLine());
		    	templates.Server server = new Server(serverId, serverName);
		    	System.out.println(ServerDatabaseConnector.getInstance().addServer(server) + " server(s) added"); 
		    }
		    else if(option.equalsIgnoreCase("generateServer"))
		    {
		    	System.out.println("Choose a server name");
		    	String serverName = m_scanner.nextLine();
		    	templates.Server server = new Server(ServerDatabaseConnector.getInstance().getNextServerId(), serverName);
		    	System.out.println(ServerDatabaseConnector.getInstance().addServer(server) + " server(s) added"); 
		    }
		    else if(option.equalsIgnoreCase("deleteServer"))
		    {
		    	System.out.println("Choose a server id to delete");
		    	int serverId = Integer.parseInt(m_scanner.nextLine());
		    	System.out.println(ServerDatabaseConnector.getInstance().removeServerById(serverId) + " server(s) removed"); 
		    }
		    else if(option.equalsIgnoreCase("editServer"))
		    {
		    	System.out.println("Enter the id of the server to edit (Numbers only)");
		    	String serverId = m_scanner.nextLine();
		    	System.out.println("Select new server name");
		    	String newServerName = m_scanner.nextLine();
		    	ServerDatabaseConnector.getInstance().editServerById(Integer.parseInt(serverId), newServerName);
		    	System.out.println("Server name updated"); 
		    }
		    else if(option.equalsIgnoreCase("listServers"))
		    {
		    	System.out.println("Current server list:");
		    	String outputFormat = FileReader.getInstance().getFirstLine("txt/", "serverDetail.txt");
		    	for(Server server : ServerDatabaseConnector.getInstance().listServers())
		    	{
		    		System.out.println(outputFormat.replace("${serverId}", String.valueOf(server.getId())).replace("${serverName}", server.getName()));
		    	}
		    }
		    else 
		    {
		    	for (String line : FileReader.getInstance().getAllLines("txt/", "commandNotRecognised.txt"))
		    	{
		    		System.out.println(line);
		    	}
		    	showHelp();
		    }
		}
    }

    private static void showHelp()
    {
    	System.out.println("");
    	for (String line : FileReader.getInstance().getAllLines("txt/", "help.txt"))
    	{
    		System.out.println(line);
    	}
    	System.out.println("");
    }
    
    private static void init()
    {
    	Map<String,String> dbCreds = XMLReader.getInstance().readNode("NTT_DB_Credentials", "database", "mysql", Arrays.asList("host", "port", "database", "username", "password"));
		String conUrl = "jdbc:" + dbCreds.get("database") + "://" + dbCreds.get("host") + ":" +  dbCreds.get("port") + "/" +  dbCreds.get("database");
		ServerDatabaseConnector.getInstance(conUrl,dbCreds.get("username"),dbCreds.get("password"));
    }
}
