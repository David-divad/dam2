/**
 * Ejercicios sobre hilos:
 * 
 * 5 - Programa que crea cuatro hilos, los ejecuta y espera que terminen. 
 *     Los hilos escriben cinco veces el número de iteración del bucle y su nombre.
 *     Despues de escribir el nombre se bloquean durante un segundo y vuelven a estar 
 *     disponibles para su ejecución.
 *  
 * David Suárez Rodríguez.
 * 
 */

class HiloEscribeIteracionConSleep extends Thread {
	
	public HiloEscribeIteracionConSleep(String str)
	{
		super(str);
	}
	
	public void run()
	{
        try {

		    for (int i=0; i<5; i++) {

			    System.out.format("%s, número de iteracion: %d\n", getName(), i);

				sleep(1000);
		    }
		
            System.out.format("%s, ha terminado\n", getName());

        } catch (InterruptedException ex) {

            System.out.format("%s, interrupción\n", getName());
		}

	}
}

public class Hilos5 {

	public static void main(String[] str)
	{
		HiloEscribeIteracionConSleep hilo1 = new HiloEscribeIteracionConSleep("Hilo 1");
		HiloEscribeIteracionConSleep hilo2 = new HiloEscribeIteracionConSleep("Hilo 2");
		HiloEscribeIteracionConSleep hilo3 = new HiloEscribeIteracionConSleep("Hilo 3");
		HiloEscribeIteracionConSleep hilo4 = new HiloEscribeIteracionConSleep("Hilo 4");
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		hilo4.start();
		
		esperaHiloTermine(hilo1);
		esperaHiloTermine(hilo2);
		esperaHiloTermine(hilo3);
		esperaHiloTermine(hilo4);
		
		System.out.println("El programa ha finalizado");
	}
	
	public static void esperaHiloTermine(Thread hilo)
	{
		/* 
		 * Lo envolvemos dentro de un bucle infinito, ya que podemos ser "interrumpidos"
		 *  durante la llamada a 'join()' 
		 */
		while (hilo.isAlive()) {
			try {
				hilo.join();
				
			} catch (InterruptedException e) {
				/* sin uso */
			}
		}	
	}
}
