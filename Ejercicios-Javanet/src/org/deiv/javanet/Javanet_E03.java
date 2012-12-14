package org.deiv.javanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 3 - Programa en java que implementa un cliente que se conecta al servidor indicando la maquina donde
 *     se encuentra el servidor y el puerto 6000 por el que esta escuchando. Una vez conectado, lee una
 *     cadena del servidor y la escribe en la salida estandar.
 *     
 *     David Suárez Rodríguez
 */
public class Javanet_E03 {
	
	static final protected int PUERTO_SERVIDOR = 6000;
	static final protected int READ_TIMEOUT = 5000;
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun nombre de host");
			System.out.println("uso: conecta_servidor host");
		}
		
		String hostname = args[0];
		
		try {
			conectaServidor(hostname);
			
		} catch (UnknownHostException e) {
			System.err.format("Imposible resolver el host: %s, ¿existe?\n", hostname);
			
		} catch (IOException e) {

			System.err.format("Imposible conectar al host: %s\n", hostname);
		}
	}

	public static void conectaServidor(String hostname) throws IOException 
	{

		
		InetAddress ip = InetAddress.getByName(hostname);	
		InetSocketAddress socketAddr = new InetSocketAddress(ip, PUERTO_SERVIDOR);
		Socket sock = new Socket();
		
		sock.connect(socketAddr);

		BufferedReader in = null;
		
		/* Leemos la respuesta */
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			/* esperamos un maximo de tiempo, si no hay respuesta volvemos */
			sock.setSoTimeout(READ_TIMEOUT);
			
			String linea = in.readLine();

			System.out.println(linea);
			
		} finally {
			if (in != null)
				in.close();
			sock.close();
		}
	}
}
