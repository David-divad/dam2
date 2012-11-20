/**
 * Ejercicios sobre hilos:
 * 
 * 2 - Programa que crea dos hilos con la clase Thread y los ejecuta. Los hilos
 *     escriben diez veces el número de iteración del bucle y su nombre. Al finalizar
 *     los hilos escriben un mensaje de finalizacion y su nombre.
 *  
 * David Suárez Rodríguez.
 * 
 */

class HiloEscribeIteracion extends Thread {
	
	public HiloEscribeIteracion(String str)
	{
		super(str);
	}
	
	public void run()
	{
        try {

		    for (int i=0; i<10; i++) {

			    System.out.format("%s, número de iteracion: %d\n", getName(), i);

			    /* 
                 * Dormimos entre 0 y 1000 milisegundos al azar para lograr más 
                 * sensacion de paralelismo. 
                 */
				sleep((int) Math.random() * 1000 );
		    }
		
            System.out.format("%s, ha terminado\n", getName());

        } catch (InterruptedException ex) {

            System.out.format("%s, interrupción\n", getName());
		}

	}
}

public class Hilos2 {

	public static void main(String[] str)
	{
		HiloEscribeIteracion hilo1 = new HiloEscribeIteracion("Hilo 1");
		HiloEscribeIteracion hilo2 = new HiloEscribeIteracion("Hilo 2");
		
		hilo1.start();
		hilo2.start();
	}
}
