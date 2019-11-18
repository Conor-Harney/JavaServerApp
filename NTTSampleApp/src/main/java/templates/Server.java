package templates; 

public class Server
{
	private int m_id;
	private String m_name;
	
	public Server(){}

	public Server(int id, String name){
		this.m_id = id;
		this.m_name = name;
	}
	
	public int getId() {
		return m_id;
	}

	public void setId(int id) {
		this.m_id = id;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		this.m_name = name;
	}
}