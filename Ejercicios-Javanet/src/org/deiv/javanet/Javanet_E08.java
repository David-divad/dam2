package org.deiv.javanet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 8 - Escribe una aplicacion cliente/servidor usando sockets orientados a conexion
 *     (Stream Sockets). El cliente envia dos enteros al servidor y el servidor le
 *     contesta con el resultado de la suma de dos enteros.
 */
public class Javanet_E08 {
	
	static final String HOST = "localhost";
	static final int PUERTO = 9000;
	
	public static void main(String[] args)
	{
		Servidor servidor = new Servidor(PUERTO);
		Cliente cliente = new Cliente();
	    
		try {
			/* iniciamos el servidor en un hilo aparte */
			Thread hilo = new Thread(servidor);
	        hilo.start();  
	          
			cliente.iniciar(HOST, PUERTO);
			
			servidor.parar();
			hilo.interrupt();
			hilo.join();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Cliente {

	Socket socket;
	DataOutputStream salida;
	BufferedReader entrada;

	public void iniciar(String host, int puerto) throws IOException, InterruptedException
	{

		try {
			System.out.format("cliente: conectando a %s\n", 
					host);
			
			socket = new Socket(host, puerto);
			salida = new DataOutputStream(socket.getOutputStream());
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			salida.write(2);
			salida.write(2);
			salida.flush();
			System.out.println("cliente: enviado 2 2");

			int respuesta = entrada.read();
						
			System.out.format("cliente: recibido: %d\n", respuesta);

		} catch (IOException e) {
			System.err.println("cliente: error de entrada/salida");
			
		} finally {
			socket.close();
		}

	}

}


class Servidor implements Runnable {
	
	protected final int puerto;
	protected boolean corriendo = false;
	
	public Servidor(int puerto)
	{
		this.puerto = puerto;
	}
	
	public void iniciar(int puerto) throws IOException
	{
		ServerSocket socketServidor = null;
		Socket socketCliente;
		DataOutputStream salida;
		BufferedReader entrada;
		
		int op1, op2;
		
		try {

			socketServidor = new ServerSocket(puerto);
			socketCliente = new Socket();

			System.out.println("servidor: esperando conexion");
			
			socketCliente = socketServidor.accept();
			
			System.out.format("servidor: el cliente %s, se ha conectado\n", 
					socketCliente.getInetAddress().getHostName());
			
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new DataOutputStream(socketCliente.getOutputStream());
			
			op1 = entrada.read();
			op2 = entrada.read();

			System.out.format("servidor: recibido %d %d\n", op1, op2);
			int suma = op1 + op2;
						
			salida.write(suma);
			salida.flush();
			
			System.out.format("servidor: enviado %d\n", suma);

			entrada.close();
			salida.close();
			socketCliente.close();
			
		} catch (IOException e) {
			System.err.println("servidor: error de entrada/salida");
			
		} finally {
			socketServidor.close();
		}

	}
	
	public void parar()
	{
		corriendo = false;
	}

	@Override
	public void run()
	{
		corriendo = true;
		
		try {
			while (corriendo) {
				this.iniciar(puerto);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
