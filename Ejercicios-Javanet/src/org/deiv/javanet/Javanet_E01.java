package org.deiv.javanet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * 1 - Programa en Java que recibe un nombre de dominio correspondiente a un ordenador
 *     e imprime en la salida estandar su direccion ip.
 *     
 *     David Suárez Rodríguez
 */

public class Javanet_E01 {
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun nombre de host");
			System.out.println("uso: InformacionHost host [, host, ...]");
		}
		
		for (String hostname : args) {

			try {
				printHostIp(hostname);
				
			} catch (UnknownHostException e) {
				System.err.format("Imposible resolver el host: %s, ¿existe?", hostname);
			}
		}
	}

	public static void printHostIp(String hostname) throws UnknownHostException
	{
		InetAddress[] ips = InetAddress.getAllByName(hostname);
		
		System.out.format("Informacion para el host %s:\n", hostname);
		
		/* imprimimos todas la direcciones ip resueltas */
		for (InetAddress addr : ips) {
			System.out.format("\tdireccion ip: %s\n", addr.getHostAddress());
		}
		
		System.out.println("");
	}
}
