/**
 * Ejercicios sobre hilos:
 * 
 * 3 - Programa en Java que hace lo mismo que el ejercicio 2. En este caso se utiliza
 *     la interfaz Runnable para la creación de los hilos.
 *  
 * David Suárez Rodríguez.
 * 
 */

class EscribeIteracion implements Runnable {
	
	public void run()
	{
        String nombre_hilo = Thread.currentThread().getName();

        try {

		    for (int i=0; i<10; i++) {

			    System.out.format("%s, número de iteracion: %d\n", nombre_hilo, i);

			    /* 
                 * Dormimos entre 0 y 1000 milisegundos al azar para lograr más 
                 * sensacion de paralelismo. 
                 */
				Thread.sleep((int) Math.random() * 1000 );
		    }
		
            System.out.format("%s, ha terminado\n", nombre_hilo);

        } catch (InterruptedException ex) {

            System.out.format("%s, interrupción\n", nombre_hilo);
		}

	}
}

public class Hilos3 {

	public static void main(String[] str)
	{
		EscribeIteracion hilo1 = new EscribeIteracion();
		EscribeIteracion hilo2 = new EscribeIteracion();
		
		new Thread(hilo1).start();
		new Thread(hilo2).start();
	}
}
