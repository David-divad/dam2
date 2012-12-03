package org.deiv.javanet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * 2 - Programa en Java que establece una conexion en un ordenador remoto mediante sockets.
 *     El programa tiene como parametros de entrada la direccion IP del ordenador remoto y un
 *     numero de puerto. Si consigue conectarse debe imprimir por la salida estandar los
 *     nombres de la maquina y el puerto de la maquina remota, asi como los de la maquina
 *     y el puerto desde donde de sonecte. El programa debe tratar las excepciones 
 *     UnknownHostException y SocketException.
 */
public class Javanet_E02 {
	public static void main(String[] args)
	{
		if (args.length != 2) {
			System.err.println("error: numero de parametros incorrecto");
			System.out.println("uso: InformacionHost host puerto");
			return;
		}
		
		String host = args[0];
		int puerto = Integer.parseInt(args[1]);
		
		try {
			conectarRemoto(host, puerto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void conectarRemoto(String hostname, int puerto) throws IOException
	{
		InetAddress ip;
		
		try {
			ip = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			System.err.format("imposible resolver el host: %s, ¿existe?\n", hostname);
			return;
		}
		
		InetSocketAddress socketAddr = new InetSocketAddress(ip, puerto);
		Socket sock = new Socket();
		
		try {
			sock.connect(socketAddr);
			
			System.out.format("Maquina remota, nombre host: %s, puerto: %s\n", 
					socketAddr.getHostName(), 
					socketAddr.getPort());
			System.out.format("Maquina local, nombre host: %s, puerto: %s\n",
					InetAddress.getLocalHost().getHostName(), 
					sock.getLocalPort());
		
		} catch (SocketException e) {
			System.err.format("imposible conectar al host: %s, puerto: %s\n", hostname, puerto);
			return;
			
		} catch (IOException e) {
			System.err.format("error E/S, imposible conectar al host: %s, puerto: %s\n", hostname, puerto);
			return;
		
		} finally {
			sock.close();
		}
	}
}

