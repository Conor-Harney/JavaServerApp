package connectors;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import readers.FileReader;
import templates.Server;

public class ServerDatabaseConnector extends DatabaseConnector {

	FileReader m_FileReader;
	
	public ServerDatabaseConnector(String connectionUrl, String connectionUser, String connectionPassword) {
		super(connectionUrl, connectionUser, connectionPassword);
		m_FileReader = new FileReader();
	}
	
	public int countServers()
	{
		String statement = (m_FileReader.getFirstLine("sql/", "countServers.sql"));
		return Math.toIntExact((Long) super.executeQuery(statement).get(0).get("count"));
	}
	
	public int addServer(Server server) 
	{
		String statement = (m_FileReader.getFirstLine("sql/", "addServer.sql"));
		statement = statement.replace("${id}",  String.valueOf(server.getId())).replace("${name}",  String.valueOf(server.getName()));
		return super.executeUpdate(statement);
	}
	
	public int removeServer(String serverName)
	{
		String statement = (m_FileReader.getFirstLine("sql/", "removeServer.sql"));
		statement = statement.replace("${serverName}",  serverName);
		return super.executeUpdate(statement);
	}
	
	public int editServerName(String oldServerName, String newServerName) 
	{
		String statement = (m_FileReader.getFirstLine("sql/", "editServer.sql"));
		statement = statement.replace("${oldName}",  oldServerName).replace("${newName}",  newServerName);
		return super.executeUpdate(statement);
	}
	
	public List<Server> listServers() {
		String statement = (m_FileReader.getFirstLine("sql/", "listServers.sql"));
		return mapServerResultSet(super.executeQuery(statement));
	}
	
	public int getNextServerId() 
	{
		String statement = (m_FileReader.getFirstLine("sql/", "getNextServerId.sql"));
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
