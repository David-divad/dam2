package org.deiv.javanet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 2 - Programa en Java que establece una conexion en un ordenador remoto mediante sockets,
 *     debe imprimir por la salida estandar los nombres de la maquina y el puerto de la maquina remota,
 *     asi como los de la maquina y el puerto desde donde se conecte. El programa debe tratar las
 *     excepciones UnknownHostException y SocketException.
 */
public class Javanet_E02 {
	public static void main(String[] args)
	{
		String hostname = "google.es";
		
		try {
			printPeticionHttp(hostname, "/");
			
		} catch (UnknownHostException e) {
			System.err.format("Imposible resolver el host: %s, ¿existe?\n", hostname);
			
		} catch (IOException e) {

			System.err.format("Imposible conectar al host: %s\n", hostname);
		}
	}

	public static void printPeticionHttp(String hostname, String uri) throws IOException 
	{
		final int HTTP_PORT = 80;
		
		InetAddress ip = InetAddress.getByName(hostname);	
		InetSocketAddress socketAddr = new InetSocketAddress(ip, HTTP_PORT);
		Socket sock = new Socket();
		
		sock.connect(socketAddr);
		
		System.out.format("Maquina remota, nombre host: %s, puerto: %s\n", 
				socketAddr.getHostName(), 
				socketAddr.getPort());
		System.out.format("Maquina local, nombre host: %s, puerto: %s\n",
				sock.getLocalAddress().getHostName(), 
				sock.getLocalPort());
		
		sock.close();
	}
}
