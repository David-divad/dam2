package dd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LeeFichero01 {


	public static void main(String[] args)
	{
		BufferedReader inputReader = null;
		String cwd = System.getProperty("user.dir");
		
		try {
			inputReader = new BufferedReader(new FileReader(cwd + "/prueba.txt"));
			
			String line = inputReader.readLine();
				
			while (line != null) {
				System.out.println(line);
					
				line = inputReader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
				
		} finally {
			try {
				if (inputReader != null) inputReader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} 
		}
	}
}
