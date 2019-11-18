package connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnector {
	protected String m_connectionUrl, m_connectionUser, m_connectionPassword;
	protected static String m_driver = "com.mysql.cj.jdbc.Driver";
	
	protected Connection openConnection()
	{
		Connection con = null;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(m_connectionUrl, m_connectionUser, m_connectionPassword);
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
		return con;
	}
	
	public DatabaseConnector(String connectionUrl, String connectionUser, String connectionPassword)
	{
		m_connectionUrl = connectionUrl;
		m_connectionUser = connectionUser;
		m_connectionPassword = connectionPassword;
		try {
			Class.forName(m_driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean testConnection()
	{
		boolean connected = false;
		try 
		{
			Connection con = openConnection();
			con.close(); 
			connected = true;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}  
		
		return connected;
	}
	
	public List<Map<String,Object>> executeQuery(String query)
	{
		List<Map<String,Object>> returnList = null;
		Statement statement;
		try {
			Connection con = openConnection();
			statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			returnList = mapResultSet(resultSet);  
			con.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnList;
	}
	
	public int executeUpdate(String query)
	{
		int returnInt = 0;
		Statement statement;
		try {
			Connection con = openConnection();
			statement = con.createStatement();
			returnInt = statement.executeUpdate(query);  
			con.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnInt;
	}


	public void setConnectionPassword(String m_connectionPassword) {
		this.m_connectionPassword = m_connectionPassword;
	}


	public String getConnectionUser() {
		return m_connectionUser;
	}


	public void setConnectionUser(String m_connectionUser) {
		this.m_connectionUser = m_connectionUser;
	}


	public String getConnectionUrl() {
		return m_connectionUrl;
	}


	public void setConnectionUrl(String m_connectionUrl) {
		this.m_connectionUrl = m_connectionUrl;
	}
	
	protected List<Map<String,Object>> mapResultSet(ResultSet resultSet)
	{
		List<Map<String,Object>> rowList = null;
		
		try {
			rowList = new ArrayList<Map<String, Object>>(resultSet.getMetaData().getColumnCount());
			resultSet.beforeFirst();
			while(resultSet.next())
			{
				Map<String,Object> resultMap = new HashMap<String, Object>();
				for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
				{
					resultMap.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
				}
				rowList.add(resultMap);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowList;
		
	}
}
