package deiv.org.listas_e01;

public class DistroInfo {

	protected String nombre;
	protected String anio;
	
	public DistroInfo(String nombre, String anio)
	{
		this.nombre = nombre;
		this.anio = anio;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
}
