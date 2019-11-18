package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
	public String getFirstLine(String relativePath, String fileName)
	{
		String firstLine = null;
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(getFile(relativePath, fileName));
			if(fileScanner.hasNext())
			{
				firstLine = fileScanner.nextLine();
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		List<String> retList = new ArrayList<String>() {
		};
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(getFile(relativePath, fileName));
			while(fileScanner.hasNext())
			{
				retList.add(fileScanner.nextLine());
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public File getFile(String relativePath, String fileName)
	{
		return new File(getClass().getClassLoader().getResource((relativePath + fileName)).getFile());
	}

}
