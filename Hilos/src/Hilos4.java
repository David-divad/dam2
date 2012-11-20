/**
 * Ejercicios sobre hilos:
 * 
 * 4 - Programa que crea dos hilos. Los hilos escriben diez veces el número
 *     de iteración del bucle y su nombre. Despues de escribir el nombre
 *     se bloquean utilizando el metodo yield() y dejan paso al otro hilo.
 *  
 * David Suárez Rodríguez.
 * 
 */

class HiloEscribeIteracionConYield extends Thread {
	
	public HiloEscribeIteracionConYield(String str)
	{
		super(str);
	}
	
	public void run()
	{
	    for (int i=0; i<10; i++) {

		    System.out.format("%s, número de iteracion: %d\n", getName(), i);
		    yield();
	    }
	
        System.out.format("%s, ha terminado\n", getName());
	}
}

public class Hilos4 {

	public static void main(String[] str)
	{
		HiloEscribeIteracionConYield hilo1 = new HiloEscribeIteracionConYield("Hilo 1");
		HiloEscribeIteracionConYield hilo2 = new HiloEscribeIteracionConYield("Hilo 2");
		
		hilo1.start();
		hilo2.start();
	}
}
