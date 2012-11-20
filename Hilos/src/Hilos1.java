/**
 * Ejercicios sobre hilos:
 * 
 * 1 - Programa en java que muestra como cualquier programa en java se ejecuta mediante un hilo.
 *     En el programa se declara un objeto de tipo Thread y se recupera la referencia mediante el
 *     metodo currentThread.
 *  
 * David Suárez Rodríguez.
 * 
 */

public class Hilos1 {
	
	public static void main(String[] args)
	{
		Thread mi_hilo = Thread.currentThread();
		
		System.out.println("Mi hilo principal se llama: " + mi_hilo.getName());
		System.out.println("Mi id es: " + mi_hilo.getId());
		System.out.println("Mi prioridad es: " + mi_hilo.getPriority());
		System.out.println("Mi estado es: " + mi_hilo.getState().name());
		
		System.out.println("cambiando nombre y prioridad...");
		mi_hilo.setName("hilo principal");
		mi_hilo.setPriority(2);
		
		System.out.println("Mi hilo principal se llama: " + mi_hilo.getName());
		System.out.println("Mi id es: " + mi_hilo.getId());
		System.out.println("Mi prioridad es: " + mi_hilo.getPriority());
		System.out.println("Mi estado es: " + mi_hilo.getState().name());
	}
}
