import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class PeticionHttp {
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun nombre de host");
			System.out.println("uso: PeticionHttp host [, host, ...]");
		}
		
		for (String hostname : args) {

			try {
				printPeticionHttp(hostname, "/");
				
			} catch (UnknownHostException e) {
				System.err.format("Imposible resolver el host: %s, ¿existe?\n", hostname);
				
			} catch (IOException e) {
				System.err.format("Imposible leer desde el host: %s\n", hostname);
			}
		}
	}

	public static void printPeticionHttp(String hostname, String uri) throws IOException
	{
		final int HTTP_PORT = 80;
		final int READ_TIMEOUT = 5000;
		
		InetAddress ip = InetAddress.getByName(hostname);		
		Socket sock = new Socket(ip, HTTP_PORT);
		
		/* Enviamos la peticion HTTP */
		String peticion = String.format("GET %s HTTP/1.1\r\nHost: %s\r\nUser-Agent: java\r\n\r\n", uri, hostname);
		DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
		writer.writeBytes(peticion);
		writer.flush();
		sock.shutdownOutput();

		BufferedReader in = null;
		
		/* Leemos la respuesta */
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			/* esperamos un maximo de tiempo, si no hay respuesta volvemos */
			sock.setSoTimeout(READ_TIMEOUT);
			
			String linea = in.readLine();

			while (linea != null) {
				System.out.println(linea);
				linea = in.readLine();
			}
			
		} finally {
			if (in != null)
				in.close();
			writer.close();
			sock.close();
		}
	}
}
