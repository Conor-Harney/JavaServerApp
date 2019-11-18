import java.lang.*;
import java.sql.*;
import java.io.*;

public class MainApp
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
    {
	boolean running = true;

	showHelp();

	while(running)
	{
            DataInputStream in =  new DataInputStream(System.in);
            String option = in.readLine();	    
		
	    if(option.equals("help"))
	    {
		showHelp();
    	    }
	    else if(option.equals("quit"))
	    {
		running = false;
	    }
	    else if(option.equals("countServers"))
	    {
              // TODO implement...
	    }
	    else if(option.equalsIgnoreCase("addServer"))
	    {

              // TODO implement...
	    }
	    else if(option.equalsIgnoreCase("deleteServer"))
	    {
              // TODO implement...
	    }
	    else if(option.equalsIgnoreCase("editServer"))
	    {
              // TODO implement...
	    }
	    else if(option.equalsIgnoreCase("listServers"))
	    {
              // TODO implement...
	    }
	}
    }

    private static void showHelp()
    {
	System.out.println("help to display this message");
	System.out.println("countServers to display the current number of servers present");
	System.out.println("addServer to display the current number of servers present");
	System.out.println("editServer to change the name of a server identified by id (takes 2 additional args - the id and the new name)");
	System.out.println("deleteServer to delete a server (takes one more arg - the id of the server to delete)");
	System.out.println("listServers to display details of all servers servers");
    }
}