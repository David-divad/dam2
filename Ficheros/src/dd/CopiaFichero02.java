package dd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopiaFichero02 {
	
	public static void main(String[] args)
	{
		BufferedReader inputReader  = null;
		BufferedWriter outputWriter = null;
		
		String cwd = System.getProperty("user.dir");
		
		try {
			inputReader  = new BufferedReader(new FileReader(cwd + "/prueba.txt"));
			outputWriter = new BufferedWriter(new FileWriter(cwd + "/copia-prueba.txt"));
			
			String line = inputReader.readLine();
				
			while (line != null) {
				outputWriter.write(line + "\n");
					
				line = inputReader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
				
		} finally {
			try {
				if (inputReader != null) inputReader.close();
				if (outputWriter != null) outputWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} 
		}
	}
}
