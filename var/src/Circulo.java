
public class Circulo {

	int x, y, radio;
	
	public Circulo(int x, int y, int radio)
	{
		this.x = x;
		this.y = y;
		this.radio = radio;
		Resultado();
	}
	
	public Circulo(int x, int y)
	{
		this(x, y, 1);
	}
	
	public void Resultado()
	{
		System.out.println(x*y*radio);
	}
	
	public static void main(String[] args)
	{	
		Circulo circulo1 = new Circulo(2, 3);
		Circulo circulo2 = new Circulo(2, 3, 2);
		
		circulo1.Resultado();
		circulo2.Resultado();
	}
}
