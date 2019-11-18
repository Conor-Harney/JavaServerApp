package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
	private static FileReader m_instance = null; 

	public static FileReader getInstance()
	{
		if (m_instance == null) 
        { 
			m_instance = new FileReader(); 
        } 
        return m_instance; 
	}
	
	private FileReader()
	{
		
	}
	
	public String getFirstLine(String relativePath, String fileName)
	{
		String firstLine = null;
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(getStream(relativePath, fileName));
			if(fileScanner.hasNext())
			{
				firstLine = fileScanner.nextLine();
			}
			fileScanner.close();
		}
		finally
		{
			if(fileScanner != null)
			{
				fileScanner.close();
			}
		}
		return firstLine;
	}
	
	public List<String>getAllLines(String relativePath, String fileName)
	{
		@SuppressWarnings("serial")
		List<String> retList = new ArrayList<String>();
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(getStream(relativePath, fileName));
			while(fileScanner.hasNext())
			{
				retList.add(fileScanner.nextLine());
			}
			fileScanner.close();
		}
		finally
		{
			if(fileScanner != null)
			{
				fileScanner.close();
			}
		}
		return retList;
	}
	
	public InputStream getStream(String relativePath, String fileName)
	{
		try {
			return (getClass().getClassLoader().getResource((relativePath + fileName)).openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
