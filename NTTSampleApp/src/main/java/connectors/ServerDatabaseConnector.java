package connectors;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import readers.FileReader;
import templates.Server;

public class ServerDatabaseConnector extends DatabaseConnector 
{
	
	private static ServerDatabaseConnector m_instance = null;
	
	public static ServerDatabaseConnector getInstance()
	{
		if(m_initialized)
		{
			if (m_instance == null) 
	        { 
				m_instance = new ServerDatabaseConnector(); 
	        } 
	        return m_instance; 
		}
		else
		{
			return null;
		}
	}
	
	public static ServerDatabaseConnector getInstance(String connectionUrl, String connectionUser, String connectionPassword)
	{
		init(connectionUrl, connectionUser, connectionPassword);
        return getInstance(); 
	}
	
	protected static void init(String connectionUrl, String connectionUser, String connectionPassword)
	{
		DatabaseConnector.init(connectionUrl,connectionUser, connectionPassword);
	}
	
	private ServerDatabaseConnector() 
	{
	}
	
	public int countServers()
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "countServers.sql"));
		return Math.toIntExact((Long) super.executeQuery(statement).get(0).get("count"));
	}
	
	public int addServer(Server server) 
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "addServer.sql"));
		statement = statement.replace("${id}",  String.valueOf(server.getId())).replace("${name}",  String.valueOf(server.getName()));
		return super.executeUpdate(statement);
	}
	
	public int removeServerByName(String serverName)
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "removeServerByName.sql"));
		statement = statement.replace("${serverName}",  serverName);
		return super.executeUpdate(statement);
	}
	
	public int removeServerById(int serverId)
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "removeServerById.sql"));
		statement = statement.replace("${serverId}",  String.valueOf(serverId));
		return super.executeUpdate(statement);
	}
	
	public int editServerByName(String oldServerName, String newServerName) 
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "editServerByName.sql"));
		statement = statement.replace("${oldName}",  oldServerName).replace("${newName}",  newServerName);
		return super.executeUpdate(statement);
	}
	
	public int editServerById(int id, String newServerName) 
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "editServerById.sql"));
		statement = statement.replace("${id}", String.valueOf(id)).replace("${newName}",  newServerName);
		return super.executeUpdate(statement);
	}
	
	public List<Server> listServers() 
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "listServers.sql"));
		return mapServerResultSet(super.executeQuery(statement));
	}
	
	public int getNextServerId() 
	{
		String statement = (FileReader.getInstance().getFirstLine("sql/", "getNextServerId.sql"));
		return Math.toIntExact((Long) super.executeQuery(statement).get(0).get("nextid"));
	}
	
	protected List<Server> mapServerResultSet(ResultSet resultSet)
	{
		return mapServerResultSet(super.mapResultSet(resultSet));
	}
	
	protected List<Server> mapServerResultSet(List<Map<String,Object>> mapList)
	{
		List<Server> serverList = new ArrayList<Server>(mapList.size());
		for(Map<String,Object> serverMap : mapList)
		{
			Server server = new Server( (Integer)serverMap.get("ID"), (String)serverMap.get("NAME"));
			serverList.add(server);
		}
		return serverList;
	}
}
